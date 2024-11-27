package com.example.back_end.service.order;

import com.example.back_end.core.admin.order.payload.CustomerOrderResponse;
import com.example.back_end.core.admin.order.payload.OrderFilter;
import com.example.back_end.core.admin.order.payload.OrderItemSummary;
import com.example.back_end.core.admin.order.payload.OrderItemsResponse;
import com.example.back_end.core.admin.order.payload.OrderRequest;
import com.example.back_end.core.admin.order.payload.OrderResponse;
import com.example.back_end.core.admin.order.payload.OrderStatusHistoryResponse;
import com.example.back_end.core.common.PageRequest;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    void saveOrder(OrderRequest request);

    void updateOrder(Order order);

    Optional<Order> getOrderById(Long orderId);


    List<OrderResponse> getOrders(OrderFilter filter);

    List<OrderItemsResponse> getOrderItemsByOrderId(Long orderId);

    List<OrderStatusHistoryResponse> getOrderStatusHistory(Long orderId);

    void updateQuantity(Long id, Integer quantity);

    void addProductToOrder(OrderRequest.OrderItemRequest itemRequest, Long orderId);


    PageResponse1<List<CustomerOrderResponse>> getCustomerOrders(PageRequest pageRequest);

    List<OrderItemSummary> getAllOrderItemSummaryByOrderId(Long orderId);
}
