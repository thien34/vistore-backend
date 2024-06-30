package com.example.back_end.core.admin.picture.payload.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PictureRequest {

    private MultipartFile image;

}
