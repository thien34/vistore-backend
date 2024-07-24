package com.example.back_end.core.admin.product.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationAttributeUpdateRequest {
    @NotBlank
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    private String specificationAttributeGroupId;

    private Integer displayOrder;

    private List<SpecificationAttributeOptionRequest> listOptions;

}
