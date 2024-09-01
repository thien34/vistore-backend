package com.example.back_end.core.admin.product.payload.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProductResponse {

    private Long id;
    private String name;
    private String sku;
    private BigDecimal unitPrice;
    private Integer minStockQuantity;
    private Boolean published;
    private String imageUrl;
}
