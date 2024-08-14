package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.ProductAttributeCombinationRequest;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeCombinationResponse;
import com.example.back_end.core.admin.product.service.ProductAttributeCombinationService;
import com.example.back_end.core.common.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/product-attribute-combinations")
public class ProductAttributeCombinationController {

    private final ProductAttributeCombinationService productAttributeCombinationService;

    @PostMapping
    public ResponseData<Void> create(@RequestBody ProductAttributeCombinationRequest request) {

        productAttributeCombinationService.saveOrUpdateProductAttributeCombination(request);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Add product combination success")
                .build();
    }

    @GetMapping("/product/{productId}")
    public ResponseData<List<ProductAttributeCombinationResponse>> findByProductId(
            @PathVariable("productId") Long productId) {

        return ResponseData.<List<ProductAttributeCombinationResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Fetch product combinations successfully")
                .data(productAttributeCombinationService.getByProductId(productId))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseData<Void> delete(@PathVariable Long id) {

        productAttributeCombinationService.delete(id);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Delete product combination success")
                .build();
    }

}
