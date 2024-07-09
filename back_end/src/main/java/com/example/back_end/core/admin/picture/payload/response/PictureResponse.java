package com.example.back_end.core.admin.picture.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PictureResponse {

        private Long id;

        private String mimeType;

        private String linkImg;
}
