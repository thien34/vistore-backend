package com.example.back_end.core.admin.product.service;

import com.example.back_end.core.admin.product.payload.request.ProductFakeRequest;
import com.example.back_end.core.admin.product.payload.response.ProductFakeResponse;
import com.example.back_end.core.common.PageResponse;


public interface ProductFakeService {
    ProductFakeResponse createProductFake(ProductFakeRequest request);
     PageResponse<?> getAllProducts(int pageNo, int pageSize) ;
}
