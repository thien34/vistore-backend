package com.example.back_end.repository;

import com.example.back_end.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    boolean existsByName(String name);

    boolean existsBySlug(String slug);

    List<Category> findByCategoryParentIsNull();

    Category findBySlug(String slug);

    List<Category> findByCategoryParent(Category category);

}