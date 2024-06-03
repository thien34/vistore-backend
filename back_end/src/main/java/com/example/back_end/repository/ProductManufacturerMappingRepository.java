package com.example.back_end.repository;

import com.example.back_end.entity.ProductManufacturerMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductManufacturerMappingRepository extends JpaRepository<ProductManufacturerMapping, Long> {
}