package com.example.back_end.core.admin.relatedproducts.service;

import com.example.back_end.core.admin.relatedproducts.payload.request.RelatedProductRequest;
import com.example.back_end.core.admin.relatedproducts.payload.response.RelatedProductResponse;
import com.example.back_end.core.common.PageResponse;

import java.util.List;

public interface RelatedProductServices {

    void AddRelatedProducts(List<RelatedProductRequest> lstRelatedProductRequest);

    void UpdateRelatedProducts(Long id, RelatedProductRequest RelatedProductRequest);

    void DeleteRelatedProducts(List<Long> id);

    PageResponse<List<RelatedProductResponse>> getAll(Long id, Integer pageNo, Integer pageSize);
}
