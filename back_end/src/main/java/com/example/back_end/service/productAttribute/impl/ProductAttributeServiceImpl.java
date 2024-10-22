package com.example.back_end.service.productAttribute.impl;

import com.example.back_end.core.admin.productAttribute.mapper.ProductAttributeMapper;
import com.example.back_end.core.admin.productAttribute.payload.request.ProdAttrSearchRequest;
import com.example.back_end.core.admin.productAttribute.payload.request.ProductAttributeRequest;
import com.example.back_end.core.admin.productAttribute.payload.response.ProductAttributeNameResponse;
import com.example.back_end.core.admin.productAttribute.payload.response.ProductAttributeResponse;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.entity.ProductAttribute;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.exception.AlreadyExistsException;
import com.example.back_end.infrastructure.exception.ExistsByNameException;
import com.example.back_end.infrastructure.exception.NotExistsException;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.infrastructure.utils.StringUtils;
import com.example.back_end.repository.ProductAttributeRepository;
import com.example.back_end.service.productAttribute.ProductAttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAttributeServiceImpl implements ProductAttributeService {

    private final ProductAttributeRepository productAttributeRepository;
    private final ProductAttributeMapper productAttributeMapper;

    @Override
    public void createProductAttribute(ProductAttributeRequest request) {

        String trimmedName = StringUtils.sanitizeText(request.getName());
        if (productAttributeRepository.existsByName(trimmedName)) {
            throw new ExistsByNameException(ErrorCode.PRODUCT_ATTRIBUTE_EXISTED.getMessage());
        }

        ProductAttribute productAttribute = productAttributeMapper.toEntity(request);
        productAttributeRepository.save(productAttribute);
    }

    @Override
    public void updateProductAttribute(Long id, ProductAttributeRequest request) {

        ProductAttribute productAttribute = productAttributeRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorCode.PRODUCT_ATTRIBUTE_NOT_EXISTED.getMessage()));

        if (productAttributeRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new AlreadyExistsException(ErrorCode.PRODUCT_ATTRIBUTE_ALREADY_EXISTS.getMessage());
        }

        productAttributeMapper.updateEntityFromRequest(request, productAttribute);
        productAttributeRepository.save(productAttribute);
    }

    @Override
    public PageResponse1<List<ProductAttributeResponse>> getAllProductAttribute(ProdAttrSearchRequest searchRequest) {

        Pageable pageable = PageUtils.createPageable(
                searchRequest.getPageNo(),
                searchRequest.getPageSize(),
                searchRequest.getSortBy(),
                searchRequest.getSortDir());

        Page<ProductAttribute> productAttributePage = productAttributeRepository.
                findAll(ProdAttrSpecification.filterByName(searchRequest.getName()), pageable);

        List<ProductAttributeResponse> productAttributeResponses = productAttributeMapper.toDtos(productAttributePage.getContent());

        return PageResponse1.<List<ProductAttributeResponse>>builder()
                .totalItems(productAttributePage.getTotalElements())
                .totalPages(productAttributePage.getTotalPages())
                .items(productAttributeResponses)
                .build();
    }

    @Override
    public ProductAttributeResponse getProductAttribute(Long id) {

        ProductAttribute productAttribute = productAttributeRepository.findById(id)
                .orElseThrow(() -> new AlreadyExistsException(ErrorCode.PRODUCT_ATTRIBUTE_EXISTED.getMessage()));

        return productAttributeMapper.toDto(productAttribute);
    }


    @Override
    public void deleteProductAttributes(List<Long> ids) {

        List<ProductAttribute> productAttributes = productAttributeRepository.findAllById(ids);

        if (!productAttributes.isEmpty()) {
            productAttributeRepository.deleteAllInBatch(productAttributes);
        }
    }

    @Override
    public List<ProductAttributeNameResponse> getAttributesName() {

        return productAttributeRepository.findAllNameProductAttribute();
    }

}