package com.example.back_end.core.client.category.mapper;

import com.example.back_end.core.client.category.payload.response.CategoryResponse;
import com.example.back_end.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryClientMapper {

    CategoryResponse toDto(Category category);

    List<CategoryResponse> toDtos(List<Category> category);

}
