package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.mapper.ProductMapper;
import com.example.back_end.core.admin.product.payload.request.ProductFakeRequest;
import com.example.back_end.core.admin.product.payload.response.ProductFakeResponse;
import com.example.back_end.core.admin.product.service.ProductFakeService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.Product;
import com.example.back_end.infrastructure.constant.SortType;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductFakeServiceImpl implements ProductFakeService {

    ProductRepository productRepository;
    ProductMapper productMapper;

    @Override
    public ProductFakeResponse createProductFake(ProductFakeRequest request) {

        Product product = Product.builder()
                .name(request.getName())
                .build();

        Product savedProduct = productRepository.save(product);

        return ProductFakeResponse.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .build();
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
