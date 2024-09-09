package com.example.back_end.repository;

import com.example.back_end.entity.ProductCategoryMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ProductCategoryMappingRepository extends JpaRepository<ProductCategoryMapping, Long> {
    void deleteByProductId(Long id);

    Collection<Object> findByProductId(Long id);
}