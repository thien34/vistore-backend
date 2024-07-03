package com.example.back_end.core.admin.product.payload.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
public class PredefinedProductAttributeValueRequest {

//    @NotBlank(message = "Product attribute must not be blank")
    Long productAttribute;

    @NotBlank(message = "Name must not be blank")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    String name;

    @NotNull(message = "Price adjustment must not be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price adjustment must be zero or positive")
    BigDecimal priceAdjustment;

    @NotNull(message = "Price adjustment use percentage must not be null")
    Boolean priceAdjustmentUsePercentage;

    @NotNull(message = "Weight adjustment must not be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Weight adjustment must be zero or positive")
    BigDecimal weightAdjustment;

    @NotNull(message = "Cost must not be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Cost must be zero or positive")
    BigDecimal cost;

    @NotNull(message = "Is pre-selected must not be null")
    Boolean isPreSelected;

    @NotNull(message = "Display order must not be null")
    @PositiveOrZero(message = "Display order must be zero or positive")
    Integer displayOrder;
}
