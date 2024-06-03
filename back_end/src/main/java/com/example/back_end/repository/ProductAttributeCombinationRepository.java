package com.example.back_end.repository;

import com.example.back_end.entity.ProductAttributeCombination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributeCombinationRepository extends JpaRepository<ProductAttributeCombination, Long> {
}