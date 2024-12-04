package com.example.back_end.core.admin.picture.controller;

import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.picture.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/picture")
public class PictureController {

    private final PictureService pictureService;

    @PostMapping("/upload-image")
    public ResponseData<String> uploadImage(@RequestParam("image") MultipartFile image) {

        String path = pictureService.savePicture(image);

        return ResponseData.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Tải ảnh lên thành công!")
                .data(path)
                .build();
    }

}
