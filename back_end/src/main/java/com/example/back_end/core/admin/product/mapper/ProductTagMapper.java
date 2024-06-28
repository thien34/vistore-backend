package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.request.ProductTagRequest;
import com.example.back_end.core.admin.product.payload.response.ProductTagResponse;
import com.example.back_end.entity.ProductTag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductTagMapper {

    ProductTag toEntity(ProductTagRequest productTagRequest);

    @Mapping(target = "productId", expression = "java(getProductId(productTag))")
    ProductTagResponse toDto(ProductTag productTag);

    default Long getProductId(ProductTag productTag) {
        if (productTag.getProductProductTagMappings() != null
                && !productTag.getProductProductTagMappings().isEmpty()) {

            return productTag.getProductProductTagMappings().get(0).getProduct().getId();
        }
        return null;
    }
}
