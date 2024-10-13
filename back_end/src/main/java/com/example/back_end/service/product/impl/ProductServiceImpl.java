package com.example.back_end.service.product.impl;

import com.example.back_end.core.admin.product.payload.request.ProductRequest;
import com.example.back_end.repository.ProductRepository;
import com.example.back_end.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public void createProduct(List<ProductRequest> requests) {

    }
}
