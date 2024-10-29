package com.example.back_end.service.product.impl;

import com.example.back_end.core.admin.product.mapper.ProductSpecificationAttributeMappingMapper;
import com.example.back_end.core.admin.product.payload.request.ProductSpecificationAttributeMappingRequest;
import com.example.back_end.core.admin.product.payload.request.ProductSpecificationAttributeMappingUpdateRequest;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingByIdResponse;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingByProductResponse;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingResponse;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingUpdateResponse;
import com.example.back_end.service.product.ProductSpecificationAttributeMappingService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.ProductSpecificationAttributeMapping;
import com.example.back_end.entity.SpecificationAttribute;
import com.example.back_end.entity.SpecificationAttributeOption;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.constant.SortType;
import com.example.back_end.infrastructure.exception.NotExistsException;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.ProductRepository;
import com.example.back_end.repository.ProductSpecificationAttributeMappingRepository;
import com.example.back_end.repository.SpecificationAttributeOptionRepository;
import com.example.back_end.repository.SpecificationAttributeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductSpecificationAttributeMappingServiceImpl implements ProductSpecificationAttributeMappingService {

    static final String SPEC_ATTRIBUTE_ID = "spec_attribute_id";
    static final String CUSTOM_VALUE = "custom_value";

    ProductSpecificationAttributeMappingRepository productSpecificationAttributeMappingRepository;
    ProductSpecificationAttributeMappingMapper productSpecificationAttributeMappingMapper;
    ProductRepository productRepository;
    SpecificationAttributeOptionRepository specificationAttributeOptionRepository;
    SpecificationAttributeRepository specificationAttributeRepository;

    @Override
    public PageResponse<List<ProductSpecificationAttributeMappingResponse>> getAllProductSpecificationAttributeMapping(
            String name, int pageNo, int pageSize) {

        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "id", SortType.DESC.getValue());
        Page<ProductSpecificationAttributeMapping> mappingPage = productSpecificationAttributeMappingRepository
                .searchByCustomValueContaining(name, pageable);

        List<ProductSpecificationAttributeMappingResponse> responses = productSpecificationAttributeMappingMapper
                .toDto(mappingPage.getContent());

        return PageResponse.<List<ProductSpecificationAttributeMappingResponse>>builder()
                .page(mappingPage.getNumber())
                .size(mappingPage.getSize())
                .totalPage(mappingPage.getTotalPages())
                .items(responses)
                .build();
    }

    @Override
    @Transactional
    public void createProductSpecificationAttributeMapping(ProductSpecificationAttributeMappingRequest request) {

        if (!productRepository.existsById(request.getProductId())) {
            throw new IllegalArgumentException(ErrorCode.PRODUCT_NOT_FOUND.getMessage());
        }
        if (request.getSpecificationAttributeOptionId() != null) {
            if (!specificationAttributeOptionRepository.existsById(request.getSpecificationAttributeOptionId())) {
                throw new IllegalArgumentException(ErrorCode.SPECIFICATION_ATTRIBUTE_OPTION_NOT_EXISTS.getMessage());
            }
        }

        ProductSpecificationAttributeMapping specificationAttributeMapping = productSpecificationAttributeMappingMapper.toEntity(request);
        productSpecificationAttributeMappingRepository.save(specificationAttributeMapping);
    }

    @Override
    public ProductSpecificationAttributeMappingByIdResponse getProductSpecificationAttributeMappingById(Long id) {
        ProductSpecificationAttributeMapping mapping = productSpecificationAttributeMappingRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        ErrorCode.PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_NOT_EXISTED.getMessage()));

        // Use the mapper to create the initial response
        ProductSpecificationAttributeMappingByIdResponse response = productSpecificationAttributeMappingMapper
                .toDtooo(mapping);

        if (mapping.getSpecificationAttributeOption() != null) {
            // Set the name from the SpecificationAttributeOption if it exists
            response.setSpecificationAttributeOptionName(mapping.getSpecificationAttributeOption().getName());
            response.setCustomValue(mapping.getSpecificationAttributeOption().getName());
            response.setSpecificationAttributeName(mapping.getSpecificationAttributeOption().getSpecificationAttribute().getName());
        } else if (mapping.getCustomValue() != null) {
            // Process the custom value
            JsonNode customValueNode = parseCustomValue(mapping.getCustomValue());

            // Set customValue in the response
            response.setCustomValue(customValueNode.has(CUSTOM_VALUE) ? customValueNode.get(CUSTOM_VALUE).asText() : mapping.getCustomValue());

            if (customValueNode.has(SPEC_ATTRIBUTE_ID)) {
                Long specAttributeId = customValueNode.get(SPEC_ATTRIBUTE_ID).asLong();
                SpecificationAttribute specAttribute = specificationAttributeRepository
                        .findById(specAttributeId).orElse(null);
                response.setSpecificationAttributeName(specAttribute != null ? specAttribute.getName() : null);
            } else {
                // If there's no SPEC_ATTRIBUTE_ID in the JSON, fallback to a default behavior
                response.setSpecificationAttributeName(mapping.getCustomValue());
            }
        } else {
            // If no option or custom value, set default or null values
            response.setSpecificationAttributeName(null);
            response.setCustomValue(null);
        }

        return response;
    }

    // Method to handle customValue
    private JsonNode parseCustomValue(String customValue) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode customValueNode;

        try {
            customValueNode = objectMapper.readTree(customValue);
        } catch (JsonProcessingException e) {
            // If not JSON, treat customValue as a simple value
            customValueNode = objectMapper.createObjectNode().put("value", customValue);
        }

        return customValueNode;
    }

    @Override
    public void deleteProductSpecificationAttribute(List<Long> ids) {

        List<ProductSpecificationAttributeMapping> spec =
                productSpecificationAttributeMappingRepository.findAllById(ids);

        if (!spec.isEmpty())
            productSpecificationAttributeMappingRepository.deleteAllInBatch(spec);
    }

    @Override
    public PageResponse<List<ProductSpecificationAttributeMappingByProductResponse>> getProcSpecMappingsByProductId(
            Long productId, int pageNo, int pageSize) {

        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "id", SortType.DESC.getValue());
        Page<ProductSpecificationAttributeMapping> mappingPage =
                productSpecificationAttributeMappingRepository.findByProductId(productId, pageable);

        List<ProductSpecificationAttributeMappingByProductResponse> responses = mappingPage.getContent().stream()
                .map(mapping -> {
                    ProductSpecificationAttributeMappingByProductResponse response = productSpecificationAttributeMappingMapper
                            .toDtos(mapping);
                    if (mapping.getSpecificationAttributeOption() != null) {
                        response.setAttributeType("Option");
                    } else {
                        SpecificationAttribute specificationAttribute = specificationAttributeRepository
                                .findById(Long.valueOf(response.getSpecificationAttributeName()))
                                .orElseThrow(() -> new IllegalArgumentException(
                                        ErrorCode.SPECIFICATION_ATTRIBUTE_NOT_EXISTED.getMessage()));

                        response.setAttributeType("Custom Text");
                        response.setSpecificationAttributeName(specificationAttribute.getName());
                    }
                    return response;
                })
                .toList();

        return PageResponse.<List<ProductSpecificationAttributeMappingByProductResponse>>builder()
                .page(mappingPage.getNumber())
                .size(mappingPage.getSize())
                .totalPage(mappingPage.getTotalPages())
                .items(responses)
                .build();
    }

    @Override
    @Transactional
    public ProductSpecificationAttributeMappingUpdateResponse updateProductSpecificationAttributeMapping(
            Long id, ProductSpecificationAttributeMappingUpdateRequest request) {

        // Retrieve the existing mapping
        ProductSpecificationAttributeMapping existingMapping =
                productSpecificationAttributeMappingRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException(
                                ErrorCode.PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_NOT_FOUND.getMessage()));

        // Update DisplayOrder
        Optional.ofNullable(request.getDisplayOrder())
                .ifPresent(existingMapping::setDisplayOrder);

        // Process SpecificationAttributeOption
        if (request.getSpecificationAttributeOptionId() != null) {
            SpecificationAttributeOption specificationAttributeOption =
                    specificationAttributeOptionRepository.findById(request.getSpecificationAttributeOptionId())
                            .orElseThrow(() -> new IllegalArgumentException(
                                    ErrorCode.ID_SPECIFICATION_ATTRIBUTE_OPTION_INVALID.getMessage()));

            // Set customValue to the name of the SpecificationAttributeOption
            existingMapping.setCustomValue(specificationAttributeOption.getName());
            existingMapping.setSpecificationAttributeOption(specificationAttributeOption);

        } else {
            // If no SpecificationAttributeOptionId is provided, handle the customValue
            String customValue = request.getCustomValue();
            if (customValue != null) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode customValueNode = objectMapper.readTree(customValue);
                    // Extract custom_value from JSON if it exists
                    String customValueText = customValueNode.has(CUSTOM_VALUE) ? customValueNode.get(CUSTOM_VALUE).asText() : customValue;
                    existingMapping.setCustomValue(customValueText);
                } catch (JsonProcessingException e) {
                    // If JSON processing fails, just set the raw customValue
                    existingMapping.setCustomValue(customValue);
                }
            } else {
                existingMapping.setCustomValue(null);
            }

            // Remove the SpecificationAttributeOption if no ID is provided
            existingMapping.setSpecificationAttributeOption(null);
        }

        // Update the mapping
        if (request.getShowOnProductPage() != null) {
            existingMapping.setShowOnProductPage(request.getShowOnProductPage());
        }

        // Save and return updated mapping
        existingMapping = productSpecificationAttributeMappingRepository.save(existingMapping);

        return ProductSpecificationAttributeMappingUpdateResponse.builder()
                .id(existingMapping.getId())
                .customValue(existingMapping.getCustomValue())
                .showOnProductPage(existingMapping.getShowOnProductPage())
                .displayOrder(existingMapping.getDisplayOrder())
                .build();
    }

    @Override
    public void deleteProductSpecificationAttributeMappingById(Long id) {

        ProductSpecificationAttributeMapping mapping = productSpecificationAttributeMappingRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(
                        ErrorCode.PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_NOT_EXISTED.getMessage()));

        productSpecificationAttributeMappingRepository.delete(mapping);
    }

}