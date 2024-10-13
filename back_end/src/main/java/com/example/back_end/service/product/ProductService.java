package com.example.back_end.service.product;

import com.example.back_end.core.admin.product.payload.request.ProductRequest;

import java.util.List;

public interface ProductService {

   void createProduct(List<ProductRequest> requests);

}
