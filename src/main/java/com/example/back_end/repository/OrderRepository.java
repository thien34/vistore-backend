package com.example.back_end.repository;

import com.example.back_end.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.time.Instant;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    @Query("SELECT o FROM Order o WHERE o.id NOT IN (SELECT r.order.id FROM ReturnRequest r WHERE r.order.id IS NOT NULL)")
    Page<Order> findAllOrderNotReturn(Pageable pageable);
    List<Order> findAllByPaidDateUtcBetween(Instant startDate, Instant endDate);
    @Query("SELECT o FROM Order o WHERE o.paidDateUtc IS NOT NULL ORDER BY o.paidDateUtc DESC")
    List<Order> findTop10ByOrderByPaidDateUtcDesc();
}