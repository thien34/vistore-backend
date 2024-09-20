package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.ProductTagRequest;
import com.example.back_end.core.admin.product.payload.request.ProductTagUpdateRequest;
import com.example.back_end.core.admin.product.payload.response.ProductTagResponse;
import com.example.back_end.core.admin.product.service.ProductTagService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/product-tags")
public class ProductTagController {

    private final ProductTagService productTagService;

    @Operation(method = "GET", summary = "Get all product tags",
            description = "Send a request via this API to get all product tags")
    @GetMapping
    public ResponseData<PageResponse<List<ProductTagResponse>>> getAll(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {

        PageResponse<List<ProductTagResponse>> response = productTagService.getAll(name, pageNo, pageSize);

        return ResponseData.<PageResponse<List<ProductTagResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Get product tags success")
                .data(response)
                .build();
    }

    @Operation(method = "POST", summary = "Add new product tag",
            description = "Send a request via this API to create new product tag")
    @PostMapping
    public ResponseData<Void> create(@Valid @RequestBody ProductTagRequest request) {

        productTagService.createProductTag(request);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Add product tag success")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<Void> updateProductTag(@PathVariable Long id, @RequestBody @Valid ProductTagUpdateRequest request) {

        productTagService.updateProductTag(id, request);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Update product tag success")
                .build();
    }

    @Operation(method = "DELETE", summary = "Delete product tags",
            description = "Send a request via this API to delete product tags")
    @DeleteMapping
    public ResponseData<Void> delete(@RequestBody List<Long> ids) {

        productTagService.delete(ids);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Delete product tags success")
                .build();
    }

}