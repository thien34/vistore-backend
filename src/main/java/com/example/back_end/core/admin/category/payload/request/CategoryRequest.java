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

    @NotBlank(message = "Tên là bắt buộc")
    @Size(max = 25, message = "Tên phải có tối đa 25 ký tự")
    @Pattern(regexp = "^[^<>]*$", message = "Tên không được chứa ký tự đặc biệt")
    private String name;

    private String description;

    private String linkImg;

    private Long categoryParentId;

}