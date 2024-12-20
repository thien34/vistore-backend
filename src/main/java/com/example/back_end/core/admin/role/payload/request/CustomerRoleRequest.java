package com.example.back_end.core.admin.role.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRoleRequest {

    private Long id;

    @NotBlank(message = "Tên không được để trống")
    private String name;

    private boolean active;

}
