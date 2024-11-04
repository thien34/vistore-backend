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
    @NotBlank(message = "Email must not be empty.")
    String email;

    String firstName;

    String lastName;

    GenderType gender;

    Instant dateOfBirth;

    Boolean active;

    @NotEmpty(message = "Customer Roles must not be empty.")
    List<Long> customerRoles;

}
