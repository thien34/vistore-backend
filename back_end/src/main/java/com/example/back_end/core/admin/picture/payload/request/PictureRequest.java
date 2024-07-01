package com.example.back_end.core.admin.picture.payload.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PictureRequest {

    private List<MultipartFile> images = new ArrayList<>();

}
