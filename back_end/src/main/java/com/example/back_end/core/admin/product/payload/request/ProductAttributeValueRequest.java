package com.example.back_end.core.admin.product.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductAttributeValueRequest {

    private Long id;

    @NotBlank
    private String name;

    private String colorSquaresRgb;

    private BigDecimal priceAdjustment;

    private Boolean priceAdjustmentPercentage;

    private BigDecimal weightAdjustment;

    private BigDecimal cost;

    private Boolean isPreSelected;

    private Integer displayOrder;

    private List<Long> pictureId;

}
