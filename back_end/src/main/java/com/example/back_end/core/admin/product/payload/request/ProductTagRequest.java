package com.example.back_end.core.admin.product.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductTagRequest {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Long productId;

}
