package com.example.back_end.core.admin.customer.payload.request;

import com.example.back_end.infrastructure.constant.GenderType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRequest {

    @Email
    @NotBlank(message = "Email không được để trống.")
    String email;

    String firstName;

    String lastName;

    GenderType gender;

    Instant dateOfBirth;

    Boolean active;

    @NotEmpty(message = "Vai trò khách hàng không được để trống.")
    List<Long> customerRoles;

}
