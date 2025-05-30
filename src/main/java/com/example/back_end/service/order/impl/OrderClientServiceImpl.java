package com.example.back_end.service.order.impl;

import com.example.back_end.core.admin.order.payload.OrderResponse;
import com.example.back_end.entity.Customer;
import com.example.back_end.entity.Order;
import com.example.back_end.repository.CustomerRepository;
import com.example.back_end.repository.OrderRepository;
import com.example.back_end.service.order.OrderClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderClientServiceImpl implements OrderClientService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    @Override
    public List<OrderResponse> getOrders(Long customerId) {

        Customer customer = customerRepository.findById(customerId).orElseThrow();

        List<Order> orders = orderRepository.findOrderByCustomer(customer);
        return orders.stream().map(OrderResponse::fromOrder)
                .sorted((o1, o2) -> o2.getPaidDateUtc().compareTo(o1.getPaidDateUtc()))
                .toList();
    }

}
