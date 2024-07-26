package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeOptionResponse;
import com.example.back_end.entity.SpecificationAttributeOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" )
public interface SpecificationAttributeOptionMapper {
    @Mapping(target = "productSpecificationAttributeMappings", source = "productSpecificationAttributeMappings")
    SpecificationAttributeOptionResponse toDto(SpecificationAttributeOption specificationAttributeOption);
}
