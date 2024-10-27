package com.example.back_end.service.product.impl;

import com.example.back_end.core.admin.product.payload.request.ProductAttributeValueRequest;
import com.example.back_end.core.admin.product.payload.response.AttributeValueResponse;
import com.example.back_end.entity.Product;
import com.example.back_end.entity.ProductAttributeValue;
import com.example.back_end.repository.ProductAttributeValueRepository;
import com.example.back_end.repository.ProductRepository;
import com.example.back_end.service.product.ProductAttributeValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAttributeValueServiceImpl implements ProductAttributeValueService {

    private final ProductAttributeValueRepository productAttributeValueRepository;
    private final ProductRepository productRepository;
    @Override
    public void createProductAttributeValues(List<ProductAttributeValueRequest> requests, Long productAttributeMappingId) {

    }

    @Override
    public void createProductAttributeValue(ProductAttributeValueRequest request) {

    }

    @Override
    public void updateProductAttributeValue(Long id, ProductAttributeValueRequest request) {

    }

    @Override
    public List<AttributeValueResponse> getProductAttributeValues(Long productId, Long attributeId) {
        Product product = productRepository.findById(productId).orElse(null);

        if (product != null) {
            List<Product> products = productRepository.findAll().stream()
                    .filter(p -> p.getParentProductId() != null && p.getParentProductId().equals(product.getParentProductId()))
                    .toList();

            List<ProductAttributeValue> attributeValues = productAttributeValueRepository.findAll();

            return attributeValues.stream()
                    .filter(value -> products.stream().anyMatch(p -> p.getId().equals(value.getProduct().getId())) &&
                            (attributeId == null || value.getProductAttribute().getId().equals(attributeId)))
                    .map(value -> new AttributeValueResponse(value.getId(), value.getValue()))
                    .toList();
        }

        return Collections.emptyList();
    }

}
