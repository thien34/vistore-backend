package com.example.back_end.repository;

import com.example.back_end.entity.ProductVideoMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVideoMappingRepository extends JpaRepository<ProductVideoMapping, Long> {
}
