package com.example.back_end.service.order.impl;

import com.example.back_end.core.admin.order.payload.OrderFilter;
import com.example.back_end.core.admin.order.payload.OrderItemsResponse;
import com.example.back_end.core.admin.order.payload.OrderRequest;
import com.example.back_end.core.admin.order.payload.OrderResponse;
import com.example.back_end.core.admin.order.payload.OrderStatusHistoryResponse;
import com.example.back_end.entity.Address;
import com.example.back_end.entity.Order;
import com.example.back_end.entity.OrderItem;
import com.example.back_end.entity.OrderStatusHistory;
import com.example.back_end.entity.Product;
import com.example.back_end.entity.ShoppingCartItem;
import com.example.back_end.infrastructure.constant.EnumAdaptor;
import com.example.back_end.infrastructure.constant.OrderStatusType;
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.infrastructure.utils.ProductJsonConverter;
import com.example.back_end.repository.AddressRepository;
import com.example.back_end.repository.OrderItemRepository;
import com.example.back_end.repository.OrderRepository;
import com.example.back_end.repository.OrderStatusHistoryRepository;
import com.example.back_end.repository.ProductRepository;
import com.example.back_end.repository.ShoppingCartItemRepository;
import com.example.back_end.service.order.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressRepository addressRepository;
    private final ShoppingCartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;

    @Override
    @Transactional
    public void saveOrder(OrderRequest request) {
        ShoppingCartItem cartItem = cartItemRepository.findByCartUUID(request.getOrderGuid());
        Order order = OrderRequest.toEntity(request);

        Address address = resolveAddress(request);
        order.setShippingAddress(address);
        order.setCreatedDate(cartItem.getCreatedDate());

        Order savedOrder = orderRepository.save(order);
        createOrderStatusHistory(savedOrder, cartItem.getCreatedDate());
        updateOrderStatusHistory(savedOrder, request.getOrderStatusId(), cartItem.getCreatedDate());

        if (request.getOrderItems() != null && !request.getOrderItems().isEmpty()) {
            List<OrderItem> orderItems = createOrderItems(request, savedOrder);
            orderItemRepository.saveAll(orderItems);
        }
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
        return address != null ? addressRepository.save(address) : null;
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

        OrderStatusHistory statusHistory = new OrderStatusHistory();
        statusHistory.setPaidDate(instant);
        statusHistory.setStatus(EnumAdaptor.valueOf(orderStatusId, OrderStatusType.class));
        statusHistory.setNotes("");
        statusHistory.setOrder(order);
        orderStatusHistoryRepository.save(statusHistory);
    }

    private List<OrderItem> createOrderItems(OrderRequest request, Order order) {
        return request.getOrderItems().stream()
                .map(itemRequest -> {
                    Product product = productRepository.findById(itemRequest.getProductId())
                            .orElseThrow(() -> new NotFoundException("Product not found"));

                    int newQuantity = product.getQuantity() - itemRequest.getQuantity();
                    if (newQuantity < 0) {
                        throw new RuntimeException("Not enough stock for product: " + product.getId());
                    }

                    product.setQuantity(newQuantity);
                    productRepository.save(product);

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
                })
                .toList();
    }

    public String convertProductToJson(Product product) {
        try {
            ProductJsonConverter converter = new ProductJsonConverter();
            return converter.convertProductToJson(product);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert product to json", e);
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

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order not found" + orderId));
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
        List<OrderItemsResponse> orderItemsResponses = orderItems
                .stream().map(orderItem -> OrderItemsResponse.fromOrderItemsResponse(orderItem, address)).toList();

        return orderItemsResponses;
    }

    @Override
    public List<OrderStatusHistoryResponse> getOrderStatusHistory(Long orderId) {
        List<OrderStatusHistory> statusHistories = orderStatusHistoryRepository.
                findByOrderId(orderId);
        List<OrderStatusHistoryResponse> responses = statusHistories
                .stream().map(OrderStatusHistoryResponse::fromOrderStatusHistoryResponse)
                .toList();
        return responses;
    }

}
