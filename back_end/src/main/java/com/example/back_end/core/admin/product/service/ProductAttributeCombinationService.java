package com.example.back_end.core.admin.product.service;

import com.example.back_end.core.admin.product.payload.request.ProductAttributeCombinationRequest;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeCombinationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ProductAttributeCombinationService {

    void saveOrUpdateProductAttributeCombination(ProductAttributeCombinationRequest request);

    List<ProductAttributeCombinationResponse> getByProductId(Long productId);

    void delete(Long id);


}
