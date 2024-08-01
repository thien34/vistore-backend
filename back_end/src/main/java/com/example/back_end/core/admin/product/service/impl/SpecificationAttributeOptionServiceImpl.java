package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.mapper.SpecificationAttributeOptionMapper;
import com.example.back_end.core.admin.product.payload.request.SpecificationAttributeOptionRequest;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeOptionResponse;
import com.example.back_end.core.admin.product.service.SpecificationAttributeOptionService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.SpecificationAttribute;
import com.example.back_end.entity.SpecificationAttributeOption;
import com.example.back_end.infrastructure.constant.ErrorCode;
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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpecificationAttributeOptionServiceImpl implements SpecificationAttributeOptionService {

    SpecificationAttributeOptionRepository specificationAttributeOptionRepository;
    SpecificationAttributeOptionMapper specificationAttributeOptionMapper;
    SpecificationAttributeRepository specificationAttributeRepository;

    @Override
    public PageResponse<?> getAllSpecificationAttributeOption(String name, int pageNo, int pageSize) {

        if (pageNo < 0 || pageSize <= 0)
            throw new IllegalArgumentException("Invalid page number or page size");

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("displayOrder").descending());
        Page<SpecificationAttributeOption> specificationAttributeOption =
                specificationAttributeOptionRepository.findByNameContaining(name, pageable);

        List<SpecificationAttributeOptionResponse> productSpecificationResponse =
                specificationAttributeOption.stream()
                .map(specificationAttributeOptionMapper::toDto)
                .toList();

        return PageResponse.builder()
                .page(specificationAttributeOption.getNumber())
                .size(specificationAttributeOption.getSize())
                .totalPage(specificationAttributeOption.getTotalPages())
                .items(productSpecificationResponse)
                .build();

    }

    @Override
    @Transactional
    public SpecificationAttributeOptionResponse createSpecificationAttributeOption(
            SpecificationAttributeOptionRequest request
    ) {

        SpecificationAttributeOption specificationAttributeOption = SpecificationAttributeOption.builder()
                .name(request.getName())
                .colorSquaresRgb(request.getColorSquaresRgb())
                .displayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0)
                .build();

        if (request.getSpecificationAttributeId() != null) {
            SpecificationAttribute specificationAttribute = specificationAttributeRepository
                    .findById(request.getSpecificationAttributeId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            ErrorCode.SPECIFICATION_ATTRIBUTE_NOT_EXISTED.getMessage()));
            specificationAttributeOption.setSpecificationAttribute(specificationAttribute);
        }

        specificationAttributeOption = specificationAttributeOptionRepository.save(specificationAttributeOption);
        return specificationAttributeOptionMapper.toDto(specificationAttributeOption);

    }


    @Override
    public void deleteSpecificationAttributeOption(List<Long> ids) {

        List<SpecificationAttributeOption> spec = specificationAttributeOptionRepository.findAllById(ids);

        if (!spec.isEmpty()) {
            specificationAttributeOptionRepository.deleteAllInBatch(spec);
        }
    }

}
