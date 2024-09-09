package com.example.back_end.repository;

import com.example.back_end.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("SELECT p.id FROM Product p WHERE p.sku = :sku")
    Long findIdBySku(@Param("sku") String sku);

    boolean existsBySku(String sku);

}