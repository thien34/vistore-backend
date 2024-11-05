package com.example.back_end.core.admin.product.payload.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProductFilter {

    private String name;

    private Long categoryId;

    private Boolean searchSubCategory;

    private Long manufacturerId;

    private Boolean published;

    private String sku;

}
