package com.example.back_end.core.admin.productTag.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductTagsResponse {

    private Long id;

    private String name;

    private Long taggedProducts;

}
