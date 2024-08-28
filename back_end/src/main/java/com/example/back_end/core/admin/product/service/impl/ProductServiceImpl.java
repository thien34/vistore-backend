package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.mapper.ProductMapper;
import com.example.back_end.core.admin.product.payload.response.ProductFakeResponse;
import com.example.back_end.core.admin.product.payload.response.ProductNameResponse;
import com.example.back_end.core.admin.product.service.ProductService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.Product;
import com.example.back_end.infrastructure.constant.SortType;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductNameResponse> getAllProductsName() {
        return productRepository.findAll().stream()
                .map(product -> new ProductNameResponse(product.getId(), product.getName()))
                .toList();
    }

    @Override
    public PageResponse<List<ProductFakeResponse>> getAllProducts(int pageNo, int pageSize) {

        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "id", SortType.DESC.getValue());
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductFakeResponse> responseList = productMapper.toDtoList(productPage.getContent());

        return PageResponse.<List<ProductFakeResponse>>builder()
                .page(productPage.getNumber())
                .size(productPage.getSize())
                .totalPage(productPage.getTotalPages())
                .items(responseList)
                .build();
    }

}
