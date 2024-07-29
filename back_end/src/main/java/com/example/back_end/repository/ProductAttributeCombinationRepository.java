package com.example.back_end.repository;

import com.example.back_end.entity.ProductAttributeCombination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductAttributeCombinationRepository extends JpaRepository<ProductAttributeCombination, Long> {

    boolean existsBySku(String sku);


//    Optional<ProductAttributeCombination> findByProductId(Long productId);

    List<ProductAttributeCombination> findByProductId(Long productId);



}