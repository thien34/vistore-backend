package com.example.back_end.core.admin.product.mapper;

import com.example.back_end.core.admin.product.payload.request.ProductRequest;
import com.example.back_end.core.admin.product.payload.response.ProductFakeResponse;
import com.example.back_end.core.admin.product.payload.response.ProductResponse;
import com.example.back_end.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductFakeResponse toDto(Product product);

    List<ProductFakeResponse> toDtoList(List<Product> products);
    
    Product toEntity(ProductRequest productRequest);

    ProductResponse toResponse(Product product);
}
