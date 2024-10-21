package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.request.ProductAttributeRequest;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeResponse;
import com.example.back_end.entity.ProductAttribute;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductAttributeMapper {

    @Mapping(target = "id", ignore = true)
    ProductAttribute toEntity(ProductAttributeRequest dto);


    ProductAttributeResponse toDto(ProductAttribute entity);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(ProductAttributeRequest request, @MappingTarget ProductAttribute entity);

}