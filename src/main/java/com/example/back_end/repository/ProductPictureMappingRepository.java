package com.example.back_end.repository;

import com.example.back_end.entity.ProductPictureMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPictureMappingRepository extends JpaRepository<ProductPictureMapping, Long> {

    Page<ProductPictureMapping> findByProductId(Long productId, Pageable pageable);

}
