package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.ProductProductAttributeMappingRequest;
import com.example.back_end.core.admin.product.payload.response.ProductProductAttributeMappingDetailResponse;
import com.example.back_end.core.admin.product.payload.response.ProductProductAttributeMappingResponse;
import com.example.back_end.service.product.ProductProductAttributeMappingService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
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
@RequestMapping("/admin/product-attribute-mapping")
public class ProductAttributeMappingController {

    private final ProductProductAttributeMappingService productAttributeService;

    @PostMapping()
    public ResponseData<Void> addProductProductAttributeMapping(
            @RequestBody ProductProductAttributeMappingRequest request) {

        productAttributeService.addProductProductAttributeMapping(request);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Product product attribute mapping added successfully")
                .build();
    }

    @GetMapping()
    public ResponseData<PageResponse<List<ProductProductAttributeMappingResponse>>> getProductProductAttributeMappings(
            @RequestParam Long productId,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {

        PageResponse<List<ProductProductAttributeMappingResponse>> response = productAttributeService
                .getProductProductAttributeMappings(productId, pageNo, pageSize);

        return ResponseData.<PageResponse<List<ProductProductAttributeMappingResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Get product product attribute mappings success")
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseData<ProductProductAttributeMappingDetailResponse> getProductProductAttributeMapping(@PathVariable Long id) {

        ProductProductAttributeMappingDetailResponse response = productAttributeService
                .getProductProductAttributeMapping(id);

        return ResponseData.<ProductProductAttributeMappingDetailResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Get product product attribute mapping success")
                .data(response)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<Void> updateProductProductAttributeMapping(
            @PathVariable Long id,
            @RequestBody ProductProductAttributeMappingRequest request) {

        productAttributeService.updateProductProductAttributeMapping(id, request);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Product product attribute mapping updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseData<Void> deleteProductProductAttributeMapping(@PathVariable Long id) {

        productAttributeService.deleteProductProductAttributeMapping(id);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Product product attribute mapping deleted successfully")
                .build();
    }

    @GetMapping("/product/{id}")
    public ResponseData<List<ProductProductAttributeMappingDetailResponse>> findProductProductAttributeMapping(@PathVariable Long id) {

        List<ProductProductAttributeMappingDetailResponse> responses = productAttributeService
                .getProductProductAttributeMappingByproductId(id);

        return ResponseData.<List<ProductProductAttributeMappingDetailResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Find product product attribute mapping success")
                .data(responses)
                .build();
    }

}
