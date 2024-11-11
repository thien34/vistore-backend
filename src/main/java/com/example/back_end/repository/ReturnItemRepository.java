package com.example.back_end.repository;

import com.example.back_end.entity.ReturnItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReturnItemRepository extends JpaRepository<ReturnItem, Long> {
    ReturnItem getById(long returnItemId);

    @Query(value = "select rt from ReturnItem rt where rt.returnRequest.id = :returnRequestId")
    Page<ReturnItem> findByReturnRequestId(Long returnRequestId, Pageable pageable);
}
