package com.example.back_end.core.admin.productTag.mapper;

import com.example.back_end.core.admin.productTag.payload.request.ProductTagRequest;
import com.example.back_end.core.admin.productTag.payload.request.ProductTagUpdateRequest;
import com.example.back_end.core.admin.productTag.payload.response.ProductTagResponse;
import com.example.back_end.entity.ProductTag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductTagMapper {

    ProductTag toEntity(ProductTagRequest productTagRequest);

    @Mapping(target = "productId", expression = "java(getProductId(productTag))")
    ProductTagResponse toDto(ProductTag productTag);

    List<ProductTagResponse> toDtos(List<ProductTag> productTags);

    ProductTag toEntity(ProductTagUpdateRequest productTagResponse);

    default Long getProductId(ProductTag productTag) {
        if (productTag.getProductProductTagMappings() != null
                && !productTag.getProductProductTagMappings().isEmpty()) {

            return productTag.getProductProductTagMappings().get(0).getProduct().getId();
        }
        return null;
    }

}
