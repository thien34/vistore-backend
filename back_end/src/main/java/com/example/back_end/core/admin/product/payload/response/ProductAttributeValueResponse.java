package com.example.back_end.core.admin.product.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ProductAttributeValueResponse {

    private Long id;

    private String name;

    private String colorSquaresRgb;

    private BigDecimal priceAdjustment;

    private Boolean priceAdjustmentPercentage;

    private BigDecimal weightAdjustment;

    private BigDecimal cost;

    private Boolean isPreSelected;

    private Integer displayOrder;

}
