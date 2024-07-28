package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.mapper.ProductAttributeValueMapper;
import com.example.back_end.core.admin.product.mapper.ProductAttributeValuePictureMapper;
import com.example.back_end.core.admin.product.payload.request.ProductAttributeValuePictureRequest;
import com.example.back_end.core.admin.product.payload.request.ProductAttributeValueRequest;
import com.example.back_end.core.admin.product.service.ProductAttributeValueService;
import com.example.back_end.entity.ProductAttributeValue;
import com.example.back_end.entity.ProductAttributeValuePicture;
import com.example.back_end.entity.ProductProductAttributeMapping;
import com.example.back_end.infrastructure.exception.ResourceNotFoundException;
import com.example.back_end.repository.ProductAttributeValuePictureRepository;
import com.example.back_end.repository.ProductAttributeValueRepository;
import com.example.back_end.repository.ProductProductAttributeMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAttributeValueServiceImpl implements ProductAttributeValueService {

    private final ProductAttributeValueRepository productAttributeValueRepository;
    private final ProductAttributeValueMapper productAttributeValueMapper;
    private final ProductProductAttributeMappingRepository productProductAttributeMappingRepository;
    private final ProductAttributeValuePictureRepository productAttributeValuePictureRepository;
    private final ProductAttributeValuePictureMapper productAttributeValuePictureMapper;

    @Override
    @Transactional
    public void createProductAttributeValue(List<ProductAttributeValueRequest> request, Long productAttributeMappingId) {

        ProductProductAttributeMapping productProductAttributeMapping = productProductAttributeMappingRepository
                .findById(productAttributeMappingId)
                .orElseThrow(() -> new ResourceNotFoundException("Product attribute mapping not found: "
                        + productAttributeMappingId));

        List<ProductAttributeValue> productAttributeValues = request.stream()
                .map(productAttributeValueRequest -> {
                    ProductAttributeValue productAttributeValue = productAttributeValueMapper
                            .toEntity(productAttributeValueRequest);
                    productAttributeValue.setProductAttributeMapping(productProductAttributeMapping);
                    return productAttributeValue;
                })
                .toList();

        List<ProductAttributeValue> savedAttributeValues = productAttributeValueRepository.saveAll(productAttributeValues);

        List<ProductAttributeValuePicture> picturesToSave = new ArrayList<>();
        for (int i = 0; i < savedAttributeValues.size(); i++) {
            ProductAttributeValue savedValue = savedAttributeValues.get(i);
            List<Long> pictureIds = request.get(i).getPictureId();
            picturesToSave.addAll(
                    pictureIds.stream()
                            .map(pictureId -> {
                                ProductAttributeValuePictureRequest attributeValuePictureRequest =
                                        new ProductAttributeValuePictureRequest(savedValue.getId(), pictureId);
                                return productAttributeValuePictureMapper.toEntity(attributeValuePictureRequest);
                            })
                            .toList()
            );
        }

        productAttributeValuePictureRepository.saveAll(picturesToSave);
    }
}