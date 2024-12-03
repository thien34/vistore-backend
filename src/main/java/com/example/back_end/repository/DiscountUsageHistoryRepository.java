package com.example.back_end.repository;

import com.example.back_end.entity.DiscountUsageHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscountUsageHistoryRepository extends JpaRepository<DiscountUsageHistory, Long> {
    List<DiscountUsageHistory> findByOrderId(Long orderId);
}
