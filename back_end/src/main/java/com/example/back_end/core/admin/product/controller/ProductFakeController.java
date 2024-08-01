package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.ProductFakeRequest;
import com.example.back_end.core.admin.product.payload.response.ProductFakeResponse;
import com.example.back_end.core.admin.product.service.ProductFakeService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.core.common.ResponseError;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductFakeController {
    ProductFakeService productFakeService;


    @Operation(summary = "Create a new product",
            description = "Create a new product with the given details")
    @PostMapping
    public ResponseData<ProductFakeResponse> createProductFake(@RequestBody ProductFakeRequest request) {
        log.info("Request to create fake product: {}", request);
        try {
            ProductFakeResponse response = productFakeService.createProductFake(request);
            return ResponseData.<ProductFakeResponse>builder()
                    .status(HttpStatus.CREATED.value())
                    .message("Product created successfully")
                    .data(response)
                    .build();
        } catch (Exception e) {
            log.error("Error creating product", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @Operation(method = "GET", summary = "Get all products ",
            description = "Send a request via this API to get all product ")
    @GetMapping
    public ResponseData<?> getAll(@RequestParam(value = "name", defaultValue = "") String name,
                                  @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {
        try {
            PageResponse<?> response = productFakeService.getAllProducts( pageNo, pageSize);
            return new ResponseData<>(HttpStatus.OK.value(), "Get product success", response);
        } catch (Exception e) {
            log.error("Error getting product ", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

}
