package com.example.back_end.core.admin.picture.service;

import com.example.back_end.core.admin.picture.payload.request.PictureRequest;
import com.example.back_end.core.admin.picture.payload.response.PictureResponse;

import java.util.List;

public interface PictureService {

    List<Long> savePicture(PictureRequest request);

    PictureResponse getPictureById(Long id);

}
