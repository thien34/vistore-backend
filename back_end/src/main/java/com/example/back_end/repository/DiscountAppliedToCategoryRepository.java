package com.example.back_end.repository;

import com.example.back_end.entity.DiscountAppliedToCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountAppliedToCategoryRepository extends JpaRepository<DiscountAppliedToCategory, Long> {
}
