package com.example.back_end.repository;

import com.example.back_end.entity.ProductReviewHelpfulness;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReviewHelpfulnessRepository extends JpaRepository<ProductReviewHelpfulness, Long> {
}
