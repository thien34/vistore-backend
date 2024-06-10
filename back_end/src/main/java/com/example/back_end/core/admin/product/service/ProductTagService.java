package com.example.back_end.core.admin.product.service;

import com.example.back_end.core.admin.product.payload.request.ProductTagRequestDto;
import com.example.back_end.core.admin.product.payload.response.ProductTagDtoResponse;
import com.example.back_end.core.common.PageResponse;

import java.util.List;

public interface ProductTagService {

    void createProductTag(ProductTagRequestDto request );

    void updateProductTag(ProductTagRequestDto request, Long id);

    PageResponse<?> getAll(String name, int pageNo, int pageSize);

    ProductTagDtoResponse getProductTag(Long id);

    void delete(List<Long> ids);
}
