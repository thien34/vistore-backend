package com.example.back_end.core.client.order.controller;

import com.example.back_end.core.admin.order.payload.OrderResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.order.OrderClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client/orders")
@RequiredArgsConstructor
public class OrderClientController {

    private final OrderClientService orderClientService;

    @GetMapping("/{customerId}")
    public ResponseData<List<OrderResponse>> getAllOrders(@PathVariable Long customerId) {
        List<OrderResponse> orders = orderClientService.getOrders(customerId);
        return new ResponseData<>(HttpStatus.OK.value(), "Lấy đơn hàng thành công", orders);
    }

}
