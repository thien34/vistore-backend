package com.example.back_end.core.admin.relatedProducts.payload.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatedProductRequest {

    @NotNull()
    private Long product1Id;

    @NotNull
    private Long product2Id;

    @Min(value = 0, message = " Start from 0")
    @Max(value = 100, message = "End at 100")
    private Integer displayOrder;

}
