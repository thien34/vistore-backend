package com.example.back_end.core.admin.product.service;

import com.example.back_end.core.admin.product.payload.request.ProductVideoMappingRequest;
import com.example.back_end.core.admin.product.payload.request.ProductVideoMappingUpdateRequest;
import com.example.back_end.core.admin.product.payload.response.ProductVideoMappingResponse;
import com.example.back_end.core.common.PageResponse;

import java.util.List;

public interface ProductVideoMappingService {
    ProductVideoMappingResponse createMapping(ProductVideoMappingRequest dto);

    PageResponse<List<ProductVideoMappingResponse>> getMappingsByProductId(Long productId, int pageNo, int pageSize);

    ProductVideoMappingResponse updateMapping(Long id, ProductVideoMappingUpdateRequest dto);

    void deleteMapping(Long id);
}
