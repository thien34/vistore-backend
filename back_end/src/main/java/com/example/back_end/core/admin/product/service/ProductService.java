package com.example.back_end.core.admin.product.service;

import com.example.back_end.core.admin.product.payload.response.ProductNameResponse;

import java.util.List;

public interface ProductService {
    List<ProductNameResponse> getAllProductsName();
}
