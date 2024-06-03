package com.example.back_end.repository;

import com.example.back_end.entity.StockQuantityHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockQuantityHistoryRepository extends JpaRepository<StockQuantityHistory, Long> {
}
