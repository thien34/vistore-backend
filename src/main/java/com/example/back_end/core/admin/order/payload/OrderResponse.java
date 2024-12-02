package com.example.back_end.core.admin.order.payload;

import com.example.back_end.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private String orderCode;
    private String billCode;
    private String customerName;
    private BigDecimal orderTotal;
    private BigDecimal orderShippingTotal;
    private BigDecimal orderDiscountTotal;
    private BigDecimal orderSubtotalDiscount;
    private Integer totalItem;
    private Instant paidDateUtc;
    private Integer orderStatus;
    private Integer paymentStatus;
    private Integer paymentMethod;
    private Integer paymentMode;
    private Long customerId;

    public static OrderResponse fromOrder(final Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setCustomerName(order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName());
        response.setOrderCode(order.getOrderGuid().toString());
        response.setOrderTotal(order.getOrderTotal());
        response.setPaidDateUtc(order.getPaidDateUtc());
        response.setBillCode(order.getBillCode());
        response.setTotalItem(order.getOrderItems() != null ? order.getOrderItems().size() : 0);
        response.setOrderStatus(order.getOrderStatusId().value);
        response.setPaymentStatus(order.getPaymentStatusId().value);
        response.setPaymentMethod(order.getPaymentMethodId().value);
        response.setPaymentMode(order.getPaymentMode().value);
        response.setCustomerId(order.getCustomer().getId());
        response.setOrderShippingTotal(order.getOrderShipping());
        response.setOrderDiscountTotal(order.getOrderDiscount());
        response.setOrderSubtotalDiscount(order.getOrderSubtotal());
        return response;
    }

}
