package com.example.back_end.core.admin.product.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProductAttributeCombinationResponse {

    private Long id;

    private String sku;

    private String gtin;

    private Integer stockQuantity;

    private Boolean allowOutOfStockOrders;

    private String attributesXml;

    private BigDecimal overriddenPrice;

    private Integer minStockQuantity;

    private String pictureUrl;

    private String manufacturerPartNumber;

}
