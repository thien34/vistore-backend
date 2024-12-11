package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.order.payload.ReStockQuanityProductRequest;
import com.example.back_end.core.admin.product.payload.request.ProductFilter;
import com.example.back_end.core.admin.product.payload.request.ProductParentRequest;
import com.example.back_end.core.admin.product.payload.request.ProductRequest;
import com.example.back_end.core.admin.product.payload.request.ProductRequestUpdate;
import com.example.back_end.core.admin.product.payload.response.ProductResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.product.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
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
                                  @RequestParam(value = "images", required = false)
                                  MultipartFile[] images) throws IOException {
        List<ProductRequest> requests = objectMapper
                .readValue(productsJson, objectMapper.getTypeFactory()
                        .constructCollectionType(List.class, ProductRequest.class));

        productService.createProduct(requests, images);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Thêm sản phẩm thành công")
                .build();
    }

    @GetMapping
    public ResponseData<List<ProductResponse>> getAllProducts(ProductFilter filter) {
        List<ProductResponse> responses = productService.getAllProducts(filter);
        return ResponseData.<List<ProductResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận tất cả các sản phẩm thành công")
                .data(responses)
                .build();
    }


    @GetMapping("/{id}")
    public ResponseData<ProductResponse> getProductById(@PathVariable(value = "id") Long id) {
        ProductResponse productResponse = productService.getProductById(id);

        return ResponseData.<ProductResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận sản phẩm thành công")
                .data(productResponse)
                .build();
    }

    @PostMapping("/restock-product")
    public ResponseData<Void> saveReturnItems(@RequestBody List<ReStockQuanityProductRequest> requests) {
        productService.reStockQuantityProduct(requests);
        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Restock sản phẩm thành công")
                .build();
    }

    @GetMapping("/by-parent-ids")
    public ResponseData<List<ProductResponse>> getProductsByParentIds(@RequestParam List<Long> parentIds) {
        List<ProductResponse> productResponses = productService.getAllProductsByParentIds(parentIds);

        return ResponseData.<List<ProductResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy sản phẩm thành công")
                .data(productResponses)
                .build();
    }

    @GetMapping("/parent/{id}")
    public ResponseData<List<ProductResponse>> getProductByParentId(@PathVariable(value = "id") Long id) {
        List<ProductResponse> productResponses = productService.getAllProductByParentId(id);

        return ResponseData.<List<ProductResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy toàn bộ sản phẩm thành công")
                .data(productResponses)
                .build();
    }


    @GetMapping("/details/{id}")
    public ResponseData<ProductResponse> getProductDetails(@PathVariable(value = "id") Long id) {
        ProductResponse productResponse = productService.getProductDetail(id);

        return ResponseData.<ProductResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy toàn bộ sản phẩm chi tiết thành công")
                .data(productResponse)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<Void> updateProduct(@RequestBody ProductRequestUpdate requestUpdate, @PathVariable Long id) throws BadRequestException {

        productService.updateProduct(requestUpdate, id);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Cập nhật sản phẩm thành công")
                .build();
    }

    @GetMapping("/product-details")
    public ResponseData<List<ProductResponse>> getAllProductDetails() {
        List<ProductResponse> responses = productService.getAllProductDetails();
        return new ResponseData<>(HttpStatus.OK.value(), "Nhận tất cả các sản phẩm thành công", responses);
    }

    @PutMapping("/parent-update/{id}")
    public ResponseData<Void> updateParentProduct(@RequestBody ProductParentRequest requestUpdate, @PathVariable Long id) {
        productService.updateParentProduct(requestUpdate, id);
        return new ResponseData<>(HttpStatus.OK.value(), "Cập nhật sản phẩm thành công");
    }

    @PutMapping("/add-child/{id}")
    public ResponseData<Void> addChildProduct(@RequestBody ProductRequestUpdate requestUpdate, @PathVariable Long id) throws BadRequestException {

        productService.addChildProduct(requestUpdate, id);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Cập nhật thành công sản phẩm")
                .build();
    }
}
