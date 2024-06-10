package com.example.back_end.core.admin.category.mapper;

import com.example.back_end.core.admin.category.payload.request.CategoryCreationRequest;
import com.example.back_end.core.admin.category.payload.request.CategoryUpdateRequest;
import com.example.back_end.core.admin.category.payload.response.CategoryResponse;
import com.example.back_end.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "deleted", constant = "false")
    Category mapToCategory(CategoryCreationRequest request);

    CategoryResponse toDto(Category category);

    void updateCategoryFromRequest(CategoryUpdateRequest request, @MappingTarget Category category);
}
