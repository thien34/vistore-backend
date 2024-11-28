package com.example.back_end.core.admin.order.mapper;

import com.example.back_end.core.admin.order.payload.CustomerOrderResponse;
import com.example.back_end.core.admin.order.payload.OrderItemSummary;
import com.example.back_end.entity.Order;
import com.example.back_end.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "lastModifiedDate", target = "orderDate")
    @Mapping(source = "customer.firstName", target = "firstName")
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.lastName", target = "lastName")
    CustomerOrderResponse toOrderResponse(Order order);

    List<CustomerOrderResponse> toOrderResponses(List<Order> orders);

    @Mapping(source = "id", target = "orderItemId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.fullName", target = "productName")
    @Mapping(source = "product.unitPrice", target = "productPrice")
    OrderItemSummary mapToSummary(OrderItem orderItem);


    List<OrderItemSummary> mapToSummaries(List<OrderItem> orderItems);
}
