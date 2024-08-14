package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.mapper.ProductSpecificationAttributeMappingMapper;
import com.example.back_end.core.admin.product.payload.request.ProductSpecificationAttributeMappingRequest;
import com.example.back_end.core.admin.product.payload.request.ProductSpecificationAttributeMappingUpdateRequest;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingResponse;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingUpdateResponse;
import com.example.back_end.core.admin.product.service.ProductSpecificationAttributeMappingService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.ProductSpecificationAttributeMapping;
import com.example.back_end.entity.SpecificationAttribute;
import com.example.back_end.entity.SpecificationAttributeOption;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.exception.NotExistsException;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductSpecificationAttributeMappingServiceImpl implements ProductSpecificationAttributeMappingService {

    ProductSpecificationAttributeMappingRepository productSpecificationAttributeMappingRepository;
    ProductSpecificationAttributeMappingMapper productSpecificationAttributeMappingMapper;
    ProductRepository productRepository;
    SpecificationAttributeOptionRepository specificationAttributeOptionRepository;
    SpecificationAttributeRepository specificationAttributeRepository;
    private static final String SPEC_ATTRIBUTE_ID = "spec_attribute_id";
    private static final String CUSTOM_VALUE = "custom_value";

    @Override
    public PageResponse<List<ProductSpecificationAttributeMappingResponse>> getAllProductSpecificationAttributeMapping(String name, int pageNo, int pageSize) {
        if (pageNo < 0 || pageSize <= 0) {
            throw new IllegalArgumentException("Invalid page number or page size");
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("displayOrder").descending());
        Page<ProductSpecificationAttributeMapping> mappingPage =
                productSpecificationAttributeMappingRepository
                        .searchByCustomValueContaining(name, pageable);

        List<ProductSpecificationAttributeMappingResponse> responses =
                productSpecificationAttributeMappingMapper
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
            ProductSpecificationAttributeMappingRequest request) throws JsonProcessingException {
        ProductSpecificationAttributeMapping mapping = productSpecificationAttributeMappingMapper.toEntity(request);

        mapping.setProduct(productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException(
                        ErrorCode.PRODUCT_ID_NOT_FOUND.getMessage())));

        SpecificationAttributeOption specificationAttributeOption = null;
        Long specificationAttributeId = null;
        String customValue = null;

        if (request.getSpecificationAttributeOptionId() != null) {
            specificationAttributeOption = specificationAttributeOptionRepository
                    .findById(request.getSpecificationAttributeOptionId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            ErrorCode.SPECIFICATION_ATTRIBUTE_OPTION_NOT_EXISTS.getMessage()));

            // Lưu tên của SpecificationAttributeOption vào customValue
            customValue = specificationAttributeOption.getName();
            mapping.setCustomValue(customValue);
            specificationAttributeOption = specificationAttributeOptionRepository.save(specificationAttributeOption);

            // Cập nhật id của specificationAttribute
            specificationAttributeId = specificationAttributeOption.getSpecificationAttribute().getId();

        } else {
            // Trích xuất specificationAttributeId từ customValueJson
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

        // Thiết lập specificationAttributeId
        mapping.setSpecificationAttributeOption(specificationAttributeOption);

        mapping = productSpecificationAttributeMappingRepository.save(mapping);

        // Lấy tên của SpecificationAttribute nếu có
        String specificationAttributeName = null;
        if (specificationAttributeId != null) {
            SpecificationAttribute specAttribute = specificationAttributeRepository.findById(specificationAttributeId).orElse(null);
            specificationAttributeName = specAttribute != null ? specAttribute.getName() : null;
        }

        // Tạo phản hồi và gán tên vào nó
        ProductSpecificationAttributeMappingResponse response = productSpecificationAttributeMappingMapper.toDto(mapping);
        response.setSpecificationAttributeId(specificationAttributeId);
        response.setSpecificationAttributeName(specificationAttributeName);

        // Thiết lập customValue để trả về
        if (request.getCustomValue() != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode customValueNode = objectMapper.readTree(request.getCustomValue());
            response.setCustomValue(customValueNode.has(CUSTOM_VALUE) ? customValueNode.get(CUSTOM_VALUE).asText() : null);
        }

        // Thiết lập customValueJson
        response.setCustomValueJson(request.getCustomValue());

        return response;
    }


    @Override
    public ProductSpecificationAttributeMappingResponse getProductSpecificationAttributeMappingById(Long id) {
        ProductSpecificationAttributeMapping mapping =
                productSpecificationAttributeMappingRepository
                        .findById(id)
                        .orElseThrow(() -> new IllegalArgumentException(
                                ErrorCode.PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_NOT_EXISTED.getMessage()));

        ProductSpecificationAttributeMappingResponse response = productSpecificationAttributeMappingMapper.toDto(mapping);

        if (mapping.getCustomValue() != null) {
            response.setCustomValueJson(mapping.getCustomValue());

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode customValueNode = objectMapper.readTree(mapping.getCustomValue());
                response.setCustomValue(customValueNode.has(CUSTOM_VALUE) ? customValueNode.get(CUSTOM_VALUE).asText() : null);

                Long specAttributeId = customValueNode.has(SPEC_ATTRIBUTE_ID) ? customValueNode.get(SPEC_ATTRIBUTE_ID).asLong() : null;
                if (specAttributeId != null) {
                    SpecificationAttribute specAttribute = specificationAttributeRepository.findById(specAttributeId).orElse(null);
                    response.setSpecificationAttributeName(specAttribute != null ? specAttribute.getName() : null);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return response;
    }


    @Override
    public void deleteProductSpecificationAttribute(List<Long> ids) {

        List<ProductSpecificationAttributeMapping> spec =
                productSpecificationAttributeMappingRepository.findAllById(ids);

        if (!spec.isEmpty())
            productSpecificationAttributeMappingRepository.deleteAllInBatch(spec);

    }

    @Override
    public PageResponse<List<ProductSpecificationAttributeMappingResponse>> getProcSpecMappingsByProductId(
            Long productId, int pageNo, int pageSize) {

        if (pageNo < 0 || pageSize <= 0) {
            throw new IllegalArgumentException("Invalid page number or page size");
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("displayOrder").descending());
        Page<ProductSpecificationAttributeMapping> mappingPage =
                productSpecificationAttributeMappingRepository.findByProductId(productId, pageable);

        List<ProductSpecificationAttributeMappingResponse> responses = mappingPage.getContent().stream()
                .map(mapping -> {
                    ProductSpecificationAttributeMappingResponse response = productSpecificationAttributeMappingMapper.toDto(mapping);

                    // Nếu `specificationAttributeId` có giá trị, lấy tên từ `SpecificationAttribute`
                    if (mapping.getSpecificationAttributeOption() != null) {
                        SpecificationAttributeOption option = mapping.getSpecificationAttributeOption();
                        SpecificationAttribute attribute = option.getSpecificationAttribute();
                        if (attribute != null) {
                            response.setSpecificationAttributeName(attribute.getName());
                        }
                    } else if (mapping.getCustomValue() != null) {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            JsonNode customValueNode = objectMapper.readTree(mapping.getCustomValue());
                            Long specAttributeId = customValueNode.has(SPEC_ATTRIBUTE_ID)
                                    ? customValueNode.get(SPEC_ATTRIBUTE_ID).asLong()
                                    : null;

                            if (specAttributeId != null) {
                                SpecificationAttribute attribute = specificationAttributeRepository.findById(specAttributeId).orElse(null);
                                response.setSpecificationAttributeName(attribute != null ? attribute.getName() : null);
                            }
                        } catch (JsonProcessingException e) {
                            // Handle exception
                            e.printStackTrace();
                        }
                    }

                    return response;
                })
                .toList();

        return PageResponse.<List<ProductSpecificationAttributeMappingResponse>>builder()
                .page(mappingPage.getNumber())
                .size(mappingPage.getSize())
                .totalPage(mappingPage.getTotalPages())
                .items(responses)
                .build();
    }


    @Override
    @Transactional
    public ProductSpecificationAttributeMappingUpdateResponse updateProductSpecificationAttributeMapping(
            Long id, ProductSpecificationAttributeMappingUpdateRequest request) throws JsonProcessingException {

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

            if (!request.getSpecificationAttributeId().equals(specificationAttributeOption.getSpecificationAttribute().getId())) {
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
