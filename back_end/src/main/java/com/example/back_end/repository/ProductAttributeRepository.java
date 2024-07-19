package com.example.back_end.repository;

import com.example.back_end.entity.ProductAttribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
    boolean existsByName(String name);
    Page<ProductAttribute> findByNameContaining(String name, Pageable pageable);
    boolean existsByNameAndIdNot(String name, Long id);

}
