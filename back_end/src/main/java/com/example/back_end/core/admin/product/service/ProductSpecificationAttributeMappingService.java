package com.example.back_end.core.admin.product.service;

import com.example.back_end.core.admin.product.payload.request.ProductSpecificationAttributeMappingRequest;
import com.example.back_end.core.admin.product.payload.request.ProductSpecificationAttributeMappingUpdateRequest;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingResponse;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingUpdateResponse;
import com.example.back_end.core.common.PageResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ProductSpecificationAttributeMappingService {

    PageResponse<List<ProductSpecificationAttributeMappingResponse>> getAllProductSpecificationAttributeMapping(String name, int pageNo, int pageSize);

    ProductSpecificationAttributeMappingResponse createProductSpecificationAttributeMapping(
            ProductSpecificationAttributeMappingRequest request) throws JsonProcessingException;

    ProductSpecificationAttributeMappingResponse getProductSpecificationAttributeMappingById(Long id);

    void deleteProductSpecificationAttribute(List<Long> ids);

    PageResponse<List<ProductSpecificationAttributeMappingResponse>> getProcSpecMappingsByProductId(
            Long productId, int pageNo, int pageSize);

    ProductSpecificationAttributeMappingUpdateResponse updateProductSpecificationAttributeMapping(
            Long id, ProductSpecificationAttributeMappingUpdateRequest request) throws JsonProcessingException;

    void deleteProductSpecificationAttributeMappingById(Long id);

}
