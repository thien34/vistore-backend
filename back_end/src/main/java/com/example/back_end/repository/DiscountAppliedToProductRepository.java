package com.example.back_end.repository;

import com.example.back_end.entity.DiscountAppliedToProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountAppliedToProductRepository extends JpaRepository<DiscountAppliedToProduct, Long> {
}