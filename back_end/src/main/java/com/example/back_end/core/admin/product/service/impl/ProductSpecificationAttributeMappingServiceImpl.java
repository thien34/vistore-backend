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
import com.example.back_end.repository.ProductSpecificationAttributeMappingRepository;
import com.example.back_end.repository.ProductRepository;
import com.example.back_end.repository.SpecificationAttributeOptionRepository;
import com.example.back_end.repository.SpecificationAttributeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Override
    public PageResponse<?> getAllProductSpecificationAttributeMapping(String name, int pageNo, int pageSize) {
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

        return PageResponse.builder()
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

        mapping.setProduct(productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException(
                        ErrorCode.ID_SPECIFICATION_ATTRIBUTE_INVALID.getMessage())));

        SpecificationAttributeOption specificationAttributeOption;

        if (request.getSpecificationAttributeOptionId() == null) {
            specificationAttributeOption = new SpecificationAttributeOption(request.getCustomValue());

            if (request.getDisplayOrder() != null) {
                specificationAttributeOption.setDisplayOrder(request.getDisplayOrder());
            }
            if (request.getSpecificationAttributeId() != null) {
                SpecificationAttribute specificationAttribute = specificationAttributeRepository
                        .findById(request.getSpecificationAttributeId())
                        .orElseThrow(() -> new IllegalArgumentException(
                                ErrorCode.ID_SPECIFICATION_ATTRIBUTE_INVALID.getMessage()));

                specificationAttributeOption.setSpecificationAttribute(specificationAttribute);
            }
            specificationAttributeOption = specificationAttributeOptionRepository.save(specificationAttributeOption);
        } else {
            specificationAttributeOption = specificationAttributeOptionRepository
                    .findById(request.getSpecificationAttributeOptionId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            ErrorCode.SPECIFICATION_ATTRIBUTE_OPTION_NOT_EXISTS.getMessage()));

            if (request.getDisplayOrder() != null) {
                specificationAttributeOption.setDisplayOrder(request.getDisplayOrder());
            }

            if (request.getSpecificationAttributeId() != null) {
                SpecificationAttribute specificationAttribute = specificationAttributeRepository
                        .findById(request.getSpecificationAttributeId())
                        .orElseThrow(() -> new IllegalArgumentException(
                                ErrorCode.ID_SPECIFICATION_ATTRIBUTE_INVALID.getMessage()));

                specificationAttributeOption.setSpecificationAttribute(specificationAttribute);
            }

            specificationAttributeOption = specificationAttributeOptionRepository.save(specificationAttributeOption);
        }
        mapping.setSpecificationAttributeOption(specificationAttributeOption);

        mapping.setAttributeType(request.getAttributeType());

        mapping = productSpecificationAttributeMappingRepository.save(mapping);

        return productSpecificationAttributeMappingMapper.toDto(mapping);

    }
    @Override
    public ProductSpecificationAttributeMappingResponse getProductSpecificationAttributeMappingById(Long id) {

        ProductSpecificationAttributeMapping mapping =
                productSpecificationAttributeMappingRepository
                        .findById(id).orElseThrow(
                                () -> new IllegalArgumentException(
                                        ErrorCode.PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_NOT_EXISTED.getMessage()));

        return productSpecificationAttributeMappingMapper.toDto(mapping);

    }
    @Override
    public void deleteProductSpecificationAttribute(List<Long> ids) {

        List<ProductSpecificationAttributeMapping> spec =
                productSpecificationAttributeMappingRepository.findAllById(ids);

        if (!spec.isEmpty())
            productSpecificationAttributeMappingRepository.deleteAllInBatch(spec);

    }
    @Override
    public PageResponse<?> getProductSpecificationAttributeMappingsByProductId(
            Long productId, int pageNo, int pageSize) {

        if (pageNo < 0 || pageSize <= 0) {
            throw new IllegalArgumentException("Invalid page number or page size");
        }

        Pageable pageable = PageRequest
                .of(pageNo, pageSize, Sort.by("displayOrder").descending());
        Page<ProductSpecificationAttributeMapping> mappingPage =
                productSpecificationAttributeMappingRepository.findByProductId(productId, pageable);

        List<ProductSpecificationAttributeMappingResponse> responses =
                productSpecificationAttributeMappingMapper
                        .toDto(mappingPage.getContent());

        return PageResponse.builder()
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

        ProductSpecificationAttributeMapping existingMapping =
                productSpecificationAttributeMappingRepository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        ErrorCode.PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_NOT_FOUND.getMessage()));

        Optional.ofNullable(request.getShowOnProductPage())
                .ifPresent(existingMapping::setShowOnProductPage);

        Optional.ofNullable(request.getDisplayOrder())
                .ifPresent(existingMapping::setDisplayOrder);

        if (request.getSpecificationAttributeOptionId() == null) {
            SpecificationAttributeOption existingOption = existingMapping.getSpecificationAttributeOption();
            if (existingOption != null) {
                boolean optionUpdated = false;

                if (request.getCustomValue() != null && !request.getCustomValue().equals(existingOption.getName())) {
                    existingOption.setName(request.getCustomValue());
                    optionUpdated = true;
                }

                if (request.getDisplayOrder() != null &&
                        !request.getDisplayOrder().equals(existingOption.getDisplayOrder())) {
                    existingOption.setDisplayOrder(request.getDisplayOrder());
                    optionUpdated = true;
                }

                if (request.getSpecificationAttributeId() != null &&
                        !request.getSpecificationAttributeId().equals(existingOption.getSpecificationAttribute().getId())) {
                    SpecificationAttribute specificationAttribute = specificationAttributeRepository
                            .findById(request.getSpecificationAttributeId())
                            .orElseThrow(() -> new IllegalArgumentException(
                                    ErrorCode.ID_SPECIFICATION_ATTRIBUTE_INVALID.getMessage()));
                    existingOption.setSpecificationAttribute(specificationAttribute);
                    optionUpdated = true;
                }

                if (optionUpdated) {
                    specificationAttributeOptionRepository.save(existingOption);
                }

                existingMapping.setSpecificationAttributeOption(existingOption);
                existingMapping.setCustomValue(existingOption.getName());
            } else {
                SpecificationAttributeOption specificationAttributeOption =
                        new SpecificationAttributeOption(request.getCustomValue());

                Optional.ofNullable(request.getDisplayOrder())
                        .ifPresent(specificationAttributeOption::setDisplayOrder);

                SpecificationAttributeOption finalSpecificationAttributeOption = specificationAttributeOption;
                Optional.ofNullable(request.getSpecificationAttributeId())
                        .ifPresent(specificationAttributeId -> {
                            SpecificationAttribute specificationAttribute = specificationAttributeRepository
                                    .findById(specificationAttributeId)
                                    .orElseThrow(() -> new IllegalArgumentException(
                                            ErrorCode.ID_SPECIFICATION_ATTRIBUTE_INVALID.getMessage()));
                            finalSpecificationAttributeOption.setSpecificationAttribute(specificationAttribute);
                        });

                specificationAttributeOption = specificationAttributeOptionRepository
                        .save(specificationAttributeOption);

                existingMapping.setSpecificationAttributeOption(specificationAttributeOption);
                existingMapping.setCustomValue(request.getCustomValue());
            }
        } else {
            SpecificationAttributeOption specificationAttributeOption = specificationAttributeOptionRepository
                    .findById(request.getSpecificationAttributeOptionId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            ErrorCode.ID_SPECIFICATION_ATTRIBUTE_OPTION_INVALID.getMessage()));

            boolean optionUpdated = false;

            if (request.getCustomValue() != null && !request.getCustomValue().equals(specificationAttributeOption.getName())) {
                specificationAttributeOption.setName(request.getCustomValue());
                optionUpdated = true;
            }

