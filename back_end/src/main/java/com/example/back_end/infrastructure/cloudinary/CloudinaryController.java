package com.example.back_end.infrastructure.cloudinary;

import com.example.back_end.core.common.ResponseData;
import com.example.back_end.core.common.ResponseError;
import com.example.back_end.infrastructure.constant.CloudinaryTypeFolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/picture/cloudinary")
@Slf4j
@RequiredArgsConstructor
public class CloudinaryController {

    private final CloudinaryUpload cloudinaryUpload;

    @PostMapping
    public ResponseData<?> uploadImage(@RequestParam("image") MultipartFile file, @RequestParam("folderName") CloudinaryTypeFolder folderName) {
        try {
            String url = cloudinaryUpload.uploadFile(file, folderName);
            return new ResponseData<>(HttpStatus.OK.value(), "Upload image success!", url);
        } catch (Exception e) {
            log.error("An error occurred during file upload", e);
            return new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }
}
