package com.example.back_end.repository;

import com.example.back_end.entity.Product;
import com.example.back_end.entity.ProductAttribute;
import com.example.back_end.entity.ProductAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributeValueRepository extends JpaRepository<ProductAttributeValue, Long> {
    boolean existsByProductAndProductAttributeAndValue(Product product, ProductAttribute productAttribute, String value);

}
