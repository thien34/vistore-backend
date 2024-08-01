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
public class SpecificationAttributeUpdateResponse {

    private Long id;

    private String name;

    private Long specificationAttributeGroupId;

    private String specificationAttributeGroupName;

    private Integer displayOrder;

    private List<SpecificationAttributeOptionResponse> listOptions;
}
