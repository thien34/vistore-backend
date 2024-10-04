package com.example.back_end.service.product.impl;

import com.example.back_end.core.admin.product.mapper.ProductAttributeValuePictureMapper;
import com.example.back_end.core.admin.product.payload.request.ProductAttributeValuePictureRequest;
import com.example.back_end.service.product.ProductAttributeValuePictureService;
import com.example.back_end.entity.ProductAttributeValuePicture;
import com.example.back_end.repository.ProductAttributeValuePictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductAttributeValuePictureServiceImpl implements ProductAttributeValuePictureService {

    private final ProductAttributeValuePictureRepository productAttributeValuePictureRepository;
    private final ProductAttributeValuePictureMapper productAttributeValuePictureMapper;

    @Override
    public void createProductAttributePictureValue(Map<Long, List<ProductAttributeValuePictureRequest>> requests) {
        List<ProductAttributeValuePictureRequest> productAttributeValuePictureRequests = requests.entrySet().stream()
                .flatMap(longListEntry -> longListEntry.getValue().stream()
                        .map(productAttributeValuePictureRequest ->
                                ProductAttributeValuePictureRequest.builder()
                                        .productAttributeValueId(longListEntry.getKey())
                                        .pictureId(productAttributeValuePictureRequest.getPictureId())
                                        .id(productAttributeValuePictureRequest.getId())
                                        .build()
                        ))
                .toList();

        List<ProductAttributeValuePicture> productAttributeValuePictures = productAttributeValuePictureMapper
                .toEntity(productAttributeValuePictureRequests);

        productAttributeValuePictureRepository.saveAll(productAttributeValuePictures);
    }

}
