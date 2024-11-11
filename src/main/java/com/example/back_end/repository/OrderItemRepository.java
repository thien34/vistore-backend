package com.example.back_end.repository;

import com.example.back_end.entity.OrderItem;
import com.example.back_end.infrastructure.constant.OrderStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT SUM(oi.quantity) FROM OrderItem oi WHERE oi.product.parentProductId = :parentId AND oi.order.orderStatusId = :status")
    Integer sumQuantityByParentProductIdAndOrderStatus(Long parentId, OrderStatusType status);

}
