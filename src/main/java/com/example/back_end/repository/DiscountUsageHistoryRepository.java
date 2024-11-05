package com.example.back_end.repository;

import com.example.back_end.entity.DiscountUsageHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountUsageHistoryRepository extends JpaRepository<DiscountUsageHistory, Long> {
}
