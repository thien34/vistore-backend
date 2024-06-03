package com.example.back_end.repository;

import com.example.back_end.entity.ProductCategoryMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryMappingRepository extends JpaRepository<ProductCategoryMapping, Long> {
}