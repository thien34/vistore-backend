package com.example.back_end.repository;

import com.example.back_end.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends JpaRepository<Order, Long> , JpaSpecificationExecutor<Order> {
}