package com.example.back_end.repository;

import com.example.back_end.entity.ProductSpecificationAttributeMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductSpecificationAttributeMappingRepository extends JpaRepository<ProductSpecificationAttributeMapping, Long> {
    @Query("SELECT p FROM ProductSpecificationAttributeMapping p WHERE p.customValue LIKE %:name%")
    Page<ProductSpecificationAttributeMapping> searchByCustomValueContaining(
            @Param("name") String name,
            Pageable pageable);
    Page<ProductSpecificationAttributeMapping> findByProductId(Long productId, Pageable pageable);
}