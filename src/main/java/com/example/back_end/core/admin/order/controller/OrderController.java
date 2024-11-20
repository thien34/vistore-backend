package com.example.back_end.core.admin.order.controller;

import com.example.back_end.core.admin.order.payload.OrderFilter;
import com.example.back_end.core.admin.order.payload.OrderItemsResponse;
import com.example.back_end.core.admin.order.payload.OrderRequest;
import com.example.back_end.core.admin.order.payload.OrderResponse;
import com.example.back_end.core.admin.order.payload.OrderStatusHistoryResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseData<Void> saveOrder(@RequestBody OrderRequest orderRequest) {
        orderService.saveOrder(orderRequest);

        return new ResponseData<>(HttpStatus.OK.value(), "Save order success");
    }

    @GetMapping
    public ResponseData<List<OrderResponse>> getAllOrders(OrderFilter filter) {
        List<OrderResponse> orders = orderService.getOrders(filter);
        return new ResponseData<>(HttpStatus.OK.value(), "Fetch orders success", orders);
    }

    @GetMapping("/{orderId}/order-items")
    public ResponseData<List<OrderItemsResponse>> getOrderItems(@PathVariable("orderId") Long orderId) {
        List<OrderItemsResponse> responses = orderService.getOrderItemsByOrderId(orderId);
        return new ResponseData<>(HttpStatus.OK.value(), "Fetch order items success", responses);
    }

    @GetMapping("/{orderId}/order-status-history")
    public ResponseData<List<OrderStatusHistoryResponse>> getOrderHist(@PathVariable("orderId") Long orderId) {
        List<OrderStatusHistoryResponse> responses = orderService.getOrderStatusHistory(orderId);
        return new ResponseData<>(HttpStatus.OK.value(), "Fetch order status history success", responses);
    }
}
