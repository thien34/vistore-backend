package com.example.back_end.repository;

import com.example.back_end.core.admin.product.payload.response.ProductTagCountProjection;
import com.example.back_end.entity.ProductProductTagMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface ProductProductTagMappingRepository extends JpaRepository<ProductProductTagMapping, Long> {

}
