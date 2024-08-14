package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.ProductFakeRequest;
import com.example.back_end.core.admin.product.payload.response.ProductFakeResponse;
import com.example.back_end.core.admin.product.service.ProductFakeService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class ProductFakeController {

    private final ProductFakeService productFakeService;

    @Operation(summary = "Create a new product",
            description = "Create a new product with the given details")
    @PostMapping
    public ResponseData<ProductFakeResponse> createProductFake(@RequestBody ProductFakeRequest request) {
        ProductFakeResponse response = productFakeService.createProductFake(request);
        return ResponseData.<ProductFakeResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Product created successfully")
                .data(response)
                .build();
    }

    @Operation(method = "GET", summary = "Get all products ",
            description = "Send a request via this API to get all product ")
    @GetMapping
    public ResponseData<PageResponse<List<ProductFakeResponse>>> getAll(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {

        PageResponse<List<ProductFakeResponse>> response = productFakeService.getAllProducts(pageNo, pageSize);

        return ResponseData.<PageResponse<List<ProductFakeResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Get product success")
                .data(response)
                .build();
    }

}
