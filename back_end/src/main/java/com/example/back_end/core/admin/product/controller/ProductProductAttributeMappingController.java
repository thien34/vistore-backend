package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.ProductProductAttributeMappingRequest;
import com.example.back_end.core.admin.product.payload.response.ProductProductAttributeMappingDetailResponse;
import com.example.back_end.core.admin.product.service.ProductProductAttributeMappingService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.core.common.ResponseError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/product-attribute-mapping")
public class ProductProductAttributeMappingController {

    private final ProductProductAttributeMappingService productAttributeService;

    @PostMapping()
    public ResponseData<?> addProductProductAttributeMapping(@RequestBody ProductProductAttributeMappingRequest request) {
        log.info("Add product product attribute mapping");
        try {
            productAttributeService.addProductProductAttributeMapping(request);
            return ResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .message("Product product attribute mapping added successfully")
                    .build();
        } catch (Exception e) {
            log.error("Error adding product product attribute mapping", e);
            return ResponseError.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
        }
    }

    @GetMapping()
    public ResponseData<?> getProductProductAttributeMappings(
            @RequestParam Long productId,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize) {
        log.info("Get product product attribute mappings");
        try {
            PageResponse<?> response = productAttributeService.getProductProductAttributeMappings(productId, pageNo, pageSize);
            return ResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get product product attribute mappings success")
                    .data(response)
                    .build();
        } catch (Exception e) {
            log.error("Error getting product product attribute mappings", e);
            return ResponseError.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/{id}")
    public ResponseData<?> getProductProductAttributeMapping(@PathVariable Long id) {
        log.info("Get product product attribute mapping");
        try {
            ProductProductAttributeMappingDetailResponse response = productAttributeService.getProductProductAttributeMapping(id);
            return ResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get product product attribute mapping success")
                    .data(response)
                    .build();
        } catch (Exception e) {
            log.error("Error getting product product attribute mapping", e);
            return ResponseError.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
        }
    }

    @PostMapping("/{id}")
    public ResponseData<?> updateProductProductAttributeMapping(@PathVariable Long id, @RequestBody ProductProductAttributeMappingRequest request) {
        log.info("Update product product attribute mapping");
        try {
            productAttributeService.updateProductProductAttributeMapping(id, request);
            return ResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .message("Product product attribute mapping updated successfully")
                    .build();
        } catch (Exception e) {
            log.error("Error updating product product attribute mapping", e);
            return ResponseError.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseData<?> deleteProductProductAttributeMapping(@PathVariable Long id) {
        log.info("Delete product product attribute mapping");
        try {
            productAttributeService.deleteProductProductAttributeMapping(id);
            return ResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .message("Product product attribute mapping deleted successfully")
                    .build();
        } catch (Exception e) {
            log.error("Error deleting product product attribute mapping", e);
            return ResponseError.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/product/{id}")
    public ResponseData<?> findProductProductAttributeMapping(@PathVariable Long id) {
        log.info("Find product product attribute mapping");
        List<ProductProductAttributeMappingDetailResponse> responses = productAttributeService
                .getProductProductAttributeMappingByproductId(id);

        return  ResponseData.builder()
                .data(responses)
                .message("Find product product attribute mapping success")
                .status(HttpStatus.OK.value())
                .build();
    }
}
