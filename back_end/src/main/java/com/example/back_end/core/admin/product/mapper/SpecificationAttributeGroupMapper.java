package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeGroupResponse;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeResponse;
import com.example.back_end.entity.SpecificationAttribute;
import com.example.back_end.entity.SpecificationAttributeGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.Comparator;
import java.util.List;

@Mapper(componentModel = "spring" )
public interface SpecificationAttributeGroupMapper {
    @Mapping(target = "specificationAttributes", expression = "java(mapSpecificationAttributes(specificationAttributeGroup.getSpecificationAttributes()))")
    SpecificationAttributeGroupResponse toDto(SpecificationAttributeGroup specificationAttributeGroup);

    // Custom method to map specification attributes to their responses
    default List<SpecificationAttributeResponse> mapSpecificationAttributes(List<SpecificationAttribute> specificationAttributes) {
        return specificationAttributes.stream()
                .map(SpecificationAttributeResponse::mapToResponse)
                .sorted(Comparator.comparing(SpecificationAttributeResponse::getDisplayOrder).reversed())
                .toList();
    }
}
