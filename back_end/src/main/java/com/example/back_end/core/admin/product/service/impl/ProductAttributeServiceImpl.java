package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.mapper.ProductAttributeMapper;
import com.example.back_end.core.admin.product.payload.request.ProductAttributeRequest;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeResponse;
import com.example.back_end.core.admin.product.service.ProductAttributeService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.ProductAttribute;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.exception.StoreException;
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

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductAttributeServiceImpl implements ProductAttributeService {
    ProductAttributeRepository productAttributeRepository;
    ProductAttributeMapper productAttributeMapper;

    @Override
    @Transactional
    public ProductAttribute createProductAttribute(ProductAttributeRequest request) {
        if (productAttributeRepository.existsByName(request.getName()))
            throw new StoreException(ErrorCode.USER_EXISTED);
        ProductAttribute productAttribute = productAttributeMapper.toEntity(request);
        return productAttributeRepository.save(productAttribute);
    }

    @Override
    public PageResponse<?> getAll(String name, int pageNo, int pageSize) {
        if (pageNo < 0 || pageSize <= 0) {
            throw new StoreException(ErrorCode.INVALID_PAGE_NUMBER_OR_PAGE_SIZE);
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        Page<ProductAttribute> productAttribute = productAttributeRepository.findByNameContaining(name, pageable);
        List<ProductAttributeResponse> productAttributeResponse = productAttribute.stream()
                .map(productAttributeMapper::toDto)
                .sorted(Comparator.comparing(ProductAttributeResponse::getId).reversed())
                .toList();

        return PageResponse.builder()
                .page(productAttribute.getNumber())
                .size(productAttribute.getSize())
                .totalPage(productAttribute.getTotalPages())
                .items(productAttributeResponse)
                .build();
    }

    @Override
    public ProductAttributeResponse getProductAttributeById(Long id) {
        ProductAttribute productAttribute = productAttributeRepository.findById(id)
                .orElseThrow(() -> new StoreException(ErrorCode.PRODUCT_ATTRIBUTE_NOT_EXISTED));
        return ProductAttributeResponse.mapToResponse(productAttribute);
    }

    @Override
    @Transactional
    public ProductAttributeResponse updateProductAttribute(Long id, ProductAttributeRequest request) {
        ProductAttribute productAttribute = productAttributeRepository.findById(id)
                .orElseThrow(() -> new StoreException(ErrorCode.PRODUCT_ATTRIBUTE_NOT_EXISTED));

        productAttribute.setName(request.getName());
        productAttribute.setDescription(request.getDescription());

        ProductAttribute updatedProductAttribute = productAttributeRepository.save(productAttribute);
        return ProductAttributeResponse.mapToResponse(updatedProductAttribute);
    }


    @Override
    @Transactional
    public void deleteProductAttribute(Long id) {
        if (!productAttributeRepository.existsById(id)) {
            throw new StoreException(ErrorCode.PRODUCT_ATTRIBUTE_NOT_EXISTED);
        }
         productAttributeRepository.deleteById(id);
    }

//    private PageResponse<?> convertToPageResponse(Page<ProductAttribute> productAttributePage, Pageable pageable) {
//        List<ProductAttributeResponse> response = productAttributeMapper.toDtos(productAttributePage);
//
//        return PageResponse.builder()
//                .page(pageable.getPageNumber())
//                .size(pageable.getPageSize())
//                .totalPage(productAttributePage.getTotalPages())
//                .items(response).build();
//    }

}
