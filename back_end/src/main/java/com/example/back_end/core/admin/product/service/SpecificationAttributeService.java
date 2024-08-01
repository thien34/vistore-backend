package com.example.back_end.core.admin.product.service;

import com.example.back_end.core.admin.product.payload.request.SpecificationAttributeRequest;
import com.example.back_end.core.admin.product.payload.request.SpecificationAttributeUpdateRequest;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeResponse;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeUpdateResponse;
import com.example.back_end.core.common.PageResponse;
import java.util.List;

public interface SpecificationAttributeService {
    PageResponse<?> getAllSpecificationAttribute(String name, int pageNo, int pageSize);
    SpecificationAttributeResponse createSpecificationAttribute(SpecificationAttributeRequest request);
    SpecificationAttributeUpdateResponse editSpecificationAttribute(
            Long id, SpecificationAttributeUpdateRequest request);
    void deleteSpecificationAttribute(List<Long> ids);
    SpecificationAttributeResponse getSpecificationAttributeById(Long id);
    PageResponse<?> getAttributesWithNoGroupOrInvalidGroup(int pageNo, int pageSize);
}
