package com.example.back_end.core.admin.picture.controller;

import com.example.back_end.core.admin.picture.payload.response.PictureResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.picture.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/picture")
public class PictureController {

    private final PictureService pictureService;

    @PostMapping
    public ResponseData<List<Long>> uploadImage(@RequestParam("images") List<MultipartFile> images) {

        List<Long> ids = pictureService.savePicture(images);

        return ResponseData.<List<Long>>builder()
                .status(HttpStatus.OK.value())
                .message("Upload image success!")
                .data(ids)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseData<PictureResponse> getPictureById(@PathVariable Long id) {

        PictureResponse picture = pictureService.getPictureById(id);

        return ResponseData.<PictureResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Get picture success")
                .data(picture)
                .build();
    }


    @PostMapping("/upload-image")
    public ResponseData<String> uploadImage(@RequestParam("image") MultipartFile image) {

        String path = pictureService.savePicture(image);

        return ResponseData.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Upload image success!")
                .data(path)
                .build();
    }

}
