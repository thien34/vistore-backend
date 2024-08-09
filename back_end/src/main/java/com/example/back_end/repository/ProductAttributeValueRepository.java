package com.example.back_end.repository;

import com.example.back_end.entity.ProductAttributeValue;
import com.example.back_end.entity.ProductProductAttributeMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductAttributeValueRepository extends JpaRepository<ProductAttributeValue, Long> {

    Optional<List<ProductAttributeValue>> findAllByProductAttributeMapping(ProductProductAttributeMapping productAttributeMapping);
    List<ProductAttributeValue> findByProductAttributeMappingId(Long productAttributeMappingId);

}