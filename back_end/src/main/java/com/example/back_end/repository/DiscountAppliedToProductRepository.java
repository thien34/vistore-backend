package com.example.back_end.repository;

import com.example.back_end.entity.DiscountAppliedToProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscountAppliedToProductRepository extends JpaRepository<DiscountAppliedToProduct, Long> {
    void deleteByDiscountId(Long id);
    List<DiscountAppliedToProduct> findByDiscountId(Long discountId);
}