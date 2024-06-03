package com.example.back_end.repository;

import com.example.back_end.entity.RelatedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelatedProductRepository extends JpaRepository<RelatedProduct, Long> {
}
