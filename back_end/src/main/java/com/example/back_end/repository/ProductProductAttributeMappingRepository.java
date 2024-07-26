package com.example.back_end.repository;

import com.example.back_end.entity.Product;
import com.example.back_end.entity.ProductAttribute;
import com.example.back_end.entity.ProductProductAttributeMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductProductAttributeMappingRepository extends JpaRepository<ProductProductAttributeMapping, Long> {

    boolean existsByProductAttributeAndProduct(ProductAttribute productAttribute, Product product);

    Page<ProductProductAttributeMapping> findAllByProductId(Long productId, Pageable pageable);
}
