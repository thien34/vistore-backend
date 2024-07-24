package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeGroupResponse;
import com.example.back_end.entity.SpecificationAttributeGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" )
public interface SpecificationAttributeGroupMapper {
    SpecificationAttributeGroupResponse toDto(SpecificationAttributeGroup specificationAttributeGroup);
}
