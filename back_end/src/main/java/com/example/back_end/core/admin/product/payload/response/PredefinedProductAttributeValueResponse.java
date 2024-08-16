package com.example.back_end.core.admin.product.payload.response;

import com.example.back_end.entity.PredefinedProductAttributeValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PredefinedProductAttributeValueResponse {

    Long id;

    Long productAttribute;

    String name;

    BigDecimal priceAdjustment;

    Boolean priceAdjustmentUsePercentage;

    BigDecimal weightAdjustment;

    BigDecimal cost;

    Boolean isPreSelected;

    Integer displayOrder;

    public static PredefinedProductAttributeValueResponse mapToResponse(PredefinedProductAttributeValue value) {
        return PredefinedProductAttributeValueResponse.builder()
                .id(value.getId())
                .name(value.getName())
                .priceAdjustment(value.getPriceAdjustment())
                .priceAdjustmentUsePercentage(value.getPriceAdjustmentUsePercentage())
                .weightAdjustment(value.getWeightAdjustment())
                .cost(value.getCost())
                .isPreSelected(value.getIsPreSelected())
                .displayOrder(value.getDisplayOrder())
                .productAttribute(value.getProductAttribute().getId())
                .build();
    }

}
