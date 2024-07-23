package com.example.back_end.repository;

import com.example.back_end.entity.PredefinedProductAttributeValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PredefinedProductAttributeValueRepository extends JpaRepository<PredefinedProductAttributeValue, Long> {
    boolean existsByName(String name);
    Page<PredefinedProductAttributeValue> findByNameContaining(String name, Pageable pageable);
    List<PredefinedProductAttributeValue> findByProductAttributeId(Long productAttributeId);

}
