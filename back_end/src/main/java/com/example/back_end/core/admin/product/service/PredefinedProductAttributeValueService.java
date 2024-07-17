package com.example.back_end.core.admin.product.service;

import com.example.back_end.core.admin.product.payload.request.PredefinedProductAttributeValueRequest;
import com.example.back_end.core.admin.product.payload.response.PredefinedProductAttributeValueResponse;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.PredefinedProductAttributeValue;
import java.util.List;
public interface PredefinedProductAttributeValueService {
    PredefinedProductAttributeValue createProductAttributeValue(PredefinedProductAttributeValueRequest request);
    PageResponse<List<PredefinedProductAttributeValueResponse>> getAllPredefinedProductAttributeValue(String name, int pageNo, int pageSize);
    PredefinedProductAttributeValueResponse getPredefinedAttributeValueById(Long id);
    PredefinedProductAttributeValueResponse updatePredefinedAttributeValue(Long id, PredefinedProductAttributeValueRequest request);
    void deletePredefinedAttributeValue(Long id);

}