//            if (request.getDisplayOrder() != null && !request.getDisplayOrder().equals(specificationAttributeOption.getDisplayOrder())) {
//                specificationAttributeOption.setDisplayOrder(request.getDisplayOrder());
//                optionUpdated = true;
//            }

            if (request.getSpecificationAttributeId() != null &&
                    !request.getSpecificationAttributeId().equals(specificationAttributeOption.getSpecificationAttribute().getId())) {
                SpecificationAttribute specificationAttribute = specificationAttributeRepository
                        .findById(request.getSpecificationAttributeId())
                        .orElseThrow(() -> new IllegalArgumentException(
                                ErrorCode.ID_SPECIFICATION_ATTRIBUTE_INVALID.getMessage()));
                specificationAttributeOption.setSpecificationAttribute(specificationAttribute);
                optionUpdated = true;
            }

            if (optionUpdated) {
                specificationAttributeOptionRepository.save(specificationAttributeOption);
            }

            existingMapping.setSpecificationAttributeOption(specificationAttributeOption);
            existingMapping.setCustomValue(specificationAttributeOption.getName());
        }

        Optional.ofNullable(request.getCustomValue())
                .filter(value -> request.getSpecificationAttributeOptionId() == null)
                .ifPresent(existingMapping::setCustomValue);

        Optional.ofNullable(request.getAttributeType())
                .ifPresent(existingMapping::setAttributeType);

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
