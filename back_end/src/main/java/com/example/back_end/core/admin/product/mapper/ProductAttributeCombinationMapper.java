package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.request.ProductAttributeCombinationRequest;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeCombinationResponse;
import com.example.back_end.entity.ProductAttributeCombination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ProductAttributeCombinationMapper.class)

public interface ProductAttributeCombinationMapper {

    @Mapping(source = "productId", target = "product.id")
    ProductAttributeCombination toEntity(ProductAttributeCombinationRequest dto);


    @Mapping(source = "picture.linkImg", target = "pictureUrl")
    ProductAttributeCombinationResponse toDto(ProductAttributeCombination entity);

    void updateEntityFromRequest(@MappingTarget ProductAttributeCombination existingCombination, ProductAttributeCombinationRequest request);

}
