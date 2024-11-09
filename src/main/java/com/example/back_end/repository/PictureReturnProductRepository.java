package com.example.back_end.repository;

import com.example.back_end.entity.PictureReturnProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PictureReturnProductRepository extends JpaRepository<PictureReturnProduct, Long> {
    @Query(value = "select prp from PictureReturnProduct prp where prp.returnItem.id = :returnItemId ")
    List<PictureReturnProduct> findByReturnItemId(Long returnItemId);
}
