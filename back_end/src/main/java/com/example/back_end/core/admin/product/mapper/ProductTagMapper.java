package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.request.ProductTagRequestDto;
import com.example.back_end.core.admin.product.payload.response.ProductTagDtoResponse;
import com.example.back_end.entity.ProductTag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ProductTagMapper {

    ProductTag toEntity(ProductTagRequestDto productTagRequestDto);
    @Mapping(target = "productId", expression = "java(getProductId(productTag))")
    ProductTagDtoResponse toDto(ProductTag productTag);


    default Long getProductId(ProductTag productTag) {
        if (productTag.getProductProductTagMappings() != null && !productTag.getProductProductTagMappings().isEmpty()) {
            return productTag.getProductProductTagMappings().get(0).getProduct().getId();
        }
        return null;
    }
}
