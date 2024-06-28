package com.example.back_end.core.admin.picture.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PictureRequest {

    private String mimeType;

    private String linkImg;

    private String seoFileName;

    private Boolean isNew;

    private String virtualPath;

}
