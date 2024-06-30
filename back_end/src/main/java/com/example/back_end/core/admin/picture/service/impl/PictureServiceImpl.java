package com.example.back_end.core.admin.picture.service.impl;

import com.example.back_end.core.admin.picture.mapper.PictureMapper;
import com.example.back_end.core.admin.picture.payload.request.PictureRequest;
import com.example.back_end.core.admin.picture.payload.response.PictureResponse;
import com.example.back_end.core.admin.picture.service.PictureService;
import com.example.back_end.entity.Picture;
import com.example.back_end.infrastructure.cloudinary.CloudinaryUpload;
import com.example.back_end.infrastructure.constant.CloudinaryTypeFolder;
import com.example.back_end.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final CloudinaryUpload cloudinaryUpload;
    private final PictureMapper pictureMapper;

    @Override
    public Long savePicture(PictureRequest request) {
        String url = cloudinaryUpload.uploadFile(request.getImage(), CloudinaryTypeFolder.PRODUCTS);

        Picture picture = Picture.builder()
                .linkImg(url)
                .mimeType(request.getImage().getContentType())
                .build();

        return pictureRepository.save(picture).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public PictureResponse getPictureById(Long id) {
        Picture picture = pictureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Picture not found with id: " + id));

        return pictureMapper.toDto(picture);
    }
}
