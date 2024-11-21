package com.example.back_end.service.picture.impl;

import com.example.back_end.infrastructure.cloudinary.CloudinaryUpload;
import com.example.back_end.infrastructure.constant.CloudinaryTypeFolder;
import com.example.back_end.service.picture.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {

    private final CloudinaryUpload cloudinaryUpload;

    //    đang dùng chung cho category, product - sửa lại khi cần
    @Override
    public String savePicture(MultipartFile image) {
        return cloudinaryUpload.uploadFile(image, CloudinaryTypeFolder.PRODUCTS);
    }

}
