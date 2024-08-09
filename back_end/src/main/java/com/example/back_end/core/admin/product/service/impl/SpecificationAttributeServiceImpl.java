package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.mapper.SpecificationAttributeMapper;
import com.example.back_end.core.admin.product.payload.request.SpecificationAttributeRequest;
import com.example.back_end.core.admin.product.payload.request.SpecificationAttributeUpdateRequest;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeOptionResponse;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeResponse;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeUpdateResponse;
import com.example.back_end.core.admin.product.service.SpecificationAttributeService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.ProductSpecificationAttributeMapping;
import com.example.back_end.entity.SpecificationAttribute;
import com.example.back_end.entity.SpecificationAttributeGroup;
import com.example.back_end.entity.SpecificationAttributeOption;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.exception.NotExistsException;
import com.example.back_end.repository.ProductSpecificationAttributeMappingRepository;
import com.example.back_end.repository.SpecificationAttributeGroupRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpecificationAttributeServiceImpl implements SpecificationAttributeService {

    SpecificationAttributeRepository specificationAttributeRepository;
    SpecificationAttributeMapper specificationAttributeMapper;
    SpecificationAttributeGroupRepository specificationAttributeGroupRepository;
    SpecificationAttributeOptionRepository specificationAttributeOptionRepository;
    ProductSpecificationAttributeMappingRepository productSpecificationAttributeMappingRepository;

    @Override
    public PageResponse<?> getAllSpecificationAttribute(String name, int pageNo, int pageSize) {
        if (pageNo < 0 || pageSize <= 0) {
            throw new IllegalArgumentException("Invalid page number or page size");
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("displayOrder").descending());
        Page<SpecificationAttribute> specificationAttributePage = specificationAttributeRepository
                .findByNameContaining(name, pageable);

        List<SpecificationAttributeResponse> responseList = specificationAttributePage.getContent().stream()
                .map(SpecificationAttributeResponse::mapToResponse)
                .toList();

        return PageResponse.builder()
                .page(specificationAttributePage.getNumber())
                .size(specificationAttributePage.getSize())
                .totalPage(specificationAttributePage.getTotalPages())
                .items(responseList)
                .build();
    }

    @Override
    public PageResponse<?> getAttributesWithNoGroupOrInvalidGroup(int pageNo, int pageSize) {

        if (pageNo < 0 || pageSize <= 0)
            throw new IllegalArgumentException("Invalid page number or page size");

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("displayOrder").descending());

        Page<SpecificationAttribute> specificationAttributePage = specificationAttributeRepository
                .findAttributesWithNoGroupOrInvalidGroup(pageable);

        List<SpecificationAttributeResponse> responseList = specificationAttributePage.getContent().stream()
                .map(specificationAttributeMapper::toDto)
                .toList();

        return PageResponse.builder()
                .page(specificationAttributePage.getNumber())
                .size(specificationAttributePage.getSize())
                .totalPage(specificationAttributePage.getTotalPages())
                .items(responseList)
                .build();
    }

    @Override
    public SpecificationAttributeResponse createSpecificationAttribute(SpecificationAttributeRequest request) {

        SpecificationAttributeGroup specificationAttributeGroup = null;

        if (request.getSpecificationAttributeGroupId() != null) {
            Optional<SpecificationAttributeGroup> specificationAttributeGroupOptional =
                    specificationAttributeGroupRepository.findById(request.getSpecificationAttributeGroupId());

            specificationAttributeGroup = specificationAttributeGroupOptional.orElse(null);
        }

        Integer displayOrder = request.getDisplayOrder() != null ? request.getDisplayOrder() : 0;

        SpecificationAttribute specificationAttribute = SpecificationAttribute.builder()
                .name(request.getName())
                .displayOrder(displayOrder)
                .specificationAttributeGroup(specificationAttributeGroup)
                .build();

        specificationAttribute = specificationAttributeRepository.save(specificationAttribute);

        return specificationAttributeMapper.toDto(specificationAttribute);

    }

    @Override
    @Transactional
    public SpecificationAttributeUpdateResponse editSpecificationAttribute(
            Long id,
            SpecificationAttributeUpdateRequest request
    ) {

        SpecificationAttribute specificationAttribute = specificationAttributeRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorCode.SPECIFICATION_ATTRIBUTE_NOT_EXISTED.getMessage()));

        specificationAttribute.setName(request.getName());
        specificationAttribute.setDisplayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0);

        if (request.getSpecificationAttributeGroupId() != null && !request
                .getSpecificationAttributeGroupId().trim().isEmpty()) {
            SpecificationAttributeGroup specificationAttributeGroup = specificationAttributeGroupRepository
                    .findById(Long.parseLong(request.getSpecificationAttributeGroupId()))
                    .orElseThrow(() -> new NotExistsException(
                            ErrorCode.SPECIFICATION_ATTRIBUTE_GROUP_NOT_EXISTED.getMessage()));
            specificationAttribute.setSpecificationAttributeGroup(specificationAttributeGroup);
        } else {
            specificationAttribute.setSpecificationAttributeGroup(null);
        }

        Map<Long, SpecificationAttributeOption> existingOptionsMap =
                specificationAttributeOptionRepository.findBySpecificationAttributeId(id)
                        .stream()
                        .collect(Collectors.toMap(SpecificationAttributeOption::getId, option -> option));

        SpecificationAttribute finalSpecificationAttribute = specificationAttribute;

        List<SpecificationAttributeOption> updatedOptions = request.getListOptions().stream().map(optionRequest -> {
            SpecificationAttributeOption option = existingOptionsMap.getOrDefault(optionRequest.getId(),
                    SpecificationAttributeOption.builder().specificationAttribute(finalSpecificationAttribute).build());
            option.setName(optionRequest.getName());
            option.setColorSquaresRgb(optionRequest.getColorSquaresRgb());
            option.setDisplayOrder(optionRequest.getDisplayOrder());
            if (option.getProductSpecificationAttributeMappings() != null) {
                option.getProductSpecificationAttributeMappings().forEach(mapping -> {
                    mapping.setCustomValue(optionRequest.getName());
                    productSpecificationAttributeMappingRepository.save(mapping);
                });
            }
            return option;
        }).toList();

        specificationAttributeOptionRepository.saveAll(updatedOptions);

        List<Long> updatedOptionIds = updatedOptions.stream()
                .map(SpecificationAttributeOption::getId)
                .filter(Objects::nonNull)
                .toList();

        specificationAttributeOptionRepository.deleteAll(existingOptionsMap.values().stream()
                .filter(option -> !updatedOptionIds.contains(option.getId()))
                .toList());

        specificationAttribute.setSpecificationAttributeOptions(new ArrayList<>(updatedOptions));
        specificationAttribute = specificationAttributeRepository.save(specificationAttribute);

        return SpecificationAttributeUpdateResponse.builder()
                .id(specificationAttribute.getId())
                .name(specificationAttribute.getName())
                .specificationAttributeGroupId(specificationAttribute.getSpecificationAttributeGroup() != null ?
                        specificationAttribute.getSpecificationAttributeGroup().getId() : null)
                .specificationAttributeGroupName(specificationAttribute.getSpecificationAttributeGroup() != null ?
                        specificationAttribute.getSpecificationAttributeGroup().getName() : null)
                .displayOrder(specificationAttribute.getDisplayOrder())
                .listOptions(specificationAttribute.getSpecificationAttributeOptions().stream()
                        .map(option -> new SpecificationAttributeOptionResponse(
                                option.getId(),
                                option.getName(),
                                option.getColorSquaresRgb(),
                                option.getDisplayOrder(),
                                option.getProductSpecificationAttributeMappings(),
                                option.getSpecificationAttribute().getId()
                        ))
                        .toList())
                .build();

    }

    @Override
    public void deleteSpecificationAttribute(List<Long> ids) {

        List<SpecificationAttribute> spec = specificationAttributeRepository.findAllById(ids);

        if (!spec.isEmpty()) {
            specificationAttributeRepository.deleteAllInBatch(spec);
        }

    }

    @Override
    public SpecificationAttributeResponse getSpecificationAttributeById(Long id) {

        SpecificationAttribute productAttribute = specificationAttributeRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorCode.SPECIFICATION_ATTRIBUTE_NOT_EXISTED.getMessage()));

        return SpecificationAttributeResponse.mapToResponse(productAttribute);

    }

}
