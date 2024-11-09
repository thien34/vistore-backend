package com.example.back_end.service.picture.impl;

import com.example.back_end.core.admin.picture.mapper.PictureReturnProductMapper;
import com.example.back_end.core.admin.picture.payload.response.PictureReturnProductResponse;
import com.example.back_end.entity.PictureReturnProduct;
import com.example.back_end.infrastructure.cloudinary.CloudinaryUpload;
import com.example.back_end.infrastructure.constant.CloudinaryTypeFolder;
import com.example.back_end.repository.PictureReturnProductRepository;
import com.example.back_end.repository.ReturnItemRepository;
import com.example.back_end.service.picture.PictureReturnProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PictureReturnProductServiceImpl implements PictureReturnProductService {
    private final PictureReturnProductRepository pictureRepository;
    private final CloudinaryUpload cloudinaryUpload;
    private final PictureReturnProductMapper pictureMapper;
    private final ReturnItemRepository returnItemRepository;

    @Override
    public List<Long> savePicture(List<MultipartFile> images, long returnItemId) {
        return images.stream().map(image -> {
            String url = cloudinaryUpload.uploadFile(image, CloudinaryTypeFolder.RETURNPRODUCTS);

            PictureReturnProduct picture = PictureReturnProduct.builder()
                    .linkImg(url)
                    .returnItem(returnItemRepository.getById(returnItemId))
                    .mimeType(image.getContentType())
                    .build();
            return pictureRepository.save(picture).getId();
        }).toList();
    }

    @Override
    public PictureReturnProductResponse getPictureById(Long id) {
        PictureReturnProduct picture = pictureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Picture Return Product not found with id: " + id));

        return pictureMapper.toDto(picture);
    }

    @Override
    public List<PictureReturnProductResponse> getAllPictureByReturnItem(Long id) {
        return pictureMapper.toDtos(pictureRepository.findByReturnItemId(id));
    }

    @Override
    public String savePicture(MultipartFile images, long returnItemId) {
        String url = cloudinaryUpload.uploadFile(images, CloudinaryTypeFolder.RETURNPRODUCTS);
        PictureReturnProduct picture = PictureReturnProduct.builder()
                .linkImg(url)
                .returnItem(returnItemRepository.getById(returnItemId))
                .mimeType(images.getContentType())
                .build();
        return pictureRepository.save(picture).getLinkImg();

    }
}
