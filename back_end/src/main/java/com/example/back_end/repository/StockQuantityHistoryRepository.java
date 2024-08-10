package com.example.back_end.repository;

import com.example.back_end.entity.StockQuantityHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockQuantityHistoryRepository extends JpaRepository<StockQuantityHistory, Long> {

    @Query(value = "select sqh from StockQuantityHistory  sqh where (:productId is null) or (sqh.product.id = :productId)")
    Page<StockQuantityHistory> findAll(Long productId, Pageable pageable);

}
