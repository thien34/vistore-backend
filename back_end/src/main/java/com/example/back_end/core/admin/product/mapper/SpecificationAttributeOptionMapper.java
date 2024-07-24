package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeOptionResponse;
import com.example.back_end.entity.SpecificationAttributeOption;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" )
public interface SpecificationAttributeOptionMapper {
    SpecificationAttributeOptionResponse toDto(SpecificationAttributeOption specificationAttributeOption);
}
