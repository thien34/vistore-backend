package com.example.back_end.core.admin.product.service;

import com.example.back_end.core.admin.product.payload.request.ProductSpecificationAttributeMappingRequest;
import com.example.back_end.core.admin.product.payload.request.ProductSpecificationAttributeMappingUpdateRequest;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingByIdResponse;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingByProductResponse;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingResponse;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingUpdateResponse;
import com.example.back_end.core.common.PageResponse;

import java.util.List;

public interface ProductSpecificationAttributeMappingService {

    PageResponse<List<ProductSpecificationAttributeMappingResponse>> getAllProductSpecificationAttributeMapping(
            String name, int pageNo, int pageSize
    );

    ProductSpecificationAttributeMappingResponse createProductSpecificationAttributeMapping(
            ProductSpecificationAttributeMappingRequest request);

    ProductSpecificationAttributeMappingByIdResponse getProductSpecificationAttributeMappingById(Long id);

    void deleteProductSpecificationAttribute(List<Long> ids);

    PageResponse<List<ProductSpecificationAttributeMappingByProductResponse>> getProcSpecMappingsByProductId(
            Long productId, int pageNo, int pageSize);

    ProductSpecificationAttributeMappingUpdateResponse updateProductSpecificationAttributeMapping(
            Long id, ProductSpecificationAttributeMappingUpdateRequest request);

    void deleteProductSpecificationAttributeMappingById(Long id);

}