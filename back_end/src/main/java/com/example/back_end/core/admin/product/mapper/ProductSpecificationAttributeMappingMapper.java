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
    @Mapping(source = "specificationAttributeOption.name", target = "specificationAttributeOptionName")
    @Mapping(target = "attributeType", source = "attributeType")
    @Mapping(target = "specificationAttributeId",source = "specificationAttributeOption.specificationAttribute.id")
    @Mapping(target = "specificationAttributeName",source = "specificationAttributeOption.specificationAttribute.name")
    ProductSpecificationAttributeMappingResponse toDto(ProductSpecificationAttributeMapping mapping);

    List<ProductSpecificationAttributeMappingResponse> toDto(List<ProductSpecificationAttributeMapping> productSpecificationAttributeMappings);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "specificationAttributeOption", ignore = true)
    @Mapping(target = "attributeType", source = "attributeType")
    ProductSpecificationAttributeMapping toEntity(ProductSpecificationAttributeMappingRequest productSpecificationAttributeMappingRequest);
}
