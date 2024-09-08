package com.example.back_end.repository;

import com.example.back_end.entity.Product;
import com.example.back_end.entity.ProductProductTagMapping;
import com.example.back_end.entity.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductProductTagMappingRepository extends JpaRepository<ProductProductTagMapping, Long> {

    boolean existsByProductAndProductTag(Product product, ProductTag productTag);
    List<ProductProductTagMapping> findByProductId(Long productId);

}
