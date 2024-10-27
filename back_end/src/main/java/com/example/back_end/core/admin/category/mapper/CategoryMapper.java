package com.example.back_end.core.admin.category.mapper;

import com.example.back_end.core.admin.category.payload.request.CategoryRequest;
import com.example.back_end.core.admin.category.payload.response.CategoryNameResponse;
import com.example.back_end.core.admin.category.payload.response.CategoryResponse;
import com.example.back_end.entity.Category;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(source = "categoryParent.id", target = "categoryParentId")
    CategoryResponse toDto(Category category);

    List<CategoryResponse> toDtos(List<Category> categories);

    @Mapping(target = "children", expression = "java(new ArrayList<>())")
    CategoryNameResponse toTreeDto(Category category);

    @Mapping(target = "categoryParent", source = "categoryParentId", qualifiedByName = "toCategory")
    Category toEntity(CategoryRequest request);

    @Mapping(target = "categoryParent", source = "categoryParentId", qualifiedByName = "toCategory")
    void updateCategoryFromRequest(CategoryRequest request, @MappingTarget Category category);

    @Named("toCategory")
    default Category toCategory(Long categoryParentId) {
        if (categoryParentId == null) {
            return null;
        }
        Category category = new Category();
        category.setId(categoryParentId);
        return category;
    }
}
