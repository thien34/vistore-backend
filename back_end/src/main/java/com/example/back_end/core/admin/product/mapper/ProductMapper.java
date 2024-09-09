package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.request.ProductRequest;
import com.example.back_end.core.admin.product.payload.response.ProductFakeResponse;
import com.example.back_end.core.admin.product.payload.response.ProductResponse;
import com.example.back_end.entity.Product;
import com.example.back_end.entity.ProductPictureMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Comparator;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductFakeResponse toDto(Product product);

    List<ProductFakeResponse> toDtoList(List<Product> products);

    Product toEntity(ProductRequest productRequest);

    List<ProductResponse> toResponseList(List<Product> content);

    @Mapping(target = "imageUrl", expression = "java(getImageUrl(product))")
    ProductResponse toResponse(Product product);

    default String getImageUrl(Product product) {

        return product.getProductPictureMappings().stream()
                .sorted(Comparator.comparingInt(ProductPictureMapping::getDisplayOrder))
                .map(mapping -> mapping.getPicture().getLinkImg())
                .findFirst()
                .orElse(null);
    }

}
