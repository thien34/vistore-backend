package com.example.back_end.core.admin.category.service;

import com.example.back_end.core.admin.category.payload.request.CategoryRequest;
import com.example.back_end.core.admin.category.payload.response.CategoryNameResponse;
import com.example.back_end.core.admin.category.payload.response.CategoryResponse;
import com.example.back_end.core.common.PageResponse;

import java.util.List;

public interface CategoryService {

    void createCategory(CategoryRequest categoryRequest);

    void updateCategory(Long id, CategoryRequest request);

    PageResponse<?> getAll(String name, Boolean published, Integer pageNo, Integer pageSize);

    CategoryResponse getCategory(Long id);

    void deleteCategories(List<Long> ids);

    List<CategoryNameResponse> getCategoriesName();

}
