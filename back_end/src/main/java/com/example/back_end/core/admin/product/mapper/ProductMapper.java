package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.response.ProductFakeResponse;
import com.example.back_end.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductFakeResponse toDto(Product product);
}
