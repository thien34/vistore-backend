package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.mapper.SpecificationAttributeGroupMapper;
import com.example.back_end.core.admin.product.payload.request.SpecificationAttributeGroupRequest;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeGroupResponse;
import com.example.back_end.core.admin.product.service.SpecificationAttributeGroupService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.SpecificationAttributeGroup;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.exception.ExistsByNameException;
import com.example.back_end.repository.SpecificationAttributeGroupRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SpecificationAttributeGroupServiceImpl implements SpecificationAttributeGroupService {
    SpecificationAttributeGroupRepository specificationAttributeGroupRepository;
    SpecificationAttributeGroupMapper specificationAttributeGroupMapper;

    @Override
    public PageResponse<?> getAllSpecificationAttributeGroup(String name, int pageNo, int pageSize) {
        if (pageNo < 0 || pageSize <= 0) {
            throw new IllegalArgumentException("Invalid page number or page size");
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        Page<SpecificationAttributeGroup> specificationAttributeGroup = specificationAttributeGroupRepository.findByNameContaining(name, pageable);

        List<SpecificationAttributeGroupResponse> productTagRespons = specificationAttributeGroup.stream()
                .map(specificationAttributeGroupMapper::toDto)
                .sorted(Comparator.comparing(SpecificationAttributeGroupResponse::getId).reversed())
                .toList();

        return PageResponse.builder()
                .page(specificationAttributeGroup.getNumber())
                .size(specificationAttributeGroup.getSize())
                .totalPage(specificationAttributeGroup.getTotalPages())
                .items(productTagRespons)
                .build();
    }

    @Override
    @Transactional
    public SpecificationAttributeGroupResponse createSpecificationAttributeGroup(SpecificationAttributeGroupRequest request) {
        if (specificationAttributeGroupRepository.existsByName(request.getName()))
            throw new ExistsByNameException(ErrorCode.SPECIFICATION_ATTRIBUTE_GROUP_EXISTED.getMessage());

        SpecificationAttributeGroup group = SpecificationAttributeGroup.builder()
                .name(request.getName())
                .displayOrder(request.getDisplayOrder())
                .build();
        group = specificationAttributeGroupRepository.save(group);

        return SpecificationAttributeGroupResponse.builder()
                .id(group.getId())
                .name(group.getName())
                .displayOrder(group.getDisplayOrder())
                .build();
    }
}
