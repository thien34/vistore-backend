package com.example.back_end.repository;

import com.example.back_end.entity.ReturnRequestReason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnRequestReasonRepository extends JpaRepository<ReturnRequestReason, Long> {
}