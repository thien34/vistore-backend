package com.example.back_end.repository;

import com.example.back_end.entity.ProcessedReturnItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedReturnItemRepository extends JpaRepository<ProcessedReturnItem, Long> {
}
