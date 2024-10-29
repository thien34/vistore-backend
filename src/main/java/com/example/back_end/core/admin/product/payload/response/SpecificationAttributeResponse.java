package com.example.back_end.core.admin.product.payload.response;

import com.example.back_end.entity.SpecificationAttribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecificationAttributeResponse {

    private Long id;

    private String name;

    private Integer displayOrder;

    private String specificationAttributeGroupName;

    private Long specificationAttributeGroupId;

    private List<SpecificationAttributeOptionResponse> listOptions;

    public static SpecificationAttributeResponse mapToResponse(SpecificationAttribute specificationAttribute) {

        SpecificationAttributeResponse response = SpecificationAttributeResponse.builder()
                .id(specificationAttribute.getId())
                .name(specificationAttribute.getName())
                .displayOrder(specificationAttribute.getDisplayOrder())
                .specificationAttributeGroupId(specificationAttribute
                        .getSpecificationAttributeGroup() != null ? specificationAttribute
                        .getSpecificationAttributeGroup().getId() : null)
                .specificationAttributeGroupName(specificationAttribute
                        .getSpecificationAttributeGroup() != null ? specificationAttribute.
                        getSpecificationAttributeGroup().getName() : null)
                .build();

        List<SpecificationAttributeOptionResponse> optionResponses = specificationAttribute
                .getSpecificationAttributeOptions().stream()
                .map(option -> new SpecificationAttributeOptionResponse(
                        option.getId(),
                        option.getName(),
                        option.getColorSquaresRgb(),
                        option.getDisplayOrder(),
                        option.getProductSpecificationAttributeMappings(),
                        option.getSpecificationAttribute().getId()
                ))
                .sorted(Comparator.comparing(SpecificationAttributeOptionResponse::getDisplayOrder,
                        Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .toList();

        response.setListOptions(optionResponses);
        return response;
    }

}