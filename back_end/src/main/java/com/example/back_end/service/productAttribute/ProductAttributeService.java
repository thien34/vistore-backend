package com.example.back_end.service.productAttribute;

import com.example.back_end.core.admin.productAttribute.payload.request.ProductAttributeRequest;
import com.example.back_end.core.admin.productAttribute.payload.response.ProductAttributeNameResponse;
import com.example.back_end.core.admin.productAttribute.payload.response.ProductAttributeResponse;
import com.example.back_end.core.admin.productAttribute.payload.request.ProdAttrSearchRequest;
import com.example.back_end.core.common.PageResponse1;

import java.util.List;

public interface ProductAttributeService {

    void createProductAttribute(ProductAttributeRequest request);

    void updateProductAttribute(Long id, ProductAttributeRequest request);

    PageResponse1<List<ProductAttributeResponse>> getAllProductAttribute(ProdAttrSearchRequest searchRequest);

    ProductAttributeResponse getProductAttribute(Long id);

    void deleteProductAttributes(List<Long> ids);

    List<ProductAttributeNameResponse> getAttributesName();

}