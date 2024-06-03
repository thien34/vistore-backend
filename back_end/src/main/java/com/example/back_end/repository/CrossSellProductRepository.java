package com.example.back_end.repository;

import com.example.back_end.entity.CrossSellProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrossSellProductRepository extends JpaRepository<CrossSellProduct, Long> {
}