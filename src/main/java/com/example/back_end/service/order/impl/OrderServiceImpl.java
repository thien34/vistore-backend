package com.example.back_end.service.order.impl;

import com.example.back_end.core.admin.order.mapper.OrderMapper;
import com.example.back_end.core.admin.order.payload.CustomerOrderResponse;
import com.example.back_end.core.admin.order.payload.OrderCustomerResponse;
import com.example.back_end.core.admin.order.payload.OrderFilter;
import com.example.back_end.core.admin.order.payload.OrderItemSummary;
import com.example.back_end.core.admin.order.payload.OrderItemsResponse;
import com.example.back_end.core.admin.order.payload.OrderRequest;
import com.example.back_end.core.admin.order.payload.OrderResponse;
import com.example.back_end.core.admin.order.payload.OrderStatusHistoryResponse;
import com.example.back_end.core.common.PageRequest;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.entity.Address;
import com.example.back_end.entity.Customer;
import com.example.back_end.entity.CustomerVoucher;
import com.example.back_end.entity.Discount;
import com.example.back_end.entity.DiscountUsageHistory;
import com.example.back_end.entity.Order;
import com.example.back_end.entity.OrderItem;
import com.example.back_end.entity.OrderStatusHistory;
import com.example.back_end.entity.Product;
import com.example.back_end.entity.ShoppingCartItem;
import com.example.back_end.entity.Ward;
import com.example.back_end.infrastructure.constant.EnumAdaptor;
import com.example.back_end.infrastructure.constant.OrderStatusType;
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.infrastructure.utils.ProductJsonConverter;
import com.example.back_end.repository.AddressRepository;
import com.example.back_end.repository.CustomerVoucherRepository;
import com.example.back_end.repository.DiscountRepository;
import com.example.back_end.repository.DiscountUsageHistoryRepository;
import com.example.back_end.repository.OrderItemRepository;
import com.example.back_end.repository.OrderRepository;
import com.example.back_end.repository.OrderStatusHistoryRepository;
import com.example.back_end.repository.ProductRepository;
import com.example.back_end.repository.ShoppingCartItemRepository;
import com.example.back_end.repository.WardRepository;
import com.example.back_end.service.order.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;
    AddressRepository addressRepository;
    ShoppingCartItemRepository cartItemRepository;
    ProductRepository productRepository;
    OrderStatusHistoryRepository orderStatusHistoryRepository;
    DiscountUsageHistoryRepository discountUsageHistoryRepository;
    DiscountRepository discountRepository;
    WardRepository wardRepository;
    CustomerVoucherRepository customerVoucherRepository;
    OrderMapper orderMapper;

    @Override
    @Transactional
    public void saveOrder(OrderRequest request) {
        ShoppingCartItem cartItem = cartItemRepository.findByCartUUID(request.getOrderGuid());
        LocalDateTime cartItemCreateDate = LocalDateTime.now();
        if (cartItem != null) {
            cartItemCreateDate = cartItem.getCreatedDate();
        }

        Order order = OrderRequest.toEntity(request);
        Address address = resolveAddress(request);
        order.setShippingAddress(address);
        order.setCreatedDate(cartItemCreateDate);
        Order savedOrder = orderRepository.save(order);
        if (request.getIdVouchers() != null && !request.getIdVouchers().isEmpty()) {
            List<Long> voucherIds = request.getIdVouchers();

            List<Discount> discounts = discountRepository.findAllById(voucherIds);

            for (Discount discount : discounts) {
                if (discount.getUsageCount() != null && discount.getUsageCount() > 0) {
                    discount.setUsageCount(discount.getUsageCount() - 1);
                } else {
                    throw new IllegalArgumentException("Voucher đã hết số lần sử dụng: " + discount.getCouponCode());
                }
                CustomerVoucher customerVoucher = customerVoucherRepository.findByCustomerIdAndDiscountId(
                        request.getCustomerId(), discount.getId()
                ).orElseGet(() -> CustomerVoucher.builder()
                        .customer(Customer.builder().id(request.getCustomerId()).build())
                        .discount(discount)
                        .usageCountPerCustomer(0)
                        .build()
                );
                if (customerVoucher.getUsageCountPerCustomer() != null) {
                    if (discount.getPerCustomerLimit() != null &&
                            customerVoucher.getUsageCountPerCustomer() >= discount.getPerCustomerLimit()) {
                        throw new IllegalArgumentException("Voucher đã vượt giới hạn sử dụng cho khách hàng: " + discount.getCouponCode());
                    }
                    customerVoucher.setUsageCountPerCustomer(customerVoucher.getUsageCountPerCustomer() + 1);
                } else {
                    customerVoucher.setUsageCountPerCustomer(1);
                }
                customerVoucherRepository.save(customerVoucher);
            }

            discountRepository.saveAll(discounts);

            List<DiscountUsageHistory> discountUsageHistories = voucherIds.stream()
                    .map(discountId -> {
                        Discount discount = findDiscountById(discountId);
                        DiscountUsageHistory usageHistory = new DiscountUsageHistory();
                        usageHistory.setDiscount(discount);
                        usageHistory.setOrder(order);
                        return usageHistory;
                    }).toList();
            discountUsageHistoryRepository.saveAll(discountUsageHistories);
        }

        createOrderStatusHistory(savedOrder, cartItemCreateDate);
        updateOrderStatusHistory(savedOrder, request.getOrderStatusId(), cartItemCreateDate);

        if (request.getOrderItems() != null && !request.getOrderItems().isEmpty()) {
            List<OrderItem> orderItems = createOrderItems(request, savedOrder);
            orderItemRepository.saveAll(orderItems);
        }
    }

    private Discount findDiscountById(Long discountId) {
        return discountRepository.findById(discountId)
                .orElseThrow(() -> new NotFoundException("Giảm giá không được tìm thấy với ID: " + discountId));
    }

    @Override
    public void updateOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    private Address resolveAddress(OrderRequest request) {
        if (request.getAddressRequest() == null) return null;

        Address existingAddress = addressRepository.findByCustomerIdAndFullAddress(
                request.getCustomerId(),
                request.getAddressRequest().getAddressName(),
                request.getAddressRequest().getPhoneNumber(),
                request.getAddressRequest().getDistrictId(),
                request.getAddressRequest().getProvinceId(),
                request.getAddressRequest().getWardId()
        );

        if (existingAddress != null) {
            return existingAddress;
        }

        Address address = request.getAddressRequest().toEntity();
        if (address.getWard() != null && address.getWard().getCode() != null) {
            Ward ward = wardRepository.findById(address.getWard().getCode())
                    .orElseThrow(() -> new NotFoundException("Phường không được tìm thấy với ID: " + address.getWard().getCode()));
            address.setWard(ward);
        }
        return addressRepository.save(address);
    }

    private void createOrderStatusHistory(Order order, LocalDateTime createdDate) {
        Instant instant = createdDate.atZone(ZoneId.systemDefault()).toInstant();

        OrderStatusHistory statusHistory = new OrderStatusHistory();
        statusHistory.setPaidDate(instant);
        statusHistory.setStatus(OrderStatusType.CREATED);
        statusHistory.setNotes("");
        statusHistory.setOrder(order);
        orderStatusHistoryRepository.save(statusHistory);
    }

    private void updateOrderStatusHistory(Order order, Integer orderStatusId, LocalDateTime createdDate) {
        Instant instant = createdDate.atZone(ZoneId.systemDefault()).toInstant();
        OrderStatusType statusType = EnumAdaptor.valueOf(orderStatusId, OrderStatusType.class);
        OrderStatusHistory paidHistory = createOrderStatusHistory(order, statusType, instant, "");
        orderStatusHistoryRepository.save(paidHistory);

        if (statusType == OrderStatusType.PAID) {
            OrderStatusHistory completedHistory = createOrderStatusHistory(order, OrderStatusType.COMPLETED, instant, "");
            orderStatusHistoryRepository.save(completedHistory);
        }
    }

    private OrderStatusHistory createOrderStatusHistory(Order order, OrderStatusType statusType, Instant
            paidDate, String notes) {
        OrderStatusHistory statusHistory = new OrderStatusHistory();
        statusHistory.setPaidDate(paidDate);
        statusHistory.setStatus(statusType);
        statusHistory.setNotes(notes);
        statusHistory.setOrder(order);
        return statusHistory;
    }

    private List<OrderItem> createOrderItems(OrderRequest request, Order order) {

        return request.getOrderItems()
                .stream()
                .map(itemRequest -> createOrderItem(itemRequest, order))
                .toList();
    }

    private OrderItem createOrderItem(OrderRequest.OrderItemRequest request, Order order) {

        Product product = findProductById(request.getProductId());

        int newQuantity = product.getQuantity() - request.getQuantity();
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Không đủ hàng cho sản phẩm: " + product.getId());
        }
        product.setQuantity(newQuantity);
        productRepository.save(product);

        return createOrderItem(order, request, product);
    }

    private OrderItem createOrderItem(Order order, OrderRequest.OrderItemRequest itemRequest, Product product) {
        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(Product.builder().id(itemRequest.getProductId()).build());
        item.setOrderItemGuid(UUID.fromString(String.valueOf(order.getOrderGuid())));
        item.setQuantity(itemRequest.getQuantity());
        item.setUnitPrice(itemRequest.getUnitPrice());
        item.setPriceTotal(itemRequest.getPriceTotal());
        item.setDiscountAmount(itemRequest.getDiscountAmount());
        item.setOriginalProductCost(itemRequest.getOriginalProductCost());
        item.setAttributeDescription(itemRequest.getAttributeDescription());
        String productJson = convertProductToJson(product);
        item.setProductJson(productJson);
        return item;
    }

    public String convertProductToJson(Product product) {
        try {
            ProductJsonConverter converter = new ProductJsonConverter();
            return converter.convertProductToJson(product);
        } catch (Exception e) {
            throw new RuntimeException("Không thể chuyển đổi sản phẩm sang json", e);
        }
    }

    @Override
    public List<OrderResponse> getOrders(OrderFilter filter) {
        return orderRepository.findAll(OrderSpecification.filterBy(filter))
                .stream()
                .map(OrderResponse::fromOrder)
                .toList();
    }

    @Override
    public List<OrderItemsResponse> getOrderItemsByOrderId(Long orderId) {

        Order order = findOrderById(orderId);
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);

        Address address;
        if (order.getShippingAddress() != null) {
            address = addressRepository.findById(order.getShippingAddress().getId()).orElse(null);
        } else if (order.getCustomer() != null) {
            List<Address> addresses = addressRepository.findByCustomerId(order.getCustomer().getId());
            if (addresses != null && !addresses.isEmpty()) {
                address = addresses.stream().findFirst().get();
            } else {
                address = null;
            }
        } else {
            address = null;
        }

        return orderItems
                .stream().map(orderItem -> OrderItemsResponse.fromOrderItemsResponse(orderItem, address)).toList();
    }

    @Override
    public List<OrderStatusHistoryResponse> getOrderStatusHistory(Long orderId) {
        List<OrderStatusHistory> statusHistories = orderStatusHistoryRepository.
                findByOrderId(orderId);
        return statusHistories.stream()
                .sorted(Comparator.comparing(OrderStatusHistory::getPaidDate))
                .map(OrderStatusHistoryResponse::fromOrderStatusHistoryResponse)
                .toList();
    }

    @Override
    @Transactional
    public void updateQuantity(Long id, Integer quantity) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sản phẩm không được tìm thấy với ID: " + id));
        Integer oldQuantity = orderItem.getQuantity();
        orderItem.setQuantity(quantity);

        orderItem.setQuantity(quantity);

        Product product = findProductById(orderItem.getProduct().getId());
        if (quantity < oldQuantity) {
            product.setQuantity(product.getQuantity() + (oldQuantity - quantity));
        } else {
            product.setQuantity(product.getQuantity() - (quantity - oldQuantity));
        }
        product = productRepository.save(product);

        if (orderItem.getQuantity() > product.getQuantity()) {
            throw new RuntimeException("Không đủ hàng cho sản phẩm: " + product.getName() + ". Số lượng có sẵn: " + product.getQuantity());
        }

        orderItem.setQuantity(quantity);
        orderItem.setPriceTotal(orderItem.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));
        orderItemRepository.save(orderItem);

        Order order = findOrderById(orderItem.getOrder().getId());
        BigDecimal newOrderSubtotal = BigDecimal.ZERO;
        BigDecimal newOrderDiscount = BigDecimal.ZERO;
        BigDecimal newOrderTotal;

        for (OrderItem item : order.getOrderItems()) {
            newOrderSubtotal = newOrderSubtotal.add(item.getPriceTotal());
        }

        if (order.getOrderSubtotalDiscount() != null) {
            newOrderDiscount = order.getOrderSubtotalDiscount();
        }

        BigDecimal orderShipping = order.getOrderShipping() != null ? order.getOrderShipping() : BigDecimal.ZERO;
        newOrderTotal = newOrderSubtotal.subtract(newOrderDiscount).add(orderShipping);
        order.setOrderSubtotal(newOrderSubtotal);
        order.setOrderTotal(newOrderTotal);
        orderRepository.save(order);
    }

    @Override
    public void addProductToOrder(OrderRequest.OrderItemRequest itemRequest, Long orderId) {

        Order order = findOrderById(orderId);
        Product product = findProductById(itemRequest.getProductId());

        if (itemRequest.getQuantity() > product.getQuantity()) {
            throw new RuntimeException("Không đủ hàng cho sản phẩm: " + product.getName());
        }

        OrderItem orderItem = createOrderItem(itemRequest, order);
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        orderItems.forEach(orderItem1 -> {
            if (orderItem1.getProduct().getId().equals(itemRequest.getProductId())) {
                orderItem.setId(orderItem1.getId());
                orderItem.setQuantity(orderItem1.getQuantity() + itemRequest.getQuantity());
            }
        });
        orderItemRepository.save(orderItem);
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm"));
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy đơn hàng"));
    }

    @Override
    public PageResponse1<List<CustomerOrderResponse>> getCustomerOrders(PageRequest pageRequest) {
        Pageable pageable = PageUtils.createPageable(
                pageRequest.getPageNo(),
                pageRequest.getPageSize(),
                pageRequest.getSortBy(),
                pageRequest.getSortDir());

        Page<Order> result = orderRepository.findAllOrderNotReturn(pageable);
        List<Order> filteredCompletedOrders = result.getContent().stream()
                .filter(x -> x.getOrderStatusId() == OrderStatusType.COMPLETED)
                .collect(Collectors.toList());
        result = new PageImpl<>(filteredCompletedOrders, pageable, filteredCompletedOrders.size());
        List<CustomerOrderResponse> customerOrderResponse = orderMapper.toOrderResponses(result.getContent());

        return PageResponse1.<List<CustomerOrderResponse>>builder()
                .totalItems(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .items(customerOrderResponse)
                .build();
    }

    @Override
    public List<OrderItemSummary> getAllOrderItemSummaryByOrderId(Long orderId) {
        return orderMapper.mapToSummaries(orderItemRepository.findByOrderId(orderId));
    }

    @Override
    @Transactional
    public void changeStatus(Integer status, String note, Long orderId) {
        Order order = findOrderById(orderId);
        LocalDateTime createdDate = LocalDateTime.now();
        Instant instant = createdDate.atZone(ZoneId.systemDefault()).toInstant();

        OrderStatusHistory orderStatusHistory = new OrderStatusHistory();
        OrderStatusType statusType = EnumAdaptor.valueOf(status, OrderStatusType.class);
        orderStatusHistory.setStatus(statusType);
        orderStatusHistory.setNotes(note);
        orderStatusHistory.setOrder(order);
        orderStatusHistory.setPaidDate(instant);

        order.setOrderStatusId(statusType);
        orderStatusHistoryRepository.save(orderStatusHistory);
        orderRepository.save(order);
    }

    @Override
    public OrderCustomerResponse getCustomerById(Long orderId) {
        Order order = findOrderById(orderId);

        Customer customer = order.getCustomer();
        OrderCustomerResponse customerResponse = new OrderCustomerResponse();
//        PaymentStatusType statusType = EnumAdaptor.valueOf(order.getPaymentStatusId().value, PaymentStatusType.class);
        if (order.getShippingAddress() != null) {
            Address address = order.getShippingAddress();
            customerResponse.setFirstName(address.getFirstName());
            customerResponse.setLastName(address.getLastName());
            customerResponse.setPhoneNumber(address.getPhoneNumber());
            customerResponse.setBillId(order.getBillCode());
            customerResponse.setDelivery("Delivery");
            customerResponse.setOrderStatusType((long) order.getOrderStatusId().value);
            return customerResponse;
        }

        if (customer.getId() == 1) {
            customerResponse.setFirstName("Retail");
            customerResponse.setDelivery("At Repair");
            customerResponse.setBillId(order.getBillCode());
            customerResponse.setOrderStatusType((long) order.getOrderStatusId().value);
            return customerResponse;
        }

        customerResponse.setFirstName(customer.getFirstName());
        customerResponse.setLastName(customer.getLastName());
        customerResponse.setPhoneNumber(customerResponse.getPhoneNumber());
        customerResponse.setBillId(customerResponse.getBillId());
//        customerResponse.setDelivery("Delivery");
        customerResponse.setOrderStatusType((long) order.getOrderStatusId().value);

        return customerResponse;
    }

    public String getProductJsonByOrderId(Long orderId) {
        Optional<OrderItem> orderItem = orderItemRepository.findById(orderId);
        if (orderItem.isPresent()) {
            return orderItem.get().getProductJson();
        } else return "";
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId, String note) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy đơn hàng"));

        if (Boolean.TRUE.equals(order.getDeleted())) {
            throw new IllegalStateException("Đơn hàng đã bị xóa");
        }

        LocalDateTime createdDate = LocalDateTime.now();
        Instant instant = createdDate.atZone(ZoneId.systemDefault()).toInstant();

        List<OrderItem> orderItems = order.getOrderItems();

        for (OrderItem orderItem : orderItems) {
            Product product = orderItem.getProduct();
            if (product != null) {
                product.setQuantity(product.getQuantity() + orderItem.getQuantity());
                productRepository.save(product);
            }
        }

        order.setOrderStatusId(OrderStatusType.CANCELLED);
        order.setDeleted(true);

        OrderStatusHistory orderStatusHistory = new OrderStatusHistory();
        orderStatusHistory.setStatus(OrderStatusType.CANCELLED);
        orderStatusHistory.setNotes(note);
        orderStatusHistory.setOrder(order);
        orderStatusHistory.setPaidDate(instant);

        orderRepository.save(order);
        orderStatusHistoryRepository.save(orderStatusHistory);
    }


    @Override
    public List<String> getDiscountByOrderId(Long orderId) {
        return discountUsageHistoryRepository.findByOrderId(orderId)
                .stream().map(d -> d.getDiscount().getCouponCode())
                .toList();
    }

}
