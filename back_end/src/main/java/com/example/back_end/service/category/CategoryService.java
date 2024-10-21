package com.example.back_end.service.category;

import com.example.back_end.core.admin.category.payload.request.CategoryRequest;
import com.example.back_end.core.admin.category.payload.request.CategorySearchRequest;
import com.example.back_end.core.admin.category.payload.response.CategoryNameResponse;
import com.example.back_end.core.admin.category.payload.response.CategoryResponse;
import com.example.back_end.core.common.PageResponse1;

import java.util.List;

public interface CategoryService {

    void createCategory(CategoryRequest categoryRequest);

    void updateCategory(Long id, CategoryRequest request);

    PageResponse1<List<CategoryResponse>> getAll(CategorySearchRequest request);

    CategoryResponse getCategory(Long id);

    void deleteCategories(List<Long> ids);

    List<CategoryNameResponse> getCategoriesName();

}
