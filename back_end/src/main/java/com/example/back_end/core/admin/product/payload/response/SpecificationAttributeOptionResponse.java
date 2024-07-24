package com.example.back_end.core.admin.product.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationAttributeOptionResponse {
    private Long id;
    private String name;
    private String colorSquaresRgb;
    private Integer displayOrder;
}
