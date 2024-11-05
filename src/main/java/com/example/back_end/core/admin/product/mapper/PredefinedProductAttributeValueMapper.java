package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.request.PredefinedProductAttributeValueRequest;
import com.example.back_end.core.admin.product.payload.response.PredefinedProductAttributeValueResponse;
import com.example.back_end.entity.PredefinedProductAttributeValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PredefinedProductAttributeValueMapper {

    @Mapping(source = "productAttribute", target = "productAttribute.id")
    PredefinedProductAttributeValue toEntity(PredefinedProductAttributeValueRequest request);

    @Mapping(source = "productAttribute.id", target = "productAttribute")
    PredefinedProductAttributeValueResponse toDto(PredefinedProductAttributeValue entity);

    List<PredefinedProductAttributeValueResponse> toListDto(List<PredefinedProductAttributeValue> entities);

    @Mapping(source = "productAttribute", target = "productAttribute.id")
    void updateEntity(
            PredefinedProductAttributeValueRequest request,
            @MappingTarget PredefinedProductAttributeValue entity
    );

}