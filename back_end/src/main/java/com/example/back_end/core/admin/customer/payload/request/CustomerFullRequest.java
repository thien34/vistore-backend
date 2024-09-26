package com.example.back_end.core.admin.customer.payload.request;

import com.example.back_end.infrastructure.constant.GenderType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CustomerFullRequest {

    private UUID customerGuid;

    @Email
    @NotBlank(message = "Email must not be empty.")
    private String email;

    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters long.")
    private String password;

    private String firstName;

    private String lastName;

    private GenderType gender;

    private Instant dateOfBirth;

    @NotEmpty(message = "Customer Roles must not be empty.")
    private List<Long> customerRoles;

    private String username;

    private Boolean active;

    private String phone;

    private Boolean hasShoppingCartItems;

    private Boolean requireReLogin;

    private Integer failedLoginAttempts;

    private Instant cannotLoginUntilDateUtc;

    private Boolean deleted;

    private Instant lastLoginDateUtc;

    private Instant lastActivityDateUtc;

}
