package com.example.back_end.repository;

import com.example.back_end.core.admin.productAttribute.payload.response.ProductAttributeNameResponse;
import com.example.back_end.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long>, JpaSpecificationExecutor<ProductAttribute> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    @Query("SELECT new com.example.back_end.core.admin.productAttribute.payload.response.ProductAttributeNameResponse(pa.id, pa.name)" +
            " FROM ProductAttribute pa")
    List<ProductAttributeNameResponse> findAllNameProductAttribute();

}