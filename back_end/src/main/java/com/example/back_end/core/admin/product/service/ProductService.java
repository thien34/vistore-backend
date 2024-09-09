package com.example.back_end.core.admin.product.service;

import com.example.back_end.core.admin.product.payload.request.ProductFilter;
import com.example.back_end.core.admin.product.payload.request.ProductRequest;
import com.example.back_end.core.admin.product.payload.response.ProductFakeResponse;
import com.example.back_end.core.admin.product.payload.response.ProductNameResponse;
import com.example.back_end.core.admin.product.payload.response.ProductResponse;
import com.example.back_end.core.common.PageResponse;

import java.util.List;

public interface ProductService {

    List<ProductNameResponse> getAllProductsName();

    PageResponse<List<ProductFakeResponse>> getAllProducts(int pageNo, int pageSize);

    PageResponse<List<ProductResponse>> getAllProducts(int pageNo, int pageSize, ProductFilter productFilter);

    Long getIdProductBySku(String sku);

    Long createOrUpdateProduct(ProductRequest request);

    ProductResponse getProductById(long id);

    void deleteProductById(long id);

}
