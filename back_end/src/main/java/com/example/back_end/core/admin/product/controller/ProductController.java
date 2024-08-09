package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.response.ProductNameResponse;
import com.example.back_end.core.admin.product.service.ProductService;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.core.common.ResponseError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/products")
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping("/listname")
    public ResponseData<?> getAllManufacturersName() {
        try {
            List<ProductNameResponse> response = productService.getAllProductsName();
            return new ResponseData<>(HttpStatus.OK.value(), "Get all Products name successfully", response);
        } catch (Exception e) {
            log.error("Error getting all Products name", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}
