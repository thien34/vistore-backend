package com.example.back_end.core.admin.product.service;

import com.example.back_end.core.admin.product.payload.request.SpecificationAttributeOptionRequest;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeOptionResponse;
import com.example.back_end.core.common.PageResponse;

import java.util.List;

public interface SpecificationAttributeOptionService {

    PageResponse<List<SpecificationAttributeOptionResponse>> getAllSpecificationAttributeOption(String name, int pageNo, int pageSize);

    SpecificationAttributeOptionResponse createSpecificationAttributeOption(
            SpecificationAttributeOptionRequest request);

    void deleteSpecificationAttributeOption(List<Long> ids);

}
