package com.example.back_end.repository;

import com.example.back_end.entity.Category;
import com.example.back_end.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    boolean existsBySku(String sku);

    boolean existsByGtin(String gtin);

    @Query("SELECT p FROM Product p WHERE p.parentProductId IN :parentIds")
    List<Product> findByParentProductIds(@Param("parentIds") List<Long> parentIds);

    List<Product> findByParentProductIdIsNull();

    List<Product> findByParentProductIdIsNullAndCategoryIn(List<Category> category);

    List<Product> findByParentProductId(Long parentProductId);

}