package com.example.back_end.core.admin.product.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class ProductTagDtoResponse {

    private Long id;

    private String name;

    private Long productId;

}

