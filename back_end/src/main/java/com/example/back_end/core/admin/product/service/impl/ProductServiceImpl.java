package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.payload.response.ProductNameResponse;
import com.example.back_end.core.admin.product.service.ProductService;
import com.example.back_end.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Override
    public List<ProductNameResponse> getAllProductsName() {
        return productRepository.findAll().stream().map(product -> new ProductNameResponse(product.getId(),product.getName())).collect(Collectors.toList());
    }
}
