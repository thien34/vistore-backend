package com.example.back_end.service.product;

import com.example.back_end.core.admin.product.payload.request.ProductProductAttributeMappingRequest;
import com.example.back_end.core.admin.product.payload.response.ProductProductAttributeMappingDetailResponse;
import com.example.back_end.core.admin.product.payload.response.ProductProductAttributeMappingResponse;
import com.example.back_end.core.common.PageResponse;

import java.util.List;

public interface ProductProductAttributeMappingService {

    PageResponse<List<ProductProductAttributeMappingResponse>> getProductProductAttributeMappings(Long productId, int pageNo, int pageSize);

    ProductProductAttributeMappingDetailResponse getProductProductAttributeMapping(Long id);

    void addProductProductAttributeMapping(ProductProductAttributeMappingRequest request);

    void updateProductProductAttributeMapping(Long id, ProductProductAttributeMappingRequest request);

    void deleteProductProductAttributeMapping(Long id);

    List<ProductProductAttributeMappingDetailResponse> getProductProductAttributeMappingByproductId(Long productId);

}
