package com.example.back_end.service.product;

import com.example.back_end.core.admin.product.payload.response.ProductPictureMappingResponse;
import com.example.back_end.core.common.PageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductPictureMappingService {
    PageResponse<List<ProductPictureMappingResponse>> getPictureByProductId(Long productId, int pageNo, int pageSize);
    List<ProductPictureMappingResponse> createMappings(Long productId, List<MultipartFile> files);
    ProductPictureMappingResponse updatePictureMapping(Long id, Integer displayOrder);
    void deletePictureMapping(Long id);
}
