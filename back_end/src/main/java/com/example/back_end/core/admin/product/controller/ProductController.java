package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.ProductRequest;
import com.example.back_end.core.admin.product.payload.request.ProductRequestUpdate;
import com.example.back_end.core.admin.product.payload.response.ProductResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.product.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class ProductController {

    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseData<Void> add(@RequestParam("products") String productsJson,
                                  @RequestParam(value = "images", required = false) MultipartFile[] images) throws IOException {
        List<ProductRequest> requests = objectMapper.readValue(productsJson, objectMapper.getTypeFactory().constructCollectionType(List.class, ProductRequest.class));

        productService.createProduct(requests, images);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Add product success")
                .build();
    }

    @GetMapping
    public ResponseData<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> responses = productService.getAllProducts();

        return ResponseData.<List<ProductResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Get all products  successfully")
                .data(responses)
                .build();
    }


    @GetMapping("/{id}")
    public ResponseData<ProductResponse> getProductById(@PathVariable(value = "id") Long id) {
        ProductResponse productResponse = productService.getProductById(id);

        return ResponseData.<ProductResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Get product successfully")
                .data(productResponse)
                .build();
    }

    @GetMapping("/parent/{id}")
    public ResponseData<List<ProductResponse>> getProductByParentId(@PathVariable(value = "id") Long id) {
        List<ProductResponse> productResponses = productService.getAllProductByParentId(id);

        return ResponseData.<List<ProductResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Get products successfully")
                .data(productResponses)
                .build();
    }


    @GetMapping("/details/{id}")
    public ResponseData<ProductResponse> getProductDetails(@PathVariable(value = "id") Long id) {
        ProductResponse productResponse = productService.getProductDetail(id);

        return ResponseData.<ProductResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Get product details successfully")
                .data(productResponse)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<Void> updateProduct(@RequestBody ProductRequestUpdate requestUpdate, @PathVariable Long id) {

        productService.updateProduct(requestUpdate, id);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Update product success")
                .build();
    }

    @GetMapping("/product-details")
    public ResponseData<List<ProductResponse>> getAllProductDetails() {
        List<ProductResponse> responses = productService.getAllProductDetails();
        return new ResponseData<>(HttpStatus.OK.value(),"Get all products successfully", responses);
    }
}
