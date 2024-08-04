package com.example.back_end.core.admin.product.payload.response;

import com.example.back_end.entity.PredefinedProductAttributeValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
        PredefinedProductAttributeValueResponse response = new PredefinedProductAttributeValueResponse();
        response.setId(value.getId());
        response.setName(value.getName());
        response.setPriceAdjustment(value.getPriceAdjustment());
        response.setPriceAdjustmentUsePercentage(value.getPriceAdjustmentUsePercentage());
        response.setWeightAdjustment(value.getWeightAdjustment());
        response.setCost(value.getCost());
        response.setIsPreSelected(value.getIsPreSelected());
        response.setDisplayOrder(value.getDisplayOrder());
        response.setProductAttribute(value.getProductAttribute().getId());
        return response;
    }
}
