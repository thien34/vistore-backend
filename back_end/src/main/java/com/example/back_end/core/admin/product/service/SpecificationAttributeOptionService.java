package com.example.back_end.core.admin.product.service;

import com.example.back_end.core.admin.product.payload.request.SpecificationAttributeOptionRequest;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeOptionResponse;
import com.example.back_end.core.common.PageResponse;

public interface SpecificationAttributeOptionService {
    PageResponse<?> getAllSpecificationAttributeOption(String name, int pageNo, int pageSize);
    SpecificationAttributeOptionResponse createSpecificationAttributeOption(SpecificationAttributeOptionRequest request);
}
