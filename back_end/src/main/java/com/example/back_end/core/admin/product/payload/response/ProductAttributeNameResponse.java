package com.example.back_end.core.admin.product.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductAttributeNameResponse {

    private Long id;

    private String name;

}
