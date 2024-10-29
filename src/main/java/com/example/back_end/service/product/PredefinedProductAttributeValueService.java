package com.example.back_end.service.product;

import com.example.back_end.core.admin.product.payload.request.PredefinedProductAttributeValueRequest;
import com.example.back_end.core.admin.product.payload.response.PredefinedProductAttributeValueResponse;
import com.example.back_end.core.common.PageResponse;

import java.util.List;

public interface PredefinedProductAttributeValueService {

    void createProductAttributeValue(PredefinedProductAttributeValueRequest request);

    PageResponse<List<PredefinedProductAttributeValueResponse>> getAllPredefinedProductAttributeValue(
            String name, int pageNo, int pageSize
    );

    PredefinedProductAttributeValueResponse getPredefinedAttributeValueById(Long id);

    void updatePredefinedAttributeValue(
            Long id, PredefinedProductAttributeValueRequest request
    );

    void deletePredefinedAttributeValue(Long id);

}