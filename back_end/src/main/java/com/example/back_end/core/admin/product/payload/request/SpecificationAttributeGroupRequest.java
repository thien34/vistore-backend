package com.example.back_end.core.admin.product.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationAttributeGroupRequest {
    private String name;
    private Integer displayOrder;
}
