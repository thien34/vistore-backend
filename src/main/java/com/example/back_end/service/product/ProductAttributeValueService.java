package com.example.back_end.service.product;

import com.example.back_end.core.admin.product.payload.request.ProductAttributeValueRequest;
import com.example.back_end.core.admin.product.payload.response.AttributeValueResponse;

import java.util.List;

public interface ProductAttributeValueService {

    void createProductAttributeValues(List<ProductAttributeValueRequest> requests, Long productAttributeMappingId);

    void createProductAttributeValue(ProductAttributeValueRequest request);

    void updateProductAttributeValue(Long id, ProductAttributeValueRequest request);

    List<AttributeValueResponse> getProductAttributeValues(Long productId, Long attributeId);
}
