package com.example.back_end.repository;

import com.example.back_end.core.admin.product.payload.response.ProductAttributeNameResponse;
import com.example.back_end.entity.ProductAttribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {

    boolean existsByName(String name);

    Page<ProductAttribute> findByNameContaining(String name, Pageable pageable);

    boolean existsByNameAndIdNot(String name, Long id);

    @Query("SELECT new com.example.back_end.core.admin.product.payload.response.ProductAttributeNameResponse(pa.id, pa.name)" +
            " FROM ProductAttribute pa")
    List<ProductAttributeNameResponse> findAllNameProductAttribute();

}
