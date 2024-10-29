package com.example.back_end.core.admin.product.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAttributeValuePictureRequest {

    private Long id;

    private Long productAttributeValueId;

    private Long pictureId;

}
