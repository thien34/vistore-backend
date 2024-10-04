package com.example.back_end.service.relatedProducts;

import com.example.back_end.core.admin.relatedProducts.payload.request.RelatedProductRequest;
import com.example.back_end.core.admin.relatedProducts.payload.response.RelatedProductResponse;
import com.example.back_end.core.common.PageResponse;

import java.util.List;

public interface RelatedProductServices {

    void addRelatedProducts(List<RelatedProductRequest> lstRelatedProductRequest);

    void updateRelatedProducts(Long id, RelatedProductRequest relatedProductRequest);

    void deleteRelatedProducts(List<Long> id);

    PageResponse<List<RelatedProductResponse>> getAll(Long id, Integer pageNo, Integer pageSize);

}
