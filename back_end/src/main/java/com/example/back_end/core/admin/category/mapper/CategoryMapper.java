package com.example.back_end.core.admin.category.mapper;

import com.example.back_end.core.admin.category.payload.request.CategoryRequest;
import com.example.back_end.core.admin.category.payload.response.CategoriesResponse;
import com.example.back_end.core.admin.category.payload.response.CategoryResponse;
import com.example.back_end.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "categoryParent.id", target = "categoryParentId")
    @Mapping(source = "picture.id", target = "pictureId")
    CategoryResponse toDto(Category category);

    CategoriesResponse toCategoriesResponse(Category category);

    List<CategoriesResponse> toCategoriesResponseList(List<Category> categories);

    @Mapping(target = "deleted", constant = "false")
    @Mapping(target = "name", source = "name", qualifiedByName = "trimName")
    @Mapping(target = "description", source = "description", qualifiedByName = "trimName")
    Category mapToCategory(CategoryRequest request);

    @Mapping(target = "name", source = "name", qualifiedByName = "trimName")
    @Mapping(target = "description", source = "description", qualifiedByName = "trimName")
    void updateCategoryFromRequest(CategoryRequest request, @MappingTarget Category category);

    @Named("trimName")
    default String trimName(String name) {
        return name != null ? name.trim() : null;
    }

}
