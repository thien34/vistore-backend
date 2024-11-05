package com.example.back_end.core.admin.productTag.payload.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductTagResponse {

    private Long id;

    private String name;

    private Long productId;

}

