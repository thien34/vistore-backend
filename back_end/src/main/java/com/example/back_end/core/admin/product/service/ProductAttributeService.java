package com.example.back_end.core.admin.product.service;

import com.example.back_end.core.admin.product.payload.request.ProductAttributeRequest;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeNameResponse;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeResponse;
import com.example.back_end.core.common.PageResponse;

import java.util.List;

public interface ProductAttributeService {

    void createProductAttribute(ProductAttributeRequest request);

    PageResponse<?> getAllProductAttribute(String name, int pageNo, int pageSize);

    ProductAttributeResponse getProductAttributeById(Long id);

    ProductAttributeResponse updateProductAttribute(Long id, ProductAttributeRequest request);

    PageResponse<?> searchByNameName(String name, int page, int size);

    void deleteProductAttribute(List<Long> ids);

    List<ProductAttributeNameResponse> getAttributeName();

}
