package com.example.back_end.repository;

import com.example.back_end.entity.Product;
import com.example.back_end.entity.ProductAttribute;
import com.example.back_end.entity.ProductAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductAttributeValueRepository extends JpaRepository<ProductAttributeValue, Long> {

    boolean existsByProductAndProductAttributeAndValue(Product product, ProductAttribute productAttribute, String value);

    List<ProductAttributeValue> findByProductAndProductAttributeAndValue(Product product, ProductAttribute productAttribute, String value);

    Optional<ProductAttributeValue> findByProductAndProductAttribute(Product product, ProductAttribute productAttribute);

    List<ProductAttributeValue> findByParentProductId(Long parentProductId);

    List<ProductAttributeValue> findByParentProductIdAndProductIdNot(Long parentProductId, Long productId);

    void deleteByProduct(Product product);

}
