package com.example.back_end.core.admin.product.service;

import com.example.back_end.core.admin.product.payload.request.ProductAttributeRequest;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeResponse;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.ProductAttribute;
import org.springframework.stereotype.Service;

@Service
public interface ProductAttributeService {
    ProductAttribute createProductAttribute(ProductAttributeRequest request);
    PageResponse<?> getAll(String name, int pageNo, int pageSize);
    ProductAttributeResponse getProductAttributeById(Long id);
    ProductAttributeResponse updateProductAttribute(Long id, ProductAttributeRequest request);
    void deleteProductAttribute(Long id);

}
