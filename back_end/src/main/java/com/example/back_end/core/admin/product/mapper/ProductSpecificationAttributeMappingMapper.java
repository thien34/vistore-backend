package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.request.ProductSpecificationAttributeMappingRequest;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingResponse;
import com.example.back_end.entity.ProductSpecificationAttributeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductSpecificationAttributeMappingMapper {
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "specificationAttributeOption.id", target = "specificationAttributeOptionId")
    ProductSpecificationAttributeMappingResponse toDto(ProductSpecificationAttributeMapping mapping);

    List<ProductSpecificationAttributeMappingResponse> toDto(List<ProductSpecificationAttributeMapping> productSpecificationAttributeMappings);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "specificationAttributeOption", ignore = true)
    ProductSpecificationAttributeMapping toEntity(ProductSpecificationAttributeMappingRequest productSpecificationAttributeMappingRequest);

}
