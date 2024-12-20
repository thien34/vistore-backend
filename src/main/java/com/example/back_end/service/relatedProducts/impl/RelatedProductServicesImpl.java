package com.example.back_end.service.relatedProducts.impl;

import com.example.back_end.core.admin.relatedProducts.mapper.RelatedProductMapper;
import com.example.back_end.core.admin.relatedProducts.payload.request.RelatedProductRequest;
import com.example.back_end.core.admin.relatedProducts.payload.response.RelatedProductResponse;
import com.example.back_end.service.relatedProducts.RelatedProductServices;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.RelatedProduct;
import com.example.back_end.infrastructure.constant.SortType;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.RelatedProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RelatedProductServicesImpl implements RelatedProductServices {

    private final RelatedProductRepository repo;
    private final RelatedProductMapper mapper;

    @Override
    public void addRelatedProducts(List<RelatedProductRequest> lstRelatedProductRequest) {
        List<RelatedProduct> relatedProductList = lstRelatedProductRequest.stream()
                .map(mapper::mapToRelatedProduct).filter(relatedProduct -> !isDuplicate(relatedProduct)).toList();
        repo.saveAll(relatedProductList);
    }

    boolean isDuplicate(RelatedProduct relatedProduct) {
        Optional<RelatedProduct> existingProduct = repo.findByProduct1IdAndProduct2Id(relatedProduct.getProduct1().getId(), relatedProduct.getProduct2().getId());
        return existingProduct.isPresent();
    }

    @Override
    public void updateRelatedProducts(Long id, RelatedProductRequest relatedProductRequest) {
        RelatedProduct relatedProduct = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Not Found Related Product Record with ID: " + id));
        mapper.updateRelatedProduct(relatedProduct, relatedProductRequest);
        repo.save(relatedProduct);
    }

    @Override
    public void deleteRelatedProducts(List<Long> id) {
        repo.deleteAllById(id);
    }

    @Override
    public PageResponse<List<RelatedProductResponse>> getAll(Long id, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "displayOrder", SortType.ASC.getValue());
        Page<RelatedProduct> relatedProductPage = repo.findByProduct1Id(id, pageable);
        List<RelatedProductResponse> relatedProductResponses = mapper.mapToListRelatedProductResponse(relatedProductPage);
        return PageResponse.<List<RelatedProductResponse>>builder()
                .page(relatedProductPage.getNumber())
                .size(relatedProductPage.getSize())
                .totalPage(relatedProductPage.getTotalPages())
                .items(relatedProductResponses)
                .build();
    }

}
