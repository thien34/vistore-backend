package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.mapper.ProductAttributeMapper;
import com.example.back_end.core.admin.product.payload.request.PredefinedProductAttributeValueUpdateRequest;
import com.example.back_end.core.admin.product.payload.request.ProductAttributeRequest;
import com.example.back_end.core.admin.product.payload.response.PredefinedProductAttributeValueResponse;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeResponse;
import com.example.back_end.core.admin.product.service.ProductAttributeService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.PredefinedProductAttributeValue;
import com.example.back_end.entity.ProductAttribute;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.exception.AlreadyExistsException;
import com.example.back_end.infrastructure.exception.ExistsByNameException;
import com.example.back_end.infrastructure.exception.NotExistsException;
import com.example.back_end.repository.PredefinedProductAttributeValueRepository;
import com.example.back_end.repository.ProductAttributeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
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
            throw new ExistsByNameException(ErrorCode.PRODUCT_ATTRIBUTE_EXISTED.getMessage());
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
                .orElseThrow(() -> new AlreadyExistsException(ErrorCode.PRODUCT_ATTRIBUTE_EXISTED.getMessage()));
        return ProductAttributeResponse.mapToResponse(productAttribute);
    }

    @Override
    @Transactional
    public ProductAttributeResponse updateProductAttribute(Long id, ProductAttributeRequest request) {
        ProductAttribute productAttribute = productAttributeRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorCode.PRODUCT_ATTRIBUTE_NOT_EXISTED.getMessage()));

        if (productAttributeRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new AlreadyExistsException(ErrorCode.PRODUCT_ATTRIBUTE_ALREADY_EXISTS.getMessage());
        }

        productAttributeMapper.updateEntityFromRequest(request, productAttribute);

        Map<Long, PredefinedProductAttributeValue> existingValuesMap = predefinedProductAttributeValueRepository.findByProductAttributeId(id)
                .stream()
                .collect(Collectors.toMap(PredefinedProductAttributeValue::getId, value -> value));

        List<PredefinedProductAttributeValue> updatedValues = request.getValues().stream().map(predefinedRequest -> {
            PredefinedProductAttributeValue value = existingValuesMap.getOrDefault(predefinedRequest.getId(),
                    PredefinedProductAttributeValue.builder().productAttribute(productAttribute).build());
            value.setCost(predefinedRequest.getCost());
            value.setDisplayOrder(predefinedRequest.getDisplayOrder());
            value.setName(predefinedRequest.getName());
            value.setPriceAdjustment(predefinedRequest.getPriceAdjustment());
            value.setIsPreSelected(predefinedRequest.getIsPreSelected());
            value.setWeightAdjustment(predefinedRequest.getWeightAdjustment());
            value.setPriceAdjustmentUsePercentage(predefinedRequest.getPriceAdjustmentUsePercentage());
            return value;
        }).toList();

        predefinedProductAttributeValueRepository.saveAll(updatedValues);

        List<Long> updatedValueIds = updatedValues.stream()
                .map(PredefinedProductAttributeValue::getId)
                .filter(Objects::nonNull)
                .toList();

        predefinedProductAttributeValueRepository.deleteAll(existingValuesMap.values().stream()
                .filter(value -> !updatedValueIds.contains(value.getId()))
                .toList());

        productAttribute.setValues(new ArrayList<>(updatedValues));
        ProductAttribute savedProductAttribute = productAttributeRepository.save(productAttribute);

        return productAttributeMapper.toDto(savedProductAttribute);

    }







    @Override
    public PageResponse<?> searchByNameName(String name, int page, int size) {

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

    @Override
    public void deleteProductAttribute(List<Long> ids) {
        List<ProductAttribute> productAttributes = productAttributeRepository.findAllById(ids);

        if (!productAttributes.isEmpty()) {
            productAttributeRepository.deleteAllInBatch(productAttributes);
        }
    }

}
