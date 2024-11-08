package com.example.back_end.core.admin.category.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 25, message = "Name must be max 25 characters")
    @Pattern(regexp = "^[^<>]*$", message = "Name must not contain special characters")
    private String name;

    private String description;

    private String linkImg;

    private Long categoryParentId;

}