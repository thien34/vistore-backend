package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.response.ProductPictureMappingResponse;
import com.example.back_end.entity.ProductPictureMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductPictureMappingMapper {
    @Mapping(target = "pictureUrl",source = "picture.linkImg")
    @Mapping(target = "productId",source = "product.id")
    ProductPictureMappingResponse toDto(ProductPictureMapping mapping);
}
