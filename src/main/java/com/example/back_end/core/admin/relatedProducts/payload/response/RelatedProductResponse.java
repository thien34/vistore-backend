package com.example.back_end.core.admin.relatedProducts.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatedProductResponse {

    private Long id;

    private Long product1Id;

    private Long product2Id;

    private String nameProduct2;

    private Integer displayOrder;

}
