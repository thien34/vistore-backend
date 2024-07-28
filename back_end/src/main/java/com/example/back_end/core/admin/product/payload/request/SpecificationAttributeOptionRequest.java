package com.example.back_end.core.admin.product.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationAttributeOptionRequest {
    private Long id;
    private String name;
    private String colorSquaresRgb;
    private Integer displayOrder;
    private Long specificationAttributeId;
}
