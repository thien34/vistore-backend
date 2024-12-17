package com.example.back_end.core.admin.order.payload;

import com.example.back_end.entity.Address;
import com.example.back_end.entity.Customer;
import com.example.back_end.entity.District;
import com.example.back_end.entity.Order;
import com.example.back_end.entity.Province;
import com.example.back_end.entity.Ward;
import com.example.back_end.infrastructure.constant.DeliveryStatusType;
import com.example.back_end.infrastructure.constant.EnumAdaptor;
import com.example.back_end.infrastructure.constant.OrderStatusType;
import com.example.back_end.infrastructure.constant.PaymentMethodType;
import com.example.back_end.infrastructure.constant.PaymentMode;
import com.example.back_end.infrastructure.constant.PaymentStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequest {
    private Long customerId;
    private String orderGuid;
    private Integer addressType;
    private String orderId;
    private Boolean pickupInStore;
    private Integer orderStatusId;
    private Integer paymentStatusId;
    private Integer paymentMethodId;
    private BigDecimal orderSubtotal;
    private BigDecimal orderSubtotalDiscount;
    private BigDecimal orderShipping;
    private BigDecimal orderDiscount;
    private BigDecimal orderTotal;
    private BigDecimal refundedAmount;
    private Instant paidDateUtc;
    private String billCode;
    private List<OrderItemRequest> orderItems;
    private AddressRequest addressRequest;
    private Integer paymentMode;
    private Integer deliveryMode;
    private List<Long> idVouchers;


    public static Order toEntity(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderGuid(UUID.fromString(orderRequest.getOrderGuid()));
        order.setOrderDiscount(orderRequest.getOrderDiscount());
        order.setOrderTotal(orderRequest.getOrderTotal());
        order.setRefundedAmount(orderRequest.getRefundedAmount());
        order.setOrderShipping(orderRequest.getOrderShipping());
        order.setOrderSubtotal(orderRequest.getOrderSubtotal());
        order.setOrderSubtotalDiscount(orderRequest.getOrderSubtotalDiscount());
        order.setCustomer(Customer.builder()
                .id(orderRequest.getCustomerId())
                .build());
        OrderStatusType statusType = EnumAdaptor.valueOf(orderRequest.getOrderStatusId(), OrderStatusType.class);
        if (statusType == OrderStatusType.PAID) {
            order.setOrderStatusId(OrderStatusType.COMPLETED);
        } else {
            order.setOrderStatusId(EnumAdaptor.valueOf(orderRequest.getOrderStatusId(), OrderStatusType.class));
        }
        order.setPaymentStatusId(EnumAdaptor.valueOf(orderRequest.getPaymentStatusId(), PaymentStatusType.class));
        order.setPaymentMethodId(EnumAdaptor.valueOf(orderRequest.getPaymentMethodId(), PaymentMethodType.class));
        order.setPaidDateUtc(Instant.now());
        order.setBillCode(orderRequest.getBillCode());
        order.setPaymentMode(EnumAdaptor.valueOf(orderRequest.getPaymentMode(), PaymentMode.class));
        order.setDeliveryStatusType(EnumAdaptor.valueOf(orderRequest.getDeliveryMode(), DeliveryStatusType.class));
        order.setDeleted(false);

        if (orderRequest.getAddressType() != null) {
            Address address = Address.builder().id(orderRequest.getAddressType().longValue()).build();
            switch (orderRequest.getAddressType()) {
                case 1 -> order.setBillingAddress(address);
                case 2 -> order.setShippingAddress(address);
                case 3 -> order.setPickupAddress(address);
            }
        }
        return order;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderItemRequest {
        private Long productId;
        private String orderItemGuid;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal priceTotal;
        private BigDecimal discountAmount;
        private BigDecimal originalProductCost;
        private String attributeDescription;
        private BigDecimal discountPrice;
        private boolean checkQuantity = false;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressRequest {
        private Long customerId;
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private String addressName;
        private String provinceId;
        private String districtId;
        private String wardId;

        public Address toEntity() {
            Address address = new Address();
            address.setAddressName(addressName);
            address.setCustomer(Customer.builder().id(customerId).build());
            address.setFirstName(firstName);
            address.setLastName(lastName);
            address.setEmail(email);
            address.setPhoneNumber(phoneNumber);
            address.setProvince(Province.builder().code(provinceId).build());
            address.setDistrict(District.builder().code(districtId).build());
            address.setWard(Ward.builder().code(wardId).build());
            return address;
        }
    }
}
