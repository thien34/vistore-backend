package com.example.back_end.repository;

import com.example.back_end.entity.ProductSpecificationAttributeMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSpecificationAttributeMappingRepository extends JpaRepository<ProductSpecificationAttributeMapping, Long> {
}