package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.mapper.ProductTagMapper;
import com.example.back_end.core.admin.product.payload.request.ProductTagRequest;
import com.example.back_end.core.admin.product.payload.response.ProductTagResponse;
import com.example.back_end.core.admin.product.service.ProductTagService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.Product;
import com.example.back_end.entity.ProductProductTagMapping;
import com.example.back_end.entity.ProductTag;
import com.example.back_end.infrastructure.constant.SortType;
import com.example.back_end.infrastructure.exception.ResourceNotFoundException;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.ProductProductTagMappingRepository;
import com.example.back_end.repository.ProductRepository;
import com.example.back_end.repository.ProductTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
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
    public PageResponse<List<ProductTagResponse>> getAll(String name, int pageNo, int pageSize) {

        // Fetch all matching items with pagination and sort by ID in descending order directly in the query
        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "id", SortType.DESC.getValue());
        Page<ProductTag> productTagPage = productTagRepository.findByNameContaining(name, pageable);

        // Map to DTO responses
        List<ProductTagResponse> productTagResponses = productTagMapper.toDtoList(productTagPage.getContent());

        return PageResponse.<List<ProductTagResponse>>builder()
                .page(productTagPage.getNumber())
                .size(productTagPage.getSize())
                .totalPage(productTagPage.getTotalPages())
                .items(productTagResponses)
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

}
