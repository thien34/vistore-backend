package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.mapper.ProductSpecificationAttributeMappingMapper;
import com.example.back_end.core.admin.product.payload.request.ProductSpecificationAttributeMappingRequest;
import com.example.back_end.core.admin.product.payload.request.ProductSpecificationAttributeMappingUpdateRequest;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingByProductResponse;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingResponse;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingUpdateResponse;
import com.example.back_end.core.admin.product.service.ProductSpecificationAttributeMappingService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.ProductSpecificationAttributeMapping;
import com.example.back_end.entity.SpecificationAttribute;
import com.example.back_end.entity.SpecificationAttributeOption;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.constant.SortType;
import com.example.back_end.infrastructure.exception.CustomJsonProcessingException;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
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
            String name, int pageNo, int pageSize
    ) {

        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "displayOrder", SortType.DESC.getValue());
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
    public ProductSpecificationAttributeMappingResponse createProductSpecificationAttributeMapping(
            ProductSpecificationAttributeMappingRequest request) {
        ProductSpecificationAttributeMapping mapping = productSpecificationAttributeMappingMapper.toEntity(request);

        try {
            mapping.setProduct(productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            ErrorCode.PRODUCT_ID_NOT_FOUND.getMessage())));

            SpecificationAttributeOption specificationAttributeOption = null;
            Long specificationAttributeId;
            String customValue;

            if (request.getSpecificationAttributeOptionId() != null) {
                specificationAttributeOption = specificationAttributeOptionRepository
                        .findById(request.getSpecificationAttributeOptionId())
                        .orElseThrow(() -> new IllegalArgumentException(
                                ErrorCode.SPECIFICATION_ATTRIBUTE_OPTION_NOT_EXISTS.getMessage()));

                // Save the name of SpecificationAttributeOption to customValue
                customValue = specificationAttributeOption.getName();
                mapping.setCustomValue(customValue);
                specificationAttributeOption = specificationAttributeOptionRepository
                        .save(specificationAttributeOption);

                // Update the id of specificationAttribute
                specificationAttributeId = specificationAttributeOption.getSpecificationAttribute().getId();

            } else {
                //Extract specificationAttributeId from customValueJson
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode customValueNode = objectMapper.readTree(request.getCustomValue());
                specificationAttributeId = customValueNode.has(SPEC_ATTRIBUTE_ID)
                        ? customValueNode.get(SPEC_ATTRIBUTE_ID).asLong()
                        : null;

                if (specificationAttributeId != null) {
                    specificationAttributeRepository
                            .findById(specificationAttributeId)
                            .orElseThrow(() -> new IllegalArgumentException(
                                    ErrorCode.ID_SPECIFICATION_ATTRIBUTE_INVALID.getMessage()));
                    mapping.setCustomValue(request.getCustomValue());
                }
            }

            // Set specificationAttributeId
            mapping.setSpecificationAttributeOption(specificationAttributeOption);

            mapping = productSpecificationAttributeMappingRepository.save(mapping);

            // Get the name of the SpecificationAttribute if available
            String specificationAttributeName = null;
            if (specificationAttributeId != null) {
                SpecificationAttribute specAttribute = specificationAttributeRepository
                        .findById(specificationAttributeId).orElse(null);
                specificationAttributeName = specAttribute != null ? specAttribute.getName() : null;
            }

            // Create a response and assign a name to it
            ProductSpecificationAttributeMappingResponse response = productSpecificationAttributeMappingMapper
                    .toDto(mapping);
            response.setSpecificationAttributeId(specificationAttributeId);
            response.setSpecificationAttributeName(specificationAttributeName);

            //Set customValue to return
            if (request.getCustomValue() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode customValueNode = objectMapper.readTree(request.getCustomValue());
                response.setCustomValue(customValueNode.has(CUSTOM_VALUE) ? customValueNode
                        .get(CUSTOM_VALUE).asText() : null);
            }

            // Set up customValueJson
            response.setCustomValueJson(request.getCustomValue());

            return response;
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON data: ", e);
            // Handle JSON processing exception
            throw new CustomJsonProcessingException("Failed to process JSON data", e);
        } catch (IllegalArgumentException e) {
            log.error("Invalid argument: ", e);
            // Handle argument exceptions
            throw new IllegalArgumentException("Invalid input provided", e);
        }
    }

    @Override
    public ProductSpecificationAttributeMappingResponse getProductSpecificationAttributeMappingById(Long id) {
        ProductSpecificationAttributeMapping mapping = productSpecificationAttributeMappingRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        ErrorCode.PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_NOT_EXISTED.getMessage()));

        ProductSpecificationAttributeMappingResponse response = productSpecificationAttributeMappingMapper
                .toDto(mapping);

        if (mapping.getCustomValue() != null) {
            response.setCustomValueJson(mapping.getCustomValue());

            JsonNode customValueNode = parseCustomValue(mapping.getCustomValue());

            response.setCustomValue(customValueNode.has(CUSTOM_VALUE) ? customValueNode
                    .get(CUSTOM_VALUE).asText() : customValueNode.asText());

            Long specAttributeId = customValueNode.has(SPEC_ATTRIBUTE_ID) ? customValueNode
                    .get(SPEC_ATTRIBUTE_ID).asLong() : null;
            if (specAttributeId != null) {
                SpecificationAttribute specAttribute = specificationAttributeRepository
                        .findById(specAttributeId).orElse(null);
                response.setSpecificationAttributeName(specAttribute != null ? specAttribute.getName() : null);
            }
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

        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "displayOrder", SortType.DESC.getValue());
        Page<ProductSpecificationAttributeMapping> mappingPage =
                productSpecificationAttributeMappingRepository.findByProductId(productId, pageable);

        ObjectMapper objectMapper = new ObjectMapper();

        List<ProductSpecificationAttributeMappingByProductResponse> responses = mappingPage.getContent().stream()
                .map(mapping -> {
                    ProductSpecificationAttributeMappingByProductResponse response = productSpecificationAttributeMappingMapper
                            .toDtos(mapping);

                    // Set specification attribute name and option name
                    if (mapping.getSpecificationAttributeOption() != null) {
                        SpecificationAttributeOption option = mapping.getSpecificationAttributeOption();
                        SpecificationAttribute attribute = option.getSpecificationAttribute();

                        response.setSpecificationAttributeName(attribute.getName());
                        response.setSpecificationAttributeOptionName(option.getName());
                        response.setAttributeType("Option");
                    } else {
                        // Nếu không có specificationAttributeOption, trích xuất custom_value từ customValue
                        String customValue = mapping.getCustomValue();

                        if (customValue != null) {
                            try {
                                JsonNode customValueNode = objectMapper.readTree(customValue);

                                // Trích xuất custom_value nếu có
                                if (customValueNode.has("custom_value")) {
                                    response.setCustomValue(customValueNode.get("custom_value").asText());
                                } else {
                                    // Nếu không có `custom_value`, sử dụng raw customValue
                                    response.setCustomValue(customValue);
                                }

                            } catch (JsonProcessingException e) {
                                log.error("Invalid JSON format in customValue: {}", customValue, e);
                                // Xử lý lỗi phân tích JSON bằng cách sử dụng raw customValue
                                response.setCustomValue(customValue);
                            }
                        }
                        response.setAttributeType("Custom Text");
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





    // Utility method to check if a string is JSON
    private boolean isJson(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        try {
            new ObjectMapper().readTree(value);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
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

        // Retrieve SpecificationAttribute from repository
        SpecificationAttribute specificationAttribute = specificationAttributeRepository
                .findById(request.getSpecificationAttributeId())
                .orElseThrow(() -> new IllegalArgumentException(
                        ErrorCode.ID_SPECIFICATION_ATTRIBUTE_INVALID.getMessage()));

        // Update DisplayOrder
        Optional.ofNullable(request.getDisplayOrder())
                .ifPresent(existingMapping::setDisplayOrder);

        // Process SpecificationAttributeOption
        if (request.getSpecificationAttributeOptionId() != null) {
            SpecificationAttributeOption specificationAttributeOption =
                    specificationAttributeOptionRepository.findById(request.getSpecificationAttributeOptionId())
                            .orElseThrow(() -> new IllegalArgumentException(
                                    ErrorCode.ID_SPECIFICATION_ATTRIBUTE_OPTION_INVALID.getMessage()));

            if (!request.getSpecificationAttributeId().equals(specificationAttributeOption
                    .getSpecificationAttribute().getId())) {
                specificationAttributeOption.setSpecificationAttribute(specificationAttribute);
                specificationAttributeOptionRepository.save(specificationAttributeOption);
            }

            existingMapping.setSpecificationAttributeOption(specificationAttributeOption);

        } else {
            existingMapping.setSpecificationAttributeOption(null); // Set to null if no option
        }

        // Update customValue
        if (request.getCustomValue() != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> customValueMap;
            try {
                // Decode existing customValue if it's serialized
                String existingCustomValue = existingMapping.getCustomValue();
                if (existingCustomValue != null && existingCustomValue.startsWith("{")) {
                    customValueMap = objectMapper.readValue(existingCustomValue, Map.class);
                } else {
                    customValueMap = new HashMap<>();
                }

                // Decode customValue from request
                Map<String, String> requestCustomValueMap = objectMapper.readValue(request.getCustomValue(), Map.class);

                // Update customValueMap from requestCustomValueMap
                customValueMap.putAll(requestCustomValueMap);
                customValueMap.put("specificationAttributeName", specificationAttribute.getName());

                // Serialize back to JSON
                String jsonCustomValue = objectMapper.writeValueAsString(customValueMap);
                existingMapping.setCustomValue(jsonCustomValue);
            } catch (JsonProcessingException e) {
                log.error("Error processing JSON data: ", e);
                // Optionally, set a default value or rethrow a custom exception
                existingMapping.setCustomValue("{}");
            }
        }

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