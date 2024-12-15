package com.example.back_end.repository;

import com.example.back_end.core.admin.statistical.payload.ProductSalesResponse;
import com.example.back_end.entity.OrderItem;
import com.example.back_end.infrastructure.constant.OrderStatusType;
import com.example.back_end.infrastructure.constant.PaymentStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT SUM(oi.quantity) FROM OrderItem oi WHERE oi.product.parentProductId = :parentId AND oi.order.orderStatusId = :status")
    Integer sumQuantityByParentProductIdAndOrderStatus(Long parentId, OrderStatusType status);

    List<OrderItem> findByOrderId(Long orderId);

    Page<OrderItem> findAllByOrderId(Long orderId, Pageable pageable);

    @Query("SELECT SUM(oi.quantity) " +
            "FROM OrderItem oi " +
            "JOIN oi.order o " +
            "WHERE o.paidDateUtc BETWEEN :startDate AND :endDate " +
            "AND o.paymentStatusId = :paymentStatus")
    Long countSoldProductsInDateRange(Instant startDate, Instant endDate, PaymentStatusType paymentStatus);

    @Query("SELECT oi.product, SUM(oi.quantity), SUM(oi.product.unitPrice * oi.quantity) " +
            "FROM OrderItem oi " +
            "WHERE oi.order.paymentStatusId = :paidStatus " +
            "GROUP BY oi.product " +
            "ORDER BY SUM(oi.quantity) DESC")
    List<Object[]> findTopSellingProducts(@Param("paidStatus") PaymentStatusType paidStatus, Pageable pageable);
    @Query("SELECT new com.example.back_end.core.admin.statistical.payload.ProductSalesResponse(" +
            "p.name, SUM(oi.quantity), SUM(oi.priceTotal)) " +
            "FROM OrderItem oi " +
            "JOIN oi.product p " +
            "JOIN oi.order o " +
            "WHERE o.paidDateUtc BETWEEN :startDate AND :endDate " +
            "GROUP BY p.name")
    List<ProductSalesResponse> findSalesDataByDateRange(@Param("startDate") Instant startDate,
                                                        @Param("endDate") Instant endDate);
}
