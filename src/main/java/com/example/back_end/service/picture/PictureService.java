package com.example.back_end.service.picture;

import org.springframework.web.multipart.MultipartFile;

public interface PictureService {

    String savePicture(MultipartFile images);

}
