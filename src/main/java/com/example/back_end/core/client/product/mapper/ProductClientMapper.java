package com.example.back_end.core.client.product.mapper;

import com.example.back_end.core.client.product.payload.reponse.ProductDetailResponse;
import com.example.back_end.core.client.product.payload.reponse.ProductResponse;
import com.example.back_end.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductClientMapper {

    @Mapping(target = "categoryName", source = "category.name")
    ProductResponse toDto(Product product);

    List<ProductResponse> toDto(List<Product> products);

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "images", ignore = true)
    ProductDetailResponse toDetailDto(Product product);

}
