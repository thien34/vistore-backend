package com.example.back_end.service.productTag.impl;

import com.example.back_end.core.admin.productTag.mapper.ProductTagMapper;
import com.example.back_end.core.admin.productTag.payload.request.ProductTagRequest;
import com.example.back_end.core.admin.productTag.payload.request.ProductTagSearchRequest;
import com.example.back_end.core.admin.productTag.payload.request.ProductTagUpdateRequest;
import com.example.back_end.core.admin.productTag.payload.response.ProductTagsResponse;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.entity.Product;
import com.example.back_end.entity.ProductProductTagMapping;
import com.example.back_end.entity.ProductTag;
import com.example.back_end.infrastructure.exception.ResourceNotFoundException;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.ProductProductTagMappingRepository;
import com.example.back_end.repository.ProductRepository;
import com.example.back_end.repository.ProductTagRepository;
import com.example.back_end.service.productTag.ProductTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProductTagServiceImpl implements ProductTagService {

    private final ProductTagRepository productTagRepository;
    private final ProductTagMapper productTagMapper;
    private final ProductRepository productRepository;
    private final ProductProductTagMappingRepository productProductTagMappingRepository;

    @Override
    public void createProductTag(ProductTagRequest request) {

        ProductTag productTag = saveProductTag(request);

        if (request.getProductId() != null) {
            Product product = getProduct(request.getProductId());
            saveProductTagMapping(product, productTag);
        }
    }

    @Override
    public void updateProductTag(Long id, ProductTagUpdateRequest request) {

        if (!productTagRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product tag with id not found: " + id);
        }

        ProductTag productTag = productTagMapper.toEntity(request);

        productTagRepository.save(productTag);
    }

    @Override
    public PageResponse1<List<ProductTagsResponse>> getAll(ProductTagSearchRequest searchRequest) {

        Pageable pageable = PageUtils.createPageable(
                searchRequest.getPageNo(),
                searchRequest.getPageSize(),
                searchRequest.getSortBy(),
                searchRequest.getSortDir());

        Page<ProductTagsResponse> productTagsPage = productTagRepository.findAllProductTagsWithCount(searchRequest.getName(), pageable);

        return PageResponse1.<List<ProductTagsResponse>>builder()
                .totalItems(productTagsPage.getTotalElements())
                .totalPages(productTagsPage.getTotalPages())
                .items(productTagsPage.getContent())
                .build();
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

    @Override
    public void createProductTags(List<ProductTag> productTags, Product product) {
        Set<String> tagNames = productTags.stream()
                .map(ProductTag::getName)
                .collect(Collectors.toSet());

        List<ProductTag> existingProductTags = productTagRepository.findByNameIn(tagNames);

        Map<String, ProductTag> existingTagMap = existingProductTags.stream()
                .collect(Collectors.toMap(ProductTag::getName, tag -> tag));

        List<ProductTag> newProductTags = new ArrayList<>();
        List<ProductTag> updatedProductTags = new ArrayList<>();

        for (ProductTag productTag : productTags) {
            ProductTag existingTag = existingTagMap.get(productTag.getName());
            if (existingTag != null) {
                existingTag.update(productTag);
                updatedProductTags.add(existingTag);
            } else {
                newProductTags.add(productTag);
            }
        }

        productTagRepository.saveAll(updatedProductTags);
        productTagRepository.saveAll(newProductTags);

        Stream.concat(updatedProductTags.stream(), newProductTags.stream())
                .forEach(tag -> saveProductTagMapping(product, tag));
    }

    @Override
    public List<ProductTag> getProductTagsByProductId(Long id) {
        return productProductTagMappingRepository.findByProductId(id)
                .stream()
                .map(ProductProductTagMapping::getProductTag)
                .toList();
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id not found: " + productId));
    }

    private ProductTag saveProductTag(ProductTagRequest request) {
        return productTagRepository.save(productTagMapper.toEntity(request));
    }

    private void saveProductTagMapping(Product product, ProductTag productTag) {
        boolean exists = productProductTagMappingRepository.existsByProductAndProductTag(product, productTag);
        if (!exists) {
            ProductProductTagMapping productTagMapping = ProductProductTagMapping.builder()
                    .product(product)
                    .productTag(productTag)
                    .build();
            productProductTagMappingRepository.save(productTagMapping);
        }
    }

}
