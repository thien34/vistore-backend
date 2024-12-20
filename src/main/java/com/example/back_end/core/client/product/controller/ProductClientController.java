package com.example.back_end.core.client.product.controller;

import com.example.back_end.core.client.product.payload.reponse.ProductDetailResponse;
import com.example.back_end.core.client.product.payload.reponse.ProductResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.product.ProductClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client/products")
public class ProductClientController {

    private final ProductClientService productClientService;

    @GetMapping()
    public ResponseData<List<ProductResponse>> getRootProducts() {

        List<ProductResponse> response = productClientService.getRootProducts();

        return ResponseData.<List<ProductResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Đạt được thành công của các danh mục gốc")
                .data(response)
                .build();
    }

    @GetMapping("/best-selling")
    public ResponseData<List<ProductResponse>> getRootProductsBestSelling() {

        List<ProductResponse> response = productClientService.getRootProductsBestSelling();

        return ResponseData.<List<ProductResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận các danh mục gốc theo thành công của sên danh mục")
                .data(response)
                .build();
    }

    @GetMapping("/product-discount")
    public ResponseData<List<ProductResponse>> getRootProductsProductDiscount() {

        List<ProductResponse> response = productClientService.getRootProductsDiscount();

        return ResponseData.<List<ProductResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận các danh mục gốc theo thành công của sên danh mục")
                .data(response)
                .build();
    }

    @GetMapping("/{categorySlug}")
    public ResponseData<List<ProductResponse>> getRootProductsByCategorySlug(@PathVariable String categorySlug) {

        List<ProductResponse> response = productClientService.getRootProductsByCategorySlug(categorySlug);

        return ResponseData.<List<ProductResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận các danh mục gốc theo thành công của sên danh mục")
                .data(response)
                .build();
    }

    @GetMapping("/product/{productSlug}")
    public ResponseData<ProductDetailResponse> getProductBySlug(@PathVariable String productSlug) {

        ProductDetailResponse response = productClientService.getProductBySlug(productSlug);

        return ResponseData.<ProductDetailResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy sản phẩm thành công bằng ảnh nhỏ")
                .data(response)
                .build();
    }

}
