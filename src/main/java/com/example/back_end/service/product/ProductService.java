package com.example.back_end.service.product;

import com.example.back_end.core.admin.order.payload.ReStockQuanityProductRequest;
import com.example.back_end.core.admin.product.payload.request.ProductFilter;
import com.example.back_end.core.admin.product.payload.request.ProductParentRequest;
import com.example.back_end.core.admin.product.payload.request.ProductRequest;
import com.example.back_end.core.admin.product.payload.request.ProductRequestUpdate;
import com.example.back_end.core.admin.product.payload.response.ProductResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    void createProduct(List<ProductRequest> requests, MultipartFile[] images);

    List<ProductResponse> getAllProducts(ProductFilter filter);

    ProductResponse getProductById(Long id);

    List<ProductResponse> getAllProductByParentId(Long parentId);

    ProductResponse getProductDetail(Long id);

    void updateProduct(ProductRequestUpdate request, Long productId) throws BadRequestException;

    List<ProductResponse> getAllProductDetails();

    List<ProductResponse> getAllProductsByParentIds(List<Long> parentIds);

    void updateParentProduct(ProductParentRequest request, Long productId);

    void addChildProduct(ProductRequestUpdate request, Long productId) throws BadRequestException;

    void reStockQuantityProduct(List<ReStockQuanityProductRequest> requests);

}
