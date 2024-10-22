package com.example.back_end.core.admin.productAttribute.mapper;

import com.example.back_end.core.admin.productAttribute.payload.request.ProductAttributeRequest;
import com.example.back_end.core.admin.productAttribute.payload.response.ProductAttributeResponse;
import com.example.back_end.entity.ProductAttribute;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductAttributeMapper {

    ProductAttribute toEntity(ProductAttributeRequest productAttributeRequest);

    ProductAttributeResponse toDto(ProductAttribute entity);

    List<ProductAttributeResponse> toDtos(List<ProductAttribute> entities);

    void updateEntityFromRequest(ProductAttributeRequest request, @MappingTarget ProductAttribute entity);

}