package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.request.ProductAttributeValueRequest;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeValueResponse;
import com.example.back_end.entity.ProductAttributeValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductAttributeValueMapper {

    @Mapping(source = "productAttributeMappingId", target = "productAttributeMapping.id")
    ProductAttributeValue toEntity(ProductAttributeValueRequest request);

    List<ProductAttributeValue> toEntities(List<ProductAttributeValueRequest> requests);

    ProductAttributeValueResponse toDto(ProductAttributeValue entity);

    List<ProductAttributeValueResponse> toDtos(List<ProductAttributeValue> entities);

    void updateProdAttrValueFromRequest(ProductAttributeValueRequest request, @MappingTarget ProductAttributeValue productAttributeValue);

}
