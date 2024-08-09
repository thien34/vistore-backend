package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.mapper.ProductAttributeValueMapper;
import com.example.back_end.core.admin.product.payload.request.ProductAttributeValueRequest;
import com.example.back_end.core.admin.product.service.ProductAttributeValueService;
import com.example.back_end.entity.ProductAttributeValue;
import com.example.back_end.entity.ProductProductAttributeMapping;
import com.example.back_end.infrastructure.exception.ResourceNotFoundException;
import com.example.back_end.repository.ProductAttributeValueRepository;
import com.example.back_end.repository.ProductProductAttributeMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAttributeValueServiceImpl implements ProductAttributeValueService {

    private final ProductAttributeValueRepository productAttributeValueRepository;
    private final ProductAttributeValueMapper productAttributeValueMapper;
    private final ProductProductAttributeMappingRepository productProductAttributeMappingRepository;

    @Override
    @Transactional
    public void createProductAttributeValue(List<ProductAttributeValueRequest> request, Long productAttributeMappingId) {

        ProductProductAttributeMapping productProductAttributeMapping = productProductAttributeMappingRepository
                .findById(productAttributeMappingId)
                .orElseThrow(() -> new ResourceNotFoundException("Product attribute mapping not found: " + productAttributeMappingId));

        List<ProductAttributeValue> productAttributeValues = request.stream()
                .map(productAttributeValueRequest -> {
                    ProductAttributeValue productAttributeValue = productAttributeValueMapper.toEntity(productAttributeValueRequest);
                    productAttributeValue.setProductAttributeMapping(productProductAttributeMapping);
                    return productAttributeValue;
                })
                .toList();

        productAttributeValueRepository.saveAll(productAttributeValues);
    }
}
