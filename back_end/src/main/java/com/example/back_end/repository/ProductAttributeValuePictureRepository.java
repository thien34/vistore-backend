package com.example.back_end.repository;

import com.example.back_end.entity.ProductAttributeValuePicture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductAttributeValuePictureRepository extends JpaRepository<ProductAttributeValuePicture, Long> {

    List<ProductAttributeValuePicture> findAllByProductAttributeValueId(Long productAttributeValueId);

}