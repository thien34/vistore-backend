package com.example.back_end.core.admin.product.service;

import com.example.back_end.core.admin.product.payload.request.ProductAttributeValuePictureRequest;

import java.util.List;
import java.util.Map;

public interface ProductAttributeValuePictureService {

    void createProductAttributePictureValue(Map<Long, List<ProductAttributeValuePictureRequest>> requests);

}
