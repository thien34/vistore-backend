package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.request.ProductProductAttributeMappingRequest;
import com.example.back_end.core.admin.product.payload.response.ProductProductAttributeMappingResponse;
import com.example.back_end.entity.ProductProductAttributeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductProductAttributeMappingMapper {

    @Mapping(source = "productAttribute.name", target = "nameProductAttribute")
    @Mapping(source = "attributeControlTypeId.label", target = "attributeControlTypeId")
    ProductProductAttributeMappingResponse toDto(ProductProductAttributeMapping attributeMapping);

    List<ProductProductAttributeMappingResponse> toDtos(List<ProductProductAttributeMapping> attributeMappings);

    @Mapping(source = "productId", target = "product.id")
    @Mapping(source = "productAttributeId", target = "productAttribute.id")
    ProductProductAttributeMapping toEntity(ProductProductAttributeMappingRequest attributeMapping);

}
