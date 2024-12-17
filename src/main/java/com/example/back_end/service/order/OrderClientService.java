package com.example.back_end.service.order;

import com.example.back_end.core.admin.order.payload.OrderResponse;

import java.util.List;

public interface OrderClientService {

    List<OrderResponse> getOrders(Long customerId);

}
