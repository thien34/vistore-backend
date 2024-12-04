package com.example.back_end.core.admin.manufacturer.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManufacturerRequest {

    private Long id;

    @NotBlank(message = "Tên không được để trống")
    @Size(max = 255, message = "Tên phải ngắn hơn 256 ký tự")
    private String name;

    private String description;

}