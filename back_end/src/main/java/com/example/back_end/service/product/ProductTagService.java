package com.example.back_end.service.product;

import com.example.back_end.core.admin.product.payload.request.ProductTagRequest;
import com.example.back_end.core.admin.product.payload.request.ProductTagUpdateRequest;
import com.example.back_end.core.admin.product.payload.response.ProductTagResponse;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.Product;
import com.example.back_end.entity.ProductTag;

import java.util.List;

public interface ProductTagService {

    void createProductTag(ProductTagRequest request);

    void updateProductTag(Long id, ProductTagUpdateRequest request);

    PageResponse<List<ProductTagResponse>> getAll(String name, int pageNo, int pageSize);

    void delete(List<Long> ids);

    void createProductTags(List<ProductTag> productTags, Product product);

    List<ProductTag> getProductTagsByProductId(Long id);
}
