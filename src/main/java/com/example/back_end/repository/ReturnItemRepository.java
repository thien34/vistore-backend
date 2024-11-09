package com.example.back_end.repository;

import com.example.back_end.entity.ReturnItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReturnItemRepository extends JpaRepository<ReturnItem,Long> {
    ReturnItem getById(long returnItemId);

    @Query(value = "select rt from ReturnItem rt where rt.returnRequest.id = :returnRequestId")
    List<ReturnItem> findByReturnRequestId(Long returnRequestId);
}
