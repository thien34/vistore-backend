package com.example.back_end.repository;

import com.example.back_end.core.admin.productTag.payload.response.ProductTagsResponse;
import com.example.back_end.entity.ProductTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {

    Page<ProductTag> findByNameContaining(String name, Pageable pageable);


    @Query("SELECT new com.example.back_end.core.admin.productTag.payload.response.ProductTagsResponse(pt.id, pt.name, COUNT(ppm.product.id)) " +
            "FROM ProductTag pt " +
            "LEFT JOIN ProductProductTagMapping ppm ON pt.id = ppm.productTag.id " +
            "WHERE (:name IS NULL OR LOWER(pt.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "GROUP BY pt.id, pt.name")
    Page<ProductTagsResponse> findAllProductTagsWithCount(@Param("name") String name, Pageable pageable);


    List<ProductTag> findByNameIn(Set<String> tagNames);
}
