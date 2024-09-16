package com.example.back_end.core.admin.relatedproducts.service;

import com.example.back_end.core.admin.relatedproducts.payload.request.RelatedProductRequest;
import com.example.back_end.core.admin.relatedproducts.payload.response.RelatedProductResponse;
import com.example.back_end.core.common.PageResponse;
import java.util.List;

public interface RelatedProductServices {

    void addRelatedProducts(List<RelatedProductRequest> lstRelatedProductRequest);

    void updateRelatedProducts(Long id, RelatedProductRequest relatedProductRequest);

    void deleteRelatedProducts(List<Long> id);

    PageResponse<List<RelatedProductResponse>> getAll(Long id, Integer pageNo, Integer pageSize);
}
