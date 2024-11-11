package com.example.back_end.service.product;

import com.example.back_end.core.client.product.payload.reponse.ProductResponse;

import java.util.List;

public interface ProductClientService {

    List<ProductResponse> getRootProducts();

    List<ProductResponse> getRootProductsByCategorySlug(String categorySlug);

}
