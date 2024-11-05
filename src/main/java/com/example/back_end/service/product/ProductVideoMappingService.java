package com.example.back_end.service.product;

import com.example.back_end.core.admin.product.payload.request.ProductVideoMappingRequest;
import com.example.back_end.core.admin.product.payload.request.ProductVideoMappingUpdateRequest;
import com.example.back_end.core.admin.product.payload.response.ProductVideoMappingResponse;
import com.example.back_end.core.common.PageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductVideoMappingService {
    ProductVideoMappingResponse createMapping(ProductVideoMappingRequest dto, MultipartFile file);

    PageResponse<List<ProductVideoMappingResponse>> getMappingsByProductId(Long productId, int pageNo, int pageSize);

    ProductVideoMappingResponse updateMapping(Long id, ProductVideoMappingUpdateRequest dto, MultipartFile file);

    void deleteMapping(Long id);
}
