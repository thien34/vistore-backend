package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.response.ProductFakeResponse;
import com.example.back_end.core.admin.product.payload.response.ProductNameResponse;
import com.example.back_end.core.admin.product.service.ProductService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list-name")
    public ResponseData<List<ProductNameResponse>> getAllProductsName() {

        List<ProductNameResponse> response = productService.getAllProductsName();

        return ResponseData.<List<ProductNameResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Get all Products name successfully")
                .data(response)
                .build();
    }

    @GetMapping
    public ResponseData<PageResponse<List<ProductFakeResponse>>> getAll(
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {

        PageResponse<List<ProductFakeResponse>> response = productService.getAllProducts(pageNo, pageSize);

        return ResponseData.<PageResponse<List<ProductFakeResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Get product success")
                .data(response)
                .build();
    }

}
