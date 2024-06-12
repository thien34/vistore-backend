package com.example.back_end.core.admin.category.mapper;

import com.example.back_end.core.admin.category.payload.request.CategoryCreationRequest;
import com.example.back_end.core.admin.category.payload.request.CategoryUpdateRequest;
import com.example.back_end.core.admin.category.payload.response.CategoriesResponse;
import com.example.back_end.core.admin.category.payload.response.CategoryResponse;
import com.example.back_end.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toDto(Category category);

    CategoriesResponse toCategoriesResponse(Category category);

    @Mapping(target = "deleted", constant = "false")
    Category mapToCategory(CategoryCreationRequest request);

    void updateCategoryFromRequest(CategoryUpdateRequest request, @MappingTarget Category category);
}
