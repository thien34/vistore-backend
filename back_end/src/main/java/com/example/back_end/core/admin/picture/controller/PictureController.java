package com.example.back_end.core.admin.picture.controller;

import com.example.back_end.core.admin.picture.payload.response.PictureResponse;
import com.example.back_end.core.admin.picture.service.PictureService;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.core.common.ResponseError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/admin/picture")
@Slf4j
@RequiredArgsConstructor
public class PictureController {

    private final PictureService pictureService;

    @PostMapping
    public ResponseData<?> uploadImage(@RequestParam("images") List<MultipartFile> images) {
        log.info("Request add picture, {}", images);
        try {
            List<Long> ids = pictureService.savePicture(images);
            return new ResponseData<>(HttpStatus.OK.value(), "Upload image success!", ids);
        } catch (Exception e) {
            log.error("An error occurred during file upload", e);
            return new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseData<?> getPictureById(@PathVariable Long id) {
        try {
            PictureResponse picture = pictureService.getPictureById(id);
            return new ResponseData<>(HttpStatus.OK.value(), "Get picture success", picture);
        } catch (Exception e) {
            log.error("Error getting picture", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}
