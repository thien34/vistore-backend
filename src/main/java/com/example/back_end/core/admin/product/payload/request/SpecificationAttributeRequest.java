package com.example.back_end.core.admin.product.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecificationAttributeRequest {

    @NotBlank
    @Size(max = 255, message = "Tên không được vượt quá 255 ký tự")
    private String name;

    private Long specificationAttributeGroupId;

    private Integer displayOrder;

}
