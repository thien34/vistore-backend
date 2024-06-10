package com.example.back_end.repository;

import com.example.back_end.entity.ProductTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {
    Page<ProductTag> findByNameContaining(String name, Pageable pageable);
}
