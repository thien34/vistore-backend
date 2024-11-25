package com.example.back_end.core.admin.picture.controller;

import com.example.back_end.core.admin.picture.payload.response.PictureReturnProductResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.picture.PictureReturnProductService;
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
@RequestMapping("/admin/picture-return-product")
public class PictureReturnProductController {

    private final PictureReturnProductService pictureReturnProductService;

    @PostMapping
    public ResponseData<List<Long>> uploadImage(@RequestParam("images") List<MultipartFile> images, @RequestParam("returnItemId") Long returnItemId) {

        List<Long> ids = pictureReturnProductService.savePicture(images, returnItemId);

        return ResponseData.<List<Long>>builder()
                .status(HttpStatus.OK.value())
                .message("Upload image success!")
                .data(ids)
                .build();
    }

    @GetMapping("/{pictureId}")
    public ResponseData<PictureReturnProductResponse> getPictureById(@PathVariable Long pictureId) {

        PictureReturnProductResponse picture = pictureReturnProductService.getPictureById(pictureId);
        return ResponseData.<PictureReturnProductResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Get image success!")
                .data(picture)
                .build();
    }

    @GetMapping("/return-item/{returnItemId}")
    public ResponseData<List<PictureReturnProductResponse>> getPictureByReturnItemId(@PathVariable Long returnItemId) {

        List<PictureReturnProductResponse> picture = pictureReturnProductService.getAllPictureByReturnItem(returnItemId);

        return ResponseData.<List<PictureReturnProductResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Get images of return item id " + returnItemId + " success!")
                .data(picture)
                .build();
    }

}
