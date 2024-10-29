package com.example.back_end.service.product.impl;

import com.example.back_end.core.admin.product.mapper.ProductPictureMappingMapper;
import com.example.back_end.core.admin.product.payload.response.ProductPictureMappingResponse;
import com.example.back_end.service.product.ProductPictureMappingService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.Picture;
import com.example.back_end.entity.Product;
import com.example.back_end.entity.ProductPictureMapping;
import com.example.back_end.infrastructure.cloudinary.CloudinaryUpload;
import com.example.back_end.infrastructure.constant.CloudinaryTypeFolder;
import com.example.back_end.infrastructure.constant.SortType;
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.PictureRepository;
import com.example.back_end.repository.ProductPictureMappingRepository;
import com.example.back_end.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductPictureMappingServiceImpl implements ProductPictureMappingService {
    private final ProductPictureMappingRepository productPictureMappingRepository;
    private final ProductPictureMappingMapper mapper;
    private final CloudinaryUpload cloudinaryUpload;
    private final PictureRepository pictureRepository;
    private final ProductRepository productRepository;

    @Override
    public PageResponse<List<ProductPictureMappingResponse>> getPictureByProductId(Long productId, int pageNo, int pageSize) {
        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "id", SortType.DESC.getValue());
        Page<ProductPictureMapping> page = productPictureMappingRepository.findByProductId(productId, pageable);

        List<ProductPictureMappingResponse> mappings = page.stream()
                .map(mapper::toDto)
                .toList();

        return PageResponse.<List<ProductPictureMappingResponse>>builder()
                .page(page.getNumber() + 1)
                .size(page.getSize())
                .totalPage(page.getTotalPages())
                .items(mappings)
                .build();
    }

    @Override
    @Transactional
    public List<ProductPictureMappingResponse> createMappings(Long productId, List<MultipartFile> files) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        List<ProductPictureMappingResponse> responses = new ArrayList<>();

        for (MultipartFile file : files) {
            String pictureUrl = cloudinaryUpload.uploadFile(file, CloudinaryTypeFolder.PRODUCTS);
            Picture picture = Picture.builder()
                    .linkImg(pictureUrl)
                    .mimeType(file.getContentType())
                    .build();

            picture = pictureRepository.save(picture);

            ProductPictureMapping mapping = ProductPictureMapping.builder()
                    .product(product)
                    .picture(picture)
                    .displayOrder(0)
                    .build();

            ProductPictureMapping savedMapping = productPictureMappingRepository.save(mapping);
            responses.add(mapper.toDto(savedMapping));
        }

        return responses;
    }

    @Override
    @Transactional
    public ProductPictureMappingResponse updatePictureMapping(Long id, Integer displayOrder) {
        ProductPictureMapping mapping = productPictureMappingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Picture mapping not found with id: " + id));

        mapping.setDisplayOrder(displayOrder);
        return mapper.toDto(productPictureMappingRepository.save(mapping));
    }
    @Override
    public void deletePictureMapping(Long id) {
        ProductPictureMapping mapping = productPictureMappingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Picture mapping not found with id: " + id));

        productPictureMappingRepository.delete(mapping);
    }
}
