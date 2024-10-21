package com.example.back_end.core.admin.product.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductAttributeResponse {

    private Long id;

    private String name;

    private String description;


}