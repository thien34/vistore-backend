package com.example.back_end.repository;

import com.example.back_end.entity.DiscountAppliedToProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface DiscountAppliedToProductRepository extends JpaRepository<DiscountAppliedToProduct, Long> {
    Collection<Object> findByProductId(Long id);

    void deleteByProductId(Long id);
}