package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.ProductAttributeRequest;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeNameResponse;
import com.example.back_end.core.admin.product.payload.response.ProductAttributeResponse;
import com.example.back_end.core.admin.product.service.ProductAttributeService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.entity.ProductAttribute;
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
@RequestMapping("/admin/product-attributes")
public class ProductAttributeController {

    private final ProductAttributeService productAttributeService;

    @GetMapping
    public ResponseData<PageResponse<List<ProductAttributeResponse>>> getAll(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {

        PageResponse<List<ProductAttributeResponse>> response = productAttributeService
                .getAllProductAttribute(name, pageNo, pageSize);

        return ResponseData.<PageResponse<List<ProductAttributeResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Get all product attribute successfully")
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseData<ProductAttributeResponse> getProductAttributeById(@PathVariable Long id) {

        ProductAttributeResponse attribute = productAttributeService.getProductAttributeById(id);

        return ResponseData.<ProductAttributeResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Get product attribute successfully ")
                .data(attribute)
                .build();
    }

    @PostMapping
    public ResponseData<ProductAttribute> createProductAttribute(@Valid @RequestBody ProductAttributeRequest dto) {

        return ResponseData.<ProductAttribute>builder()
                .status(HttpStatus.CREATED.value())
                .message("Product attribute created successfully")
                .data(productAttributeService.createProductAttribute(dto))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<ProductAttributeResponse> updateProductAttribute(@PathVariable Long id, @Valid @RequestBody ProductAttributeRequest dto) {

        ProductAttributeResponse updatedAttribute = productAttributeService.updateProductAttribute(id, dto);

        return ResponseData.<ProductAttributeResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Product attribute updated successfully")
                .data(updatedAttribute)
                .build();

    }

    @DeleteMapping
    public ResponseData<Void> deleteProductAttributes(@RequestBody List<Long> ids) {

        productAttributeService.deleteProductAttribute(ids);

        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Delete product attributes success")
                .build();
    }

    @GetMapping("/list-name")
    public ResponseData<List<ProductAttributeNameResponse>> getAllNameProductAttributes() {

        List<ProductAttributeNameResponse> names = productAttributeService.getAttributeName();

        return ResponseData.<List<ProductAttributeNameResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Get all product attribute names successfully")
                .data(names)
                .build();
    }

}