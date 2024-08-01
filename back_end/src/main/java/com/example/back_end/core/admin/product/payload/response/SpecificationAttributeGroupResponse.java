package com.example.back_end.core.admin.product.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecificationAttributeGroupResponse {

    private Long id;

    private String name;

    private Integer displayOrder;

    private List<SpecificationAttributeResponse> specificationAttributes;
}
