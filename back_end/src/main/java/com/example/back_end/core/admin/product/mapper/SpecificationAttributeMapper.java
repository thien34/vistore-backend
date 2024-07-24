package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeResponse;
import com.example.back_end.entity.SpecificationAttribute;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SpecificationAttributeMapper {
    @Mapping(target = "specificationAttributeGroupName", source = "specificationAttributeGroup.name")
    @Mapping(target = "specificationAttributeGroupId", source = "specificationAttributeGroup.id")
    SpecificationAttributeResponse toDto(SpecificationAttribute specificationAttribute);
}
