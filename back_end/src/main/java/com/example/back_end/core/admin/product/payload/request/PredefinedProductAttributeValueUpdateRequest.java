package com.example.back_end.core.admin.product.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PredefinedProductAttributeValueUpdateRequest {

    Long id;

    @NotBlank(message = "Name must not be blank")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    String name;

    BigDecimal priceAdjustment;

    Boolean priceAdjustmentUsePercentage;

    BigDecimal weightAdjustment;

    BigDecimal cost;

    Boolean isPreSelected;

    Integer displayOrder;
}
