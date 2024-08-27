package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.ProductVideoMappingRequest;
import com.example.back_end.core.admin.product.payload.request.ProductVideoMappingUpdateRequest;
import com.example.back_end.core.admin.product.payload.response.ProductVideoMappingResponse;
import com.example.back_end.core.admin.product.service.ProductVideoMappingService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/product-video-mappings")
public class ProductVideoMappingController {

    private final ProductVideoMappingService productVideoMappingService;

    @Operation(method = "POST", summary = "Add new product video mapping",
            description = "Send a request via this API to create a new product video mapping")
    @PostMapping
    public ResponseData<ProductVideoMappingResponse> createProductVideoMapping(
            @Valid @RequestBody ProductVideoMappingRequest dto) {

        ProductVideoMappingResponse response = productVideoMappingService.createMapping(dto);

        return ResponseData.<ProductVideoMappingResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Product video mapping created successfully")
                .data(response)
                .build();
    }

    @Operation(method = "PUT", summary = "Update product video mapping",
            description = "Send a request via this API to update product video mapping")
    @PutMapping("/{id}")
    public ResponseData<ProductVideoMappingResponse> updateProductVideoMapping(
            @PathVariable Long id,
            @Valid @RequestBody ProductVideoMappingUpdateRequest dto) {
        ProductVideoMappingResponse response = productVideoMappingService.updateMapping(id, dto);
        return ResponseData.<ProductVideoMappingResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Product video mapping updated successfully")
                .data(response)
                .build();
    }


    @Operation(method = "DELETE", summary = "Delete product video mapping",
            description = "Send a request via this API to delete product video mapping")
    @DeleteMapping("/{id}")
    public ResponseData<Void> deleteProductVideoMapping(@PathVariable Long id) {

        productVideoMappingService.deleteMapping(id);

        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Product video mapping deleted successfully")
                .build();
    }

    @Operation(method = "GET", summary = "Get product video mappings by product ID",
            description = "Send a request via this API to get product video mappings by product ID")
    @GetMapping
    public ResponseData<PageResponse<List<ProductVideoMappingResponse>>> getByProductId(
            @RequestParam Long productId,
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {

        PageResponse<List<ProductVideoMappingResponse>> response =
                productVideoMappingService.getMappingsByProductId(productId, pageNo, pageSize);

        return ResponseData.<PageResponse<List<ProductVideoMappingResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Get product video mappings by product ID success")
                .data(response)
                .build();
    }
}
