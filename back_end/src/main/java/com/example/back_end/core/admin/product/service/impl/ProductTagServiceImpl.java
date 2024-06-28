package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.mapper.ProductTagMapper;
import com.example.back_end.core.admin.product.payload.request.ProductTagRequest;
import com.example.back_end.core.admin.product.payload.response.ProductTagResponse;
import com.example.back_end.core.admin.product.service.ProductTagService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.Product;
import com.example.back_end.entity.ProductProductTagMapping;
import com.example.back_end.entity.ProductTag;
import com.example.back_end.infrastructure.exception.ResourceNotFoundException;
import com.example.back_end.repository.ProductProductTagMappingRepository;
import com.example.back_end.repository.ProductRepository;
import com.example.back_end.repository.ProductTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductTagServiceImpl implements ProductTagService {

    private final ProductTagRepository productTagRepository;
    private final ProductTagMapper productTagMapper;
    private final ProductRepository productRepository;
    private final ProductProductTagMappingRepository productProductTagMappingRepository;

    @Override
    public void createProductTag(ProductTagRequest request) {
        Product product = getProduct(request.getProductId());
        ProductTag productTag = saveProductTag(request);

        saveProductTagMapping(product, productTag);
    }

    @Override
    public PageResponse<?> getAll(String name, int pageNo, int pageSize) {
        if (pageNo < 0 || pageSize <= 0) {
            throw new IllegalArgumentException("Invalid page number or page size");
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());

        // Fetch all matching items with pagination and sort by ID in descending order directly in the query
        Page<ProductTag> productTagPage = productTagRepository.findByNameContaining(name, pageable);

        // Map to DTO responses
        List<ProductTagResponse> productTagRespons = productTagPage.stream()
                .map(productTagMapper::toDto)
                .sorted(Comparator.comparing(ProductTagResponse::getId).reversed())
                .toList();

        // Build the page response
        return PageResponse.builder()
                .page(productTagPage.getNumber())
                .size(productTagPage.getSize())
                .totalPage(productTagPage.getTotalPages())
                .items(productTagRespons)
                .build();
    }

    @Override
    public ProductTagResponse getProductTag(Long id) {
        ProductTag productTag = productTagRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product tag with id not found: " + id));

        return productTagMapper.toDto(productTag);
    }

    @Override
    @Transactional
    public void delete(List<Long> ids) {
        List<ProductTag> productTags = productTagRepository.findAllById(ids);

        List<ProductProductTagMapping> mappingsToDelete = new ArrayList<>();

        for (ProductTag productTag : productTags) {
            List<ProductProductTagMapping> mappings = productTag.getProductProductTagMappings();
            if (mappings != null && !mappings.isEmpty()) {
                mappingsToDelete.addAll(mappings);
            }
        }

        productProductTagMappingRepository.deleteAllInBatch(mappingsToDelete);

        productTagRepository.deleteAllInBatch(productTags);
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id not found: " + productId));
    }

    private ProductTag saveProductTag(ProductTagRequest request) {
        return productTagRepository.save(productTagMapper.toEntity(request));
    }

    private void saveProductTagMapping(Product product, ProductTag productTag) {
        ProductProductTagMapping productTagMapping = ProductProductTagMapping.builder()
                .product(product)
                .productTag(productTag)
                .build();
        productProductTagMappingRepository.save(productTagMapping);
    }

    private PageResponse<?> convertToPageResponse(Page<ProductTag> productTagPage, Pageable pageable) {
        List<ProductTagResponse> response = productTagPage.getContent()
                .stream()
                .map(productTagMapper::toDto)
                .toList();

        return PageResponse.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .totalPage(productTagPage.getTotalPages())
                .items(response).build();
    }
}
