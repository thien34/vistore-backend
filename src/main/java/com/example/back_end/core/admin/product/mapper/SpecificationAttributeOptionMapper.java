package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeOptionResponse;
import com.example.back_end.entity.SpecificationAttributeOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SpecificationAttributeOptionMapper {

    @Mapping(target = "specificationAttributeId", source = "specificationAttribute.id")
    SpecificationAttributeOptionResponse toDto(SpecificationAttributeOption specificationAttributeOption);

    List<SpecificationAttributeOptionResponse> toDtoList(
            List<SpecificationAttributeOption> specificationAttributeOptions);

}
