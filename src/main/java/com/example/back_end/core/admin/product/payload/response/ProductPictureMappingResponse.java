package com.example.back_end.core.admin.product.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductPictureMappingResponse {
    private Long id;

    private Long productId;

    private String pictureUrl;

    private Integer displayOrder;
}
