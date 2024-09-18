package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.mapper.PredefinedProductAttributeValueMapper;
import com.example.back_end.core.admin.product.payload.request.PredefinedProductAttributeValueRequest;
import com.example.back_end.core.admin.product.payload.response.PredefinedProductAttributeValueResponse;
import com.example.back_end.core.admin.product.service.PredefinedProductAttributeValueService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.PredefinedProductAttributeValue;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.constant.SortType;
import com.example.back_end.infrastructure.exception.AlreadyExistsException;
import com.example.back_end.infrastructure.exception.DataIntegrityViolationException;
import com.example.back_end.infrastructure.exception.ExistsByNameException;
import com.example.back_end.infrastructure.exception.NotExistsException;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.infrastructure.utils.StringUtils;
import com.example.back_end.repository.PredefinedProductAttributeValueRepository;
import com.example.back_end.repository.ProductAttributeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public void createProductAttributeValue(
            PredefinedProductAttributeValueRequest request
    ) {
        if (!productAttributeRepository.existsById(request.getProductAttribute()))
            throw new DataIntegrityViolationException(ErrorCode.PRODUCT_ATTRIBUTE_NOT_EXISTED.getMessage());

        if (predefinedProductAttributeValueRepository
                .existsByName(StringUtils.sanitizeText(request.getName())))
            throw new ExistsByNameException(ErrorCode.PREDEFINED_PRODUCT_ATTRIBUTE_NAME_EXISTED.getMessage());

        PredefinedProductAttributeValue value = predefinedProductAttributeValueMapper.toEntity(request);
        predefinedProductAttributeValueRepository.save(value);
    }

    @Override
    public PageResponse<List<PredefinedProductAttributeValueResponse>> getAllPredefinedProductAttributeValue(
            String name, int pageNo, int pageSize
    ) {

        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "displayOrder", SortType.DESC.getValue());
        Page<PredefinedProductAttributeValue> valuePage =
                predefinedProductAttributeValueRepository.findByNameContaining(name, pageable);

        List<PredefinedProductAttributeValueResponse> responseList = predefinedProductAttributeValueMapper
                .toListDto(valuePage.getContent());

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
                .orElseThrow(() -> new NotExistsException(
                        ErrorCode.PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_NOT_EXISTED.getMessage()));

        return PredefinedProductAttributeValueResponse.mapToResponse(value);
    }

    @Override
    @Transactional
    public void updatePredefinedAttributeValue(Long id, PredefinedProductAttributeValueRequest request) {
        PredefinedProductAttributeValue value = predefinedProductAttributeValueRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(
                        ErrorCode.PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_NOT_EXISTED.getMessage()));

        if (productAttributeRepository
                .existsByName(StringUtils.sanitizeText(request.getName()))) {
            throw new AlreadyExistsException(ErrorCode.PRODUCT_ATTRIBUTE_EXISTED.getMessage());
        }

        predefinedProductAttributeValueMapper.updateEntity(request, value);
        predefinedProductAttributeValueRepository.save(value);
    }


    @Override
    public void deletePredefinedAttributeValue(Long id) {
        if (!predefinedProductAttributeValueRepository.existsById(id))
            throw new NotExistsException(ErrorCode.PREDEFINED_PRODUCT_ATTRIBUTE_VALUE_NOT_EXISTED.getMessage());

        predefinedProductAttributeValueRepository.deleteById(id);
    }
}