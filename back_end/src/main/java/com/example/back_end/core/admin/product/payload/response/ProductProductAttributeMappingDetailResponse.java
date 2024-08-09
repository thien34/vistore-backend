package com.example.back_end.core.admin.product.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductProductAttributeMappingDetailResponse {

    private Long id;

    private Long productId;

    private Long productAttributeId;

    private String textPrompt;

    private Boolean isRequired;

    private String attributeControlTypeId;

    private Integer displayOrder;

    private List<ProductAttributeValueResponse> productAttributeValueResponses;
}
