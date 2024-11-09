package com.example.back_end.service.picture;

import com.example.back_end.core.admin.picture.payload.response.PictureReturnProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PictureReturnProductService {

    List<Long> savePicture(List<MultipartFile> images, long returnItemId);

    PictureReturnProductResponse getPictureById(Long id);

    List<PictureReturnProductResponse> getAllPictureByReturnItem(Long id);

    String savePicture(MultipartFile images, long returnItemId);
}
