package com.example.back_end.repository;

import com.example.back_end.entity.ProductManufacturerMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ProductManufacturerMappingRepository extends JpaRepository<ProductManufacturerMapping, Long> {
    void deleteByProductId(Long id);

    Collection<Object> findByProductId(Long id);
}