package com.example.back_end.repository;

import com.example.back_end.entity.ProductVideoMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVideoMappingRepository extends JpaRepository<ProductVideoMapping, Long> {

    Page<ProductVideoMapping> findByProductId(Long productId, Pageable pageable);

}
