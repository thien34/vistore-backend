package com.example.back_end.service.product.impl;

import com.example.back_end.core.admin.product.mapper.ProductAttributeMapper;
import com.example.back_end.core.admin.product.payload.request.ProductAttributeRequest;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeNameResponse;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeResponse;
import com.example.back_end.service.product.ProductAttributeService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.ProductAttribute;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.constant.SortType;
import com.example.back_end.infrastructure.exception.AlreadyExistsException;
import com.example.back_end.infrastructure.exception.ExistsByNameException;
import com.example.back_end.infrastructure.exception.NotExistsException;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.infrastructure.utils.StringUtils;
import com.example.back_end.repository.ProductAttributeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductAttributeServiceImpl implements ProductAttributeService {

    ProductAttributeRepository productAttributeRepository;
    ProductAttributeMapper productAttributeMapper;

    @Override
    @Transactional
    public ProductAttribute createProductAttribute(ProductAttributeRequest request) {

        String trimmedName = StringUtils.sanitizeText(request.getName());
        if (productAttributeRepository.existsByName(trimmedName)) {
            throw new ExistsByNameException(ErrorCode.PRODUCT_ATTRIBUTE_EXISTED.getMessage());
        }

        ProductAttribute productAttribute = productAttributeMapper.toEntity(request);

        return productAttributeRepository.save(productAttribute);
    }

    @Override
    public PageResponse<List<ProductAttributeResponse>> getAllProductAttribute(String name, int pageNo, int pageSize) {

        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "id", SortType.DESC.getValue());
        Page<ProductAttribute> productAttributePage = productAttributeRepository.findByNameContaining(name, pageable);

        List<ProductAttributeResponse> productAttributeResponseList = productAttributePage.stream()
                .map(productAttributeMapper::toDto)
                .toList();

        return PageResponse.<List<ProductAttributeResponse>>builder()
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

        return productAttributeMapper.toDto(productAttribute);
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
        ProductAttribute savedProductAttribute = productAttributeRepository.save(productAttribute);

        return productAttributeMapper.toDto(savedProductAttribute);
    }

    @Override
    public void deleteProductAttribute(List<Long> ids) {

        List<ProductAttribute> productAttributes = productAttributeRepository.findAllById(ids);

        if (!productAttributes.isEmpty()) {
            productAttributeRepository.deleteAllInBatch(productAttributes);
        }
    }

    @Override
    public List<ProductAttributeNameResponse> getAttributeName() {
        return productAttributeRepository.findAllNameProductAttribute();
    }

}