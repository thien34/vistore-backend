package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.response.ProductPictureMappingResponse;
import com.example.back_end.core.admin.product.service.ProductPictureMappingService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/product-picture-mapping")
public class ProductPictureMappingController {
    private final ProductPictureMappingService productPictureMappingService;

    @GetMapping
    public ResponseData<PageResponse<List<ProductPictureMappingResponse>>> getMappingsByProductId(
            @RequestParam Long productId,
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {

        PageResponse<List<ProductPictureMappingResponse>> response =
                productPictureMappingService.getPictureByProductId(productId, pageNo, pageSize);

        return ResponseData.<PageResponse<List<ProductPictureMappingResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Get product picture mappings by product ID success")
                .data(response)
                .build();
    }

    @PostMapping
    public ResponseData<List<ProductPictureMappingResponse>> createPictureMappings(
            @RequestParam Long productId,
            @RequestParam List<MultipartFile> files) {

        List<ProductPictureMappingResponse> response = productPictureMappingService.createMappings(productId, files);

        return ResponseData.<List<ProductPictureMappingResponse>>builder()
                .status(HttpStatus.CREATED.value())
                .message("Mappings created successfully")
                .data(response)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<ProductPictureMappingResponse> updatePictureMapping(
            @PathVariable Long id,
            @RequestParam Integer displayOrder) {

        ProductPictureMappingResponse response = productPictureMappingService.updatePictureMapping(id, displayOrder);

        return ResponseData.<ProductPictureMappingResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Product picture mapping updated successfully")
                .data(response)
                .build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePictureMapping(@PathVariable Long id) {
        productPictureMappingService.deletePictureMapping(id);
        return ResponseEntity.noContent().build();
    }
}
