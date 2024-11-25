package com.example.back_end.service.order;

import com.example.back_end.core.admin.order.payload.OrderFilter;
import com.example.back_end.core.admin.order.payload.OrderItemsResponse;
import com.example.back_end.core.admin.order.payload.OrderRequest;
import com.example.back_end.core.admin.order.payload.OrderResponse;
import com.example.back_end.core.admin.order.payload.OrderStatusHistoryResponse;

import java.util.List;

public interface OrderService {

    void saveOrder(OrderRequest request);

    List<OrderResponse> getOrders(OrderFilter filter);

    List<OrderItemsResponse> getOrderItemsByOrderId(Long orderId);

    List<OrderStatusHistoryResponse> getOrderStatusHistory(Long orderId);

    void updateQuantity(Long id, Integer quantity);

    void addProductToOrder(OrderRequest.OrderItemRequest itemRequest, Long orderId);

}
