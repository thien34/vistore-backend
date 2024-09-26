package com.example.back_end.core.admin.customer.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRoleRequest {

    @NotBlank(message = "Name must not be blank")
    String name;

    Boolean active;

}
