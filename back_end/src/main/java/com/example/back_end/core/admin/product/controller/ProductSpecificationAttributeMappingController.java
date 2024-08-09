package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.ProductSpecificationAttributeMappingRequest;
import com.example.back_end.core.admin.product.payload.request.ProductSpecificationAttributeMappingUpdateRequest;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingResponse;
import com.example.back_end.core.admin.product.payload.response.ProductSpecificationAttributeMappingUpdateResponse;
import com.example.back_end.core.admin.product.service.ProductSpecificationAttributeMappingService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.core.common.ResponseError;
import com.example.back_end.infrastructure.constant.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/product-specification-attribute-mappings")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductSpecificationAttributeMappingController {
    ProductSpecificationAttributeMappingService productSpecificationAttributeMappingService;

    @Operation(method = "POST", summary = "Add new product specification attribute mapping",
            description = "Send a request via this API to create new product specification attribute mapping")
    @PostMapping
    public ResponseData<ProductSpecificationAttributeMappingResponse> createProductSpecificationAttributeMapping(
            @Valid @RequestBody ProductSpecificationAttributeMappingRequest dto) {
        try {
            ProductSpecificationAttributeMappingResponse response =
                    productSpecificationAttributeMappingService.createProductSpecificationAttributeMapping(dto);
            return ResponseData.<ProductSpecificationAttributeMappingResponse>builder()
                    .status(HttpStatus.OK.value())
                    .message(SuccessCode.PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_CREATED.getMessage())
                    .data(response)
                    .build();
        } catch (Exception e) {
            log.error("Error creating product specification attribute mapping", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @Operation(method = "GET", summary = "Get all product specification attribute mappings",
            description = "Send a request via this API to get all product specification attribute mappings")
    @GetMapping
    public ResponseData<?> getAll(@RequestParam(value = "name", defaultValue = "") String name,
                                  @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {
        try {
            PageResponse<?> response = productSpecificationAttributeMappingService.getAllProductSpecificationAttributeMapping(name, pageNo, pageSize);
            return new ResponseData<>(HttpStatus.OK.value(), "Get product specification attribute mappings success", response);
        } catch (Exception e) {
            log.error("Error getting product specification attribute mappings", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @Operation(method = "GET", summary = "Get product specification attribute mapping by ID",
            description = "Send a request via this API to get product specification attribute mapping by ID")
    @GetMapping("/{id}")
    public ResponseData<ProductSpecificationAttributeMappingResponse> getById(@PathVariable Long id) {
        try {
            ProductSpecificationAttributeMappingResponse response = productSpecificationAttributeMappingService.getProductSpecificationAttributeMappingById(id);
            return new ResponseData<>(HttpStatus.OK.value(), "Get product specification attribute mapping by ID success", response);
        } catch (Exception e) {
            log.error("Error getting product specification attribute mapping by ID", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    @Operation(method = "DELETE", summary = "Delete product specification attribute mappings",
            description = "Send a request via this API to delete product specification attribute mappings")
    @DeleteMapping
    public ResponseData<?> deleteProductSpecificationAttributeMappings(@RequestBody List<Long> ids) {
        log.info("Request to delete product specification attribute mappings with ids: {}", ids);
        try {
            productSpecificationAttributeMappingService.deleteProductSpecificationAttribute(ids);
            return new ResponseData<>(HttpStatus.OK.value(), "Delete product specification attribute mappings success");
        } catch (Exception e) {
            log.error("Error deleting product specification attribute mappings", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    @Operation(method = "GET", summary = "Get product specification attribute mappings by product ID",
            description = "Send a request via this API to get product specification attribute mappings by product ID")
    @GetMapping("/by-product/{productId}")
    public ResponseData<?> getByProductId(
            @PathVariable Long productId,
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {
        try {
            PageResponse<?> response =
                    productSpecificationAttributeMappingService.getProductSpecificationAttributeMappingsByProductId(productId, pageNo, pageSize);
            return ResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get product specification attribute mappings by product ID success")
                    .data(response)
                    .build();
        } catch (Exception e) {
            log.error("Error getting product specification attribute mappings by product ID", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    @Operation(method = "PUT", summary = "Update product specification attribute mapping",
            description = "Send a request via this API to update product specification attribute mapping")
    @PutMapping("/{id}")
    public ResponseData<ProductSpecificationAttributeMappingUpdateResponse> updateProductSpecificationAttributeMapping(
            @PathVariable Long id,
            @Valid @RequestBody ProductSpecificationAttributeMappingUpdateRequest dto) {
        try {
            ProductSpecificationAttributeMappingUpdateResponse response =
                    productSpecificationAttributeMappingService.updateProductSpecificationAttributeMapping(id, dto);
            return ResponseData.<ProductSpecificationAttributeMappingUpdateResponse>builder()
                    .status(HttpStatus.OK.value())
                    .message(SuccessCode.PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_UPDATED.getMessage())
                    .data(response)
                    .build();
        } catch (Exception e) {
            log.error("Error updating product specification attribute mapping", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseData<Void> deleteProductSpecificationAttributeMapping(@PathVariable Long id) {
        try {
            productSpecificationAttributeMappingService.deleteProductSpecificationAttributeMappingById(id);

            return ResponseData.<Void>builder()
                    .status(SuccessCode.PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_DELETED.getStatusCode().value())
                    .message(SuccessCode.PRODUCT_SPECIFICATION_ATTRIBUTE_MAPPING_DELETED.getMessage())
                    .build();
        } catch (Exception e) {
            log.error("Error delete delete product specification attribute mapping", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

}
