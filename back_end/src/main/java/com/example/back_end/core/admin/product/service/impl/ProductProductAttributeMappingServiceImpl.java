package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.mapper.ProductAttributeValueMapper;
import com.example.back_end.core.admin.product.mapper.ProductProductAttributeMappingMapper;
import com.example.back_end.core.admin.product.payload.request.ProductProductAttributeMappingRequest;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeValueResponse;
import com.example.back_end.core.admin.product.payload.response.ProductProductAttributeMappingDetailResponse;
import com.example.back_end.core.admin.product.payload.response.ProductProductAttributeMappingResponse;
import com.example.back_end.core.admin.product.service.ProductAttributeValueService;
import com.example.back_end.core.admin.product.service.ProductProductAttributeMappingService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.Product;
import com.example.back_end.entity.ProductAttribute;
import com.example.back_end.entity.ProductAttributeValue;
import com.example.back_end.entity.ProductAttributeValuePicture;
import com.example.back_end.entity.ProductProductAttributeMapping;
import com.example.back_end.infrastructure.exception.ResourceNotFoundException;
import com.example.back_end.repository.ProductAttributeRepository;
import com.example.back_end.repository.ProductAttributeValuePictureRepository;
import com.example.back_end.repository.ProductAttributeValueRepository;
import com.example.back_end.repository.ProductProductAttributeMappingRepository;
import com.example.back_end.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductProductAttributeMappingServiceImpl implements ProductProductAttributeMappingService {

    private final ProductRepository productRepository;
    private final ProductAttributeRepository productAttributeRepository;
    private final ProductAttributeValueRepository productAttributeValueRepository;
    private final ProductProductAttributeMappingRepository productProductAttributeMappingRepository;
    private final ProductProductAttributeMappingMapper productProductAttributeMappingMapper;
    private final ProductAttributeValuePictureRepository productAttributeValuePictureRepository;
    private final ProductAttributeValueService productAttributeValueService;
    private final ProductAttributeValueMapper productAttributeValueMapper;

    @Override
    public PageResponse<List<ProductProductAttributeMappingResponse>> getProductProductAttributeMappings(Long productId, int pageNo, int pageSize) {
        validatePageRequest(pageNo, pageSize);
        getProductOrThrow(productId);

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("id").descending());
        Page<ProductProductAttributeMapping> page = productProductAttributeMappingRepository
                .findAllByProductId(productId, pageable);
        List<ProductProductAttributeMappingResponse> responses = productProductAttributeMappingMapper
                .toDtos(page.getContent());

        return PageResponse.<List<ProductProductAttributeMappingResponse>>builder()
                .page(page.getNumber())
                .size(page.getSize())
                .totalPage(page.getTotalPages())
                .items(responses)
                .build();
    }

    @Override
    public ProductProductAttributeMappingDetailResponse getProductProductAttributeMapping(Long id) {
        ProductProductAttributeMapping attributeMapping = getMappingOrThrow(id);

        List<ProductAttributeValue> productAttributeValue = productAttributeValueRepository
                .findAllByProductAttributeMapping(attributeMapping)
                .orElseThrow(() -> new ResourceNotFoundException("Product attribute value with id not found: " + id));

        List<ProductAttributeValueResponse> productAttributeValueResponses = productAttributeValueMapper
                .toDtos(productAttributeValue);

        productAttributeValueResponses.forEach(productAttributeValueResponse -> {
            List<ProductAttributeValuePicture> productAttributeValuePictures = productAttributeValuePictureRepository
                    .findAllByProductAttributeValueId(productAttributeValueResponse.getId());
            List<String> imageUrls = productAttributeValuePictures.stream()
                    .map(picture -> picture.getPicture().getLinkImg())
                    .toList();

            productAttributeValueResponse.setImageUrl(imageUrls);
        });

        return ProductProductAttributeMappingDetailResponse.builder()
                .id(attributeMapping.getId())
                .productId(attributeMapping.getProduct().getId())
                .productAttributeId(attributeMapping.getProductAttribute().getId())
                .textPrompt(attributeMapping.getTextPrompt())
                .isRequired(attributeMapping.getIsRequired())
                .attributeControlTypeId(attributeMapping.getAttributeControlTypeId().getLabel())
                .displayOrder(attributeMapping.getDisplayOrder())
                .productAttributeValueResponses(productAttributeValueResponses)
                .build();
    }

    @Override
    @Transactional
    public void addProductProductAttributeMapping(ProductProductAttributeMappingRequest request) {
        getProductOrThrow(request.getProductId());
        validateProductAttribute(request.getProductAttributeId());
        checkProductAttributeExits(request.getProductAttributeId(), request.getProductId());

        ProductProductAttributeMapping attributeMapping = productProductAttributeMappingMapper.toEntity(request);
        ProductProductAttributeMapping attributeMappingSaved =
                productProductAttributeMappingRepository.save(attributeMapping);

        if (request.getProductAttributeValueRequests() != null) {
            productAttributeValueService.createProductAttributeValue(
                    request.getProductAttributeValueRequests(),
                    attributeMappingSaved.getId()
            );
        }
    }

    @Override
    @Transactional
    public void updateProductProductAttributeMapping(Long id, ProductProductAttributeMappingRequest request) {
        getMappingOrThrow(id);
        getProductOrThrow(request.getProductId());
        validateProductAttribute(request.getProductAttributeId());

        ProductProductAttributeMapping attributeMapping = productProductAttributeMappingMapper.toEntity(request);
        productProductAttributeMappingRepository.save(attributeMapping);

        if (request.getProductAttributeValueRequests() != null) {
            productAttributeValueService.createProductAttributeValue(request.getProductAttributeValueRequests(), id);
        }
    }

    @Override
    public void deleteProductProductAttributeMapping(Long id) {
        if (!productProductAttributeMappingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product product attribute mapping with id not found: " + id);
        }
        productProductAttributeMappingRepository.deleteById(id);
    }

    private void validatePageRequest(int pageNo, int pageSize) {
        if (pageNo < 1 || pageSize <= 0) {
            throw new IllegalArgumentException("Invalid page number or page size");
        }
    }

    private void getProductOrThrow(Long productId) {
        boolean exists = productRepository.existsById(productId);
        if (!exists) {
            throw new ResourceNotFoundException("Product with id not found: " + productId);
        }
    }

    private ProductProductAttributeMapping getMappingOrThrow(Long id) {
        return productProductAttributeMappingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product product attribute mapping with id not found: " + id));
    }

    public void validateProductAttribute(Long productAttributeId) {
        if (productAttributeId != null && !productAttributeRepository.existsById(productAttributeId)) {
            throw new ResourceNotFoundException("Product attribute with id not found: " + productAttributeId);
        }
    }

    public void checkProductAttributeExits(Long productAttributeId, Long productId) {

        ProductAttribute productAttribute = productAttributeRepository.findById(productAttributeId)
                .orElseThrow(() -> new ResourceNotFoundException("Product attribute with id not found: " + productAttributeId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id not found: " + productId));

        if (productProductAttributeMappingRepository.existsByProductAttributeAndProduct(productAttribute, product)) {
            throw new IllegalArgumentException("Product attribute already mapped");
        }
    }

    @Override
    public List<ProductProductAttributeMappingDetailResponse> getProductProductAttributeMappingByproductId(Long productId) {

        return productProductAttributeMappingRepository.findByProductId(productId).stream()
                .map(mapping -> ProductProductAttributeMappingDetailResponse.builder()
                        .id(mapping.getId())
                        .attName(mapping.getProductAttribute().getName())
                        .productId(mapping.getProduct().getId())
                        .productAttributeId(mapping.getProductAttribute().getId())
                        .textPrompt(mapping.getTextPrompt())
                        .isRequired(mapping.getIsRequired())
                        .attributeControlTypeId(mapping.getAttributeControlTypeId() != null ? mapping.getAttributeControlTypeId().name() : null)
                        .displayOrder(mapping.getDisplayOrder())
                        .productAttributeValueResponses(fetchProductAttributeValues(mapping.getId()))
                        .build())
                .toList();
    }


    private List<ProductAttributeValueResponse> fetchProductAttributeValues(Long productAttributeMappingId) {
        List<ProductAttributeValue> values = productAttributeValueRepository
                .findByProductAttributeMappingId(productAttributeMappingId);

        return values.stream()
                .map(productAttributeValueMapper::toDto)
                .toList();
    }

}
