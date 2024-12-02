package com.example.back_end.repository;

import com.example.back_end.entity.PendingReturnItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingReturnItemRepository extends JpaRepository<PendingReturnItem, Long> {
}
