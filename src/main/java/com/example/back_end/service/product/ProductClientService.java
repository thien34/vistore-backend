package com.example.back_end.service.product;

import com.example.back_end.core.client.product.payload.reponse.ProductDetailResponse;
import com.example.back_end.core.client.product.payload.reponse.ProductResponse;

import java.util.List;

public interface ProductClientService {

    List<ProductResponse> getRootProducts();

    List<ProductResponse> getRootProductsBestSelling();

    List<ProductResponse> getRootProductsDiscount();

    List<ProductResponse> getRootProductsByCategorySlug(String categorySlug);

    ProductDetailResponse getProductBySlug(String productSlug);
}
