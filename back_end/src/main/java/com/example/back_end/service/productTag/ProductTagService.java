package com.example.back_end.service.productTag;

import com.example.back_end.core.admin.productTag.payload.request.ProductTagRequest;
import com.example.back_end.core.admin.productTag.payload.request.ProductTagSearchRequest;
import com.example.back_end.core.admin.productTag.payload.request.ProductTagUpdateRequest;
import com.example.back_end.core.admin.productTag.payload.response.ProductTagsResponse;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.entity.Product;
import com.example.back_end.entity.ProductTag;

import java.util.List;

public interface ProductTagService {

    void createProductTag(ProductTagRequest request);

    void updateProductTag(Long id, ProductTagUpdateRequest request);

    PageResponse1<List<ProductTagsResponse>> getAll(ProductTagSearchRequest searchRequest);

    void delete(List<Long> ids);

    void createProductTags(List<ProductTag> productTags, Product product);

    List<ProductTag> getProductTagsByProductId(Long id);
}
