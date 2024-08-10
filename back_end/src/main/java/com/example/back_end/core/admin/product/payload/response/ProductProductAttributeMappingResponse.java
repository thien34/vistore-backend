package com.example.back_end.core.admin.product.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductProductAttributeMappingResponse {

    private Long id;

    private String nameProductAttribute;

    private String textPrompt;

    private Boolean isRequired;

    private String attributeControlTypeId;

    private Integer displayOrder;

}
