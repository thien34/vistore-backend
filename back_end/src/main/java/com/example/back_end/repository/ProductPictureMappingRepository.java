package com.example.back_end.repository;

import com.example.back_end.entity.ProductPictureMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPictureMappingRepository extends JpaRepository<ProductPictureMapping, Long> {
}
