package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.mapper.ProductSpecificationAttributeMappingMapper;
import com.example.back_end.core.admin.product.payload.request.ProductSpecificationAttributeMappingRequest;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingResponse;
import com.example.back_end.core.admin.product.service.ProductSpecificationAttributeMappingService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.ProductSpecificationAttributeMapping;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.repository.ProductSpecificationAttributeMappingRepository;
import com.example.back_end.repository.ProductRepository;
import com.example.back_end.repository.SpecificationAttributeOptionRepository;
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
public class ProductSpecificationAttributeMappingServiceImpl implements ProductSpecificationAttributeMappingService {
    ProductSpecificationAttributeMappingRepository productSpecificationAttributeMappingRepository;
    ProductSpecificationAttributeMappingMapper productSpecificationAttributeMappingMapper;
    ProductRepository productRepository;
    SpecificationAttributeOptionRepository specificationAttributeOptionRepository;

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
        //Once you have the product ID
        /*
        mapping.setProduct(productRepository.findById(request.getProductId()).orElseThrow(() -> new IllegalArgumentException("Invalid product ID")));
        */
        mapping.setSpecificationAttributeOption(specificationAttributeOptionRepository
                .findById(request.getSpecificationAttributeOptionId()).orElseThrow(
                        () -> new IllegalArgumentException(ErrorCode.SPECIFICATION_ATTRIBUTE_OPTION_NOT_EXISTS.getMessage())));
        mapping = productSpecificationAttributeMappingRepository.save(mapping);
        return productSpecificationAttributeMappingMapper.toDto(mapping);
    }

    @Override
    public ProductSpecificationAttributeMappingResponse getProductSpecificationAttributeMappingById(Long id) {
        ProductSpecificationAttributeMapping mapping =
                productSpecificationAttributeMappingRepository
                        .findById(id).orElseThrow(
                                () -> new IllegalArgumentException(
                                        ErrorCode.PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_EXISTED.getMessage()));
        return productSpecificationAttributeMappingMapper.toDto(mapping);
    }

    @Override
    public void deleteProductSpecificationAttribute(List<Long> ids) {
        List<ProductSpecificationAttributeMapping> spec = productSpecificationAttributeMappingRepository.findAllById(ids);

        if (!spec.isEmpty()) {
            productSpecificationAttributeMappingRepository.deleteAllInBatch(spec);
        }
    }
}
