package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.request.ProductAttributeValuePictureRequest;
import com.example.back_end.entity.ProductAttributeValuePicture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductAttributeValuePictureMapper {

    @Mapping(source = "productAttributeValueId", target = "productAttributeValue.id")
    @Mapping(source = "pictureId", target = "picture.id")
    ProductAttributeValuePicture toEntity(ProductAttributeValuePictureRequest request);

}
