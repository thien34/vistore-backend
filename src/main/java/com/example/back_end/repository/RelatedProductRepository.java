package com.example.back_end.repository;

import com.example.back_end.entity.RelatedProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RelatedProductRepository extends JpaRepository<RelatedProduct, Long> {
    @Query("select re from RelatedProduct re where re.product1.id = :id ")
    Page<RelatedProduct> findByProduct1Id(@Param("id") Long id, Pageable pageable);
    Optional<RelatedProduct> findByProduct1IdAndProduct2Id(Long product1Id, Long product2Id);
}
