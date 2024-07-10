package com.example.back_end.core.admin.product.service;

import com.example.back_end.core.admin.product.payload.request.ProductTagRequest;
import com.example.back_end.core.admin.product.payload.response.ProductTagResponse;
import com.example.back_end.core.common.PageResponse;

import java.util.List;

public interface ProductTagService {

    void createProductTag(ProductTagRequest request);

    PageResponse<?> getAll(String name, int pageNo, int pageSize);

    ProductTagResponse getProductTag(Long id);

    void delete(List<Long> ids);

}
