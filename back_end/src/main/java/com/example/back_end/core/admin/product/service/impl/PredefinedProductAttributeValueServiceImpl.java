package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.mapper.PredefinedProductAttributeValueMapper;
import com.example.back_end.core.admin.product.payload.request.PredefinedProductAttributeValueRequest;
import com.example.back_end.core.admin.product.payload.response.PredefinedProductAttributeValueResponse;
import com.example.back_end.core.admin.product.service.PredefinedProductAttributeValueService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.PredefinedProductAttributeValue;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.exception.AlreadyExistsException;
import com.example.back_end.infrastructure.exception.DataIntegrityViolationException;
import com.example.back_end.infrastructure.exception.ExistsByNameException;
import com.example.back_end.infrastructure.exception.NotExistsException;
import com.example.back_end.infrastructure.exception.ResourceNotFoundException;
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

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PredefinedProductAttributeValueServiceImpl implements PredefinedProductAttributeValueService {

    ProductAttributeRepository productAttributeRepository;
    PredefinedProductAttributeValueRepository predefinedProductAttributeValueRepository;
    PredefinedProductAttributeValueMapper predefinedProductAttributeValueMapper;

    @Override
    @Transactional
    public PredefinedProductAttributeValue createProductAttributeValue(
            PredefinedProductAttributeValueRequest request
    ) {
        if (!productAttributeRepository.existsById(request.getProductAttribute()))
            throw new DataIntegrityViolationException(ErrorCode.PRODUCT_ATTRIBUTE_NOT_EXISTED.getMessage());

        if (predefinedProductAttributeValueRepository
                .existsByName(request.getName().trim().replaceAll("\\s+", " ")))
            throw new ExistsByNameException(ErrorCode.PREDEFINED_PRODUCT_ATTRIBUTE_NAME_EXISTED.getMessage());

        PredefinedProductAttributeValue value = predefinedProductAttributeValueMapper.toEntity(request);
        return predefinedProductAttributeValueRepository.save(value);
    }

    @Override
    public PageResponse<List<PredefinedProductAttributeValueResponse>> getAllPredefinedProductAttributeValue(
            String name,
            int pageNo,
            int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("displayOrder").descending());
        Page<PredefinedProductAttributeValue> valuePage =
                predefinedProductAttributeValueRepository.findByNameContaining(name, pageable);

        List<PredefinedProductAttributeValueResponse> responseList = valuePage.stream()
                .map(predefinedProductAttributeValueMapper::toDto)
                .toList();

        return PageResponse.<List<PredefinedProductAttributeValueResponse>>builder()
                .page(valuePage.getNumber())
                .size(valuePage.getSize())
                .totalPage(valuePage.getTotalPages())
                .items(responseList)
                .build();
    }

    @Override
    public PredefinedProductAttributeValueResponse getPredefinedAttributeValueById(Long id) {

        PredefinedProductAttributeValue value = predefinedProductAttributeValueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_NOT_EXISTED.getMessage()));

        return PredefinedProductAttributeValueResponse.mapToResponse(value);
    }

    @Override
    @Transactional
    public PredefinedProductAttributeValueResponse updatePredefinedAttributeValue(
            Long id,
            PredefinedProductAttributeValueRequest request
    ) {

        PredefinedProductAttributeValue value = predefinedProductAttributeValueRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorCode.PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_NOT_EXISTED.getMessage()));

        if (productAttributeRepository
                .existsByName(request.getName().trim().replaceAll("\\s+", " "))) {
            throw new AlreadyExistsException(ErrorCode.PRODUCT_ATTRIBUTE_EXISTED.getMessage());
        }

        predefinedProductAttributeValueMapper.updateEntity(request, value);
        PredefinedProductAttributeValue updatedValue = predefinedProductAttributeValueRepository.save(value);

        return PredefinedProductAttributeValueResponse.mapToResponse(updatedValue);
    }

    @Override
    public void deletePredefinedAttributeValue(Long id) {
        if (!predefinedProductAttributeValueRepository.existsById(id))
            throw new NotExistsException(ErrorCode.PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_NOT_EXISTED.getMessage());

        predefinedProductAttributeValueRepository.deleteById(id);
    }
}
