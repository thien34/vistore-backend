package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeResponse;
import com.example.back_end.entity.SpecificationAttribute;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SpecificationAttributeMapper {

    @Mapping(target = "specificationAttributeGroupName", source = "specificationAttributeGroup.name")
    @Mapping(target = "specificationAttributeGroupId", source = "specificationAttributeGroup.id")
    SpecificationAttributeResponse toDto(SpecificationAttribute specificationAttribute);

    List<SpecificationAttributeResponse> toDtoList(List<SpecificationAttribute> specificationAttributes);

}
