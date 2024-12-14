package com.example.back_end.repository;

import com.example.back_end.core.admin.statistical.payload.ProductSaleResponse;
import com.example.back_end.entity.Category;
import com.example.back_end.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    boolean existsBySku(String sku);

    boolean existsByGtin(String gtin);

    @Query("SELECT p FROM Product p WHERE p.parentProductId IN :parentIds")
    List<Product> findByParentProductIds(@Param("parentIds") List<Long> parentIds);

    List<Product> findByParentProductIdIsNull();

    List<Product> findByParentProductIdIsNullAndCategoryIn(List<Category> category);

    List<Product> findByParentProductId(Long parentProductId);

    Product findBySlug(String slug);
    @Query("SELECT new com.example.back_end.core.admin.statistical.payload.ProductSaleResponse(oi.product.id, oi.product.name, SUM(oi.quantity), SUM(oi.priceTotal)) " +
            "FROM OrderItem oi " +
            "JOIN oi.product p " +
            "JOIN oi.order o " +
            "WHERE o.paidDateUtc BETWEEN :startDate AND :endDate " +
            "GROUP BY oi.product.id " +
            "ORDER BY SUM(oi.priceTotal) DESC")
    List<ProductSaleResponse> findTopSellingProducts(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);

    @Query("SELECT p FROM Product p WHERE p.quantity < 100")
    List<Product> findOutOfStockProducts();

}