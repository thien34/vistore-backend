package com.example.back_end.repository;

import com.example.back_end.entity.Product;
import com.example.back_end.entity.ProductAttributeCombination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductAttributeCombinationRepository extends JpaRepository<ProductAttributeCombination, Long> {

    List<ProductAttributeCombination> findByProductId(Long productId);

    List<ProductAttributeCombination> findByProduct(Product product);
}