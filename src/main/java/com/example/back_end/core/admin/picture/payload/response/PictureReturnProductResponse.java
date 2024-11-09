package com.example.back_end.core.admin.picture.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PictureReturnProductResponse {
    private Long id;

    private Long returnItemId;

    private String mimeType;

    private String linkImg;
}
