package com.example.back_end.repository;

import com.example.back_end.core.admin.category.payload.response.CategoryNameResponse;
import com.example.back_end.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    @Query("SELECT new com.example.back_end.core.admin.category.payload.response.CategoryNameResponse(id, name) FROM Category")
    List<CategoryNameResponse> findAllCategoriesName();

}