package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.mapper.ProductVideoMappingMapper;
import com.example.back_end.core.admin.product.payload.request.ProductVideoMappingRequest;
import com.example.back_end.core.admin.product.payload.request.ProductVideoMappingUpdateRequest;
import com.example.back_end.core.admin.product.payload.response.ProductVideoMappingResponse;
import com.example.back_end.core.admin.product.service.ProductVideoMappingService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.Product;
import com.example.back_end.entity.ProductVideoMapping;
import com.example.back_end.entity.Video;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.exception.NotExistsException;
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.ProductRepository;
import com.example.back_end.repository.ProductVideoMappingRepository;
import com.example.back_end.repository.VideoRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductVideoMappingServiceImpl implements ProductVideoMappingService {
    ProductVideoMappingRepository productVideoMappingRepository;
    VideoRepository videoRepository;
    ProductVideoMappingMapper mapper;
    ProductRepository productRepository;

    @Override
    public ProductVideoMappingResponse createMapping(ProductVideoMappingRequest dto) {
        Video video = videoRepository.findByVideoUrl(dto.getVideoUrl())
                .orElseGet(() -> videoRepository.save(Video.builder().videoUrl(dto.getVideoUrl()).build()));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_ID_NOT_FOUND.getMessage()));

        ProductVideoMapping mapping = mapper.toEntity(dto);
        mapping.setVideo(video);
        mapping.setProduct(product);

        ProductVideoMapping savedMapping = productVideoMappingRepository.save(mapping);
        return mapper.toResponseDTO(savedMapping);
    }


    @Override
    public ProductVideoMappingResponse updateMapping(Long id, ProductVideoMappingUpdateRequest dto) {
        ProductVideoMapping mapping = productVideoMappingRepository.findById(id)
                .orElseThrow(() -> new NotExistsException(ErrorCode.PRODUCT_VIDEO_MAPPING_NOT_EXISTS.getMessage()));

        Video video = mapping.getVideo();
        video.setVideoUrl(dto.getVideoUrl());
        videoRepository.save(video);

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new NotExistsException(ErrorCode.PRODUCT_ID_NOT_FOUND.getMessage()));
        mapping.setProduct(product);

        mapping.setDisplayOrder(dto.getDisplayOrder());
        return mapper.toResponseDTO(productVideoMappingRepository.save(mapping));
    }


    @Override
    public void deleteMapping(Long id) {

        if (!productVideoMappingRepository.existsById(id))
            throw new NotExistsException(ErrorCode.PRODUCT_VIDEO_MAPPING_NOT_EXISTS.getMessage());

        productVideoMappingRepository.deleteById(id);
    }

    @Override
    public PageResponse<List<ProductVideoMappingResponse>> getMappingsByProductId(Long productId, int pageNo, int pageSize) {
        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "id", "desc");
        Page<ProductVideoMapping> page = productVideoMappingRepository.findByProductId(productId, pageable);

        List<ProductVideoMappingResponse> mappings = page.stream()
                .map(mapper::toResponseDTO)
                .toList();

        return PageResponse.<List<ProductVideoMappingResponse>>builder()
                .page(page.getNumber())
                .size(page.getSize())
                .totalPage(page.getTotalPages())
                .items(mappings)
                .build();
    }
}
