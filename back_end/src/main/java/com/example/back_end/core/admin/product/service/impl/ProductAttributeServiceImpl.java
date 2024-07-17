package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.mapper.ProductAttributeMapper;
import com.example.back_end.core.admin.product.payload.request.ProductAttributeRequest;
import com.example.back_end.core.admin.product.payload.response.PredefinedProductAttributeValueResponse;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeResponse;
import com.example.back_end.core.admin.product.service.ProductAttributeService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.PredefinedProductAttributeValue;
import com.example.back_end.entity.ProductAttribute;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.exception.ResourceNotFoundException;
import com.example.back_end.infrastructure.exception.StoreException;
import com.example.back_end.repository.PredefinedProductAttributeValueRepository;
import com.example.back_end.repository.ProductAttributeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductAttributeServiceImpl implements ProductAttributeService {
    ProductAttributeRepository productAttributeRepository;
    ProductAttributeMapper productAttributeMapper;
    PredefinedProductAttributeValueRepository predefinedProductAttributeValueRepository;

    @Override
    @Transactional
    public ProductAttribute createProductAttribute(ProductAttributeRequest request) {
        if (productAttributeRepository
                .existsByName(request.getName().trim().replaceAll("\\s+", " ")))
            throw new StoreException(ErrorCode.PRODUCT_ATTRIBUTE_EXISTED);
        ProductAttribute productAttribute = productAttributeMapper.toEntity(request);
        ProductAttribute productAttributeSave = productAttributeRepository.save(productAttribute);
        List<PredefinedProductAttributeValue> values = new ArrayList<>();
        request.getValues().forEach(predifend -> {
            PredefinedProductAttributeValue predefinedProductAttributeValue = PredefinedProductAttributeValue.builder()
                    .productAttribute(productAttributeSave)
                    .cost(predifend.getCost())
                    .displayOrder(predifend.getDisplayOrder())
                    .name(predifend.getName())
                    .priceAdjustment(predifend.getPriceAdjustment())
                    .isPreSelected(predifend.getIsPreSelected())
                    .weightAdjustment(predifend.getWeightAdjustment())
                    .priceAdjustmentUsePercentage(predifend.getPriceAdjustmentUsePercentage())
                    .build();
            values.add(predefinedProductAttributeValue);
        });
        predefinedProductAttributeValueRepository.saveAll(values);
        return productAttributeSave;
    }

    @Override
    public PageResponse<?> getAllProductAttribute(String name, int pageNo, int pageSize) {
        if (pageNo < 0 || pageSize <= 0) {
            throw new StoreException(ErrorCode.INVALID_PAGE_NUMBER_OR_PAGE_SIZE);
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        Page<ProductAttribute> productAttributePage = productAttributeRepository.findByNameContaining(name, pageable);
        List<ProductAttributeResponse> productAttributeResponseList = productAttributePage.stream()
                .map(productAttribute -> {
                    ProductAttributeResponse response = productAttributeMapper.toDto(productAttribute);
                    List<PredefinedProductAttributeValueResponse> values = productAttribute.getValues().stream()
                            .map(PredefinedProductAttributeValueResponse::mapToResponse)
                            .toList();
                    response.setValues(values);
                    return response;
                })
                .toList();

        return PageResponse.builder()
                .page(productAttributePage.getNumber())
                .size(productAttributePage.getSize())
                .totalPage(productAttributePage.getTotalPages())
                .items(productAttributeResponseList)
                .build();
    }


    @Override
    public ProductAttributeResponse getProductAttributeById(Long id) {
        ProductAttribute productAttribute = productAttributeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PRODUCT_ATTRIBUTE_EXISTED.getMessage()));
        return ProductAttributeResponse.mapToResponse(productAttribute);
    }

    @Override
    @Transactional
    public ProductAttributeResponse updateProductAttribute(Long id, ProductAttributeRequest request) {
        ProductAttribute productAttribute = productAttributeRepository.findById(id)
                .orElseThrow(() -> new StoreException(ErrorCode.PRODUCT_ATTRIBUTE_NOT_EXISTED));
        if (productAttributeRepository
                .existsByName(request.getName().trim().replaceAll("\\s+", " ")))
            throw new StoreException(ErrorCode.PRODUCT_ATTRIBUTE_EXISTED);

        productAttribute.setName(request.getName());
        productAttribute.setDescription(request.getDescription());

        ProductAttribute updatedProductAttribute = productAttributeRepository.save(productAttribute);
        return ProductAttributeResponse.mapToResponse(updatedProductAttribute);
    }


    @Override
    public void deleteProductAttribute(Long id) {
        if (!productAttributeRepository.existsById(id)) {
            throw new StoreException(ErrorCode.PRODUCT_ATTRIBUTE_NOT_EXISTED);
        }
        productAttributeRepository.deleteById(id);
    }
    @Override
    public PageResponse<?> searchByNameName(String name, int page, int size) {
        if (page < 0 || size <= 0) {
            throw new StoreException(ErrorCode.INVALID_PAGE_NUMBER_OR_PAGE_SIZE);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<ProductAttribute> productAttributePage = productAttributeRepository.findByNameContaining(name, pageable);
        List<ProductAttributeResponse> productAttributeResponseList = productAttributePage.stream()
                .map(productAttribute -> {
                    ProductAttributeResponse response = productAttributeMapper.toDto(productAttribute);
                    List<PredefinedProductAttributeValueResponse> values = productAttribute.getValues().stream()
                            .map(PredefinedProductAttributeValueResponse::mapToResponse)
                            .toList();
                    response.setValues(values);
                    return response;
                })
                .toList();
        return PageResponse.builder()
                .page(productAttributePage.getNumber())
                .size(productAttributePage.getSize())
                .totalPage(productAttributePage.getTotalPages())
                .items(productAttributeResponseList)
                .build();
    }

}
