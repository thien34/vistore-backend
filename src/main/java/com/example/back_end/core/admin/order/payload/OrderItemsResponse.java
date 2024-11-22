package com.example.back_end.core.admin.order.payload;

import com.example.back_end.core.admin.product.payload.response.ProductResponse;
import com.example.back_end.entity.Address;
import com.example.back_end.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsResponse {

    private Long id;
    private String orderCode;
    private UUID orderItemGuid;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal priceTotal;
    private BigDecimal discountAmount;
    private BigDecimal originalProductCost;
    private String attributeDescription;
    private String productJson;
    private BigDecimal itemWeight;
    private Integer deliveryStatus;
    private CustomerOrder customerOrder;
    private ProductResponse productResponse;
    public static OrderItemsResponse fromOrderItemsResponse(OrderItem orderItem, Address address) {

        OrderItemsResponse orderItemsResponse = new OrderItemsResponse();
        orderItemsResponse.setId(orderItem.getId());
        orderItemsResponse.setOrderCode(orderItem.getOrderItemGuid().toString());
        orderItemsResponse.setQuantity(orderItem.getQuantity());
        orderItemsResponse.setUnitPrice(orderItem.getUnitPrice());
        orderItemsResponse.setPriceTotal(orderItem.getPriceTotal());
        orderItemsResponse.setDiscountAmount(orderItem.getDiscountAmount());
        orderItemsResponse.setOriginalProductCost(orderItem.getOriginalProductCost());
        orderItemsResponse.setAttributeDescription(orderItem.getAttributeDescription());
        orderItemsResponse.setProductJson(orderItem.getProductJson());
        orderItemsResponse.setItemWeight(orderItem.getItemWeight());
        orderItemsResponse.setDeliveryStatus(orderItem.getOrder().getDeliveryStatusType().value);
        orderItemsResponse.setProductResponse(ProductResponse.fromProduct(orderItem.getProduct()));
        orderItemsResponse.setCustomerOrder(CustomerOrder.fromCustomerOrder(address));
        return orderItemsResponse;
    }



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomerOrder {
        private Long id;
        private String customerName;
        private String customerPhone;
        private String addressOrder;
        private Long addressId;

        public static CustomerOrder fromCustomerOrder(Address address) {
            if (address == null) {
                return null;
            }
            CustomerOrder customerOrder = new CustomerOrder();
            customerOrder.setId(address.getId());
            customerOrder.setCustomerName(address.getFirstName() + " " + address.getLastName());
            customerOrder.setAddressOrder(address.getAddressName());
            customerOrder.setAddressId(address.getId());
            return customerOrder;
        }

    }
}
