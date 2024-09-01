package com.example.back_end.core.admin.product.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPictureMappingRequest {
    private Long productId;

    private Integer displayOrder;
}
