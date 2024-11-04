package com.example.back_end.service.product;

import com.example.back_end.core.admin.product.payload.request.ProductRequest;
import com.example.back_end.core.admin.product.payload.request.ProductRequestUpdate;
import com.example.back_end.core.admin.product.payload.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    void createProduct(List<ProductRequest> requests, MultipartFile[] images);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);

    List<ProductResponse> getAllProductByParentId(Long parentId);

    ProductResponse getProductDetail(Long id);

   void updateProduct(ProductRequestUpdate request, Long productId);

   List<ProductResponse> getAllProductDetails();

}
