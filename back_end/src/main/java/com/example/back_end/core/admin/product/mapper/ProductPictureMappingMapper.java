package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.response.ProductPictureMappingResponse;
import com.example.back_end.entity.ProductPictureMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductPictureMappingMapper {
    @Mapping(target = "pictureUrl",source = "picture.linkImg")
    @Mapping(target = "productId",source = "product.id")
    ProductPictureMappingResponse toDto(ProductPictureMapping mapping);
}
