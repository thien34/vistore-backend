package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.ProductAttributeValueRequest;
import com.example.back_end.service.product.impl.ProductAttributeValueServiceImpl;
import com.example.back_end.core.common.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/product-attribute-values")
public class ProductAttributeValueController {

    private final ProductAttributeValueServiceImpl productAttributeValueService;

    @PostMapping()
    public ResponseData<Void> createProductAttributeValue(@RequestBody ProductAttributeValueRequest request) {
        productAttributeValueService.createProductAttributeValue(request);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Product attribute value created successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<Void> updateProductAttributeValue(@PathVariable Long id, @RequestBody ProductAttributeValueRequest request) {

        productAttributeValueService.updateProductAttributeValue(id, request);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Product attribute value updated successfully")
                .build();
    }

}
