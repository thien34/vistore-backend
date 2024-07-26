package com.example.back_end.core.admin.product.service;

import com.example.back_end.core.admin.product.payload.request.SpecificationAttributeGroupRequest;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeGroupResponse;
import com.example.back_end.core.common.PageListResponse;

import java.util.List;

public interface SpecificationAttributeGroupService {
    PageListResponse<?> getAllSpecificationAttributeGroup(String name, int pageNo, int pageSize);
    SpecificationAttributeGroupResponse createSpecificationAttributeGroup(SpecificationAttributeGroupRequest request);
    SpecificationAttributeGroupResponse getSpecificationAttributeGroupById(Long id);
    void deleteSpecificationAttributeGroup(List<Long> ids);
}
