package com.example.back_end.repository;

import com.example.back_end.entity.Order;
import com.example.back_end.infrastructure.constant.OrderStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Long> , JpaSpecificationExecutor<Order> {
    @Query("SELECT o FROM Order o WHERE o.deleted = false AND o.orderStatusId = :status AND o.orderSubtotal >= :minOrderAmount")
    List<Order> findEligibleOrdersForVoucher(@Param("status") OrderStatusType status, @Param("minOrderAmount") BigDecimal minOrderAmount);

}