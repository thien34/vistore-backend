package com.example.back_end.core.admin.product.payload.request;

import com.example.back_end.infrastructure.constant.AttributeControlType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductProductAttributeMappingRequest {

    private Long id;

    @NotNull
    private Long productId;

    @NotNull
    private Long productAttributeId;

    private String textPrompt;

    private Boolean isRequired;

    private AttributeControlType attributeControlTypeId;

    private Integer displayOrder;

    private List<ProductAttributeValueRequest> productAttributeValueRequests;

}
