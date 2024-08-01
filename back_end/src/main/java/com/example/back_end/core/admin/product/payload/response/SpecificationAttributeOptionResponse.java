package com.example.back_end.core.admin.product.payload.response;

import com.example.back_end.entity.ProductSpecificationAttributeMapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationAttributeOptionResponse {

    private Long id;

    private String name;

    private String colorSquaresRgb;

    private Integer displayOrder;

    private List<ProductSpecificationAttributeMapping> productSpecificationAttributeMappings;

    private Long specificationAttributeId;
}
