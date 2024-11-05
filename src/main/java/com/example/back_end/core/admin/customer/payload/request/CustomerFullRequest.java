package com.example.back_end.core.admin.customer.payload.request;

import com.example.back_end.infrastructure.constant.GenderType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerFullRequest {

    UUID customerGuid;

    @Email
    @NotBlank(message = "Email must not be empty.")
    String email;

    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters long.")
    String password;

    String currentPassword;

    String firstName;

    String lastName;

    GenderType gender;

    Instant dateOfBirth;

    @NotEmpty(message = "Customer Roles must not be empty.")
    List<Long> customerRoles;

    String username;

    Boolean active;

    @Pattern(
            regexp = "^(0\\d{9}|\\+84\\d{9}|84\\d{9})$",
            message = "Số điện thoại không hợp lệ. Vui lòng nhập số điện thoại Việt Nam hợp lệ"
    )
    String phone;

    Boolean hasShoppingCartItems;

    Boolean requireReLogin;

    Integer failedLoginAttempts;

    Instant cannotLoginUntilDateUtc;

    Boolean deleted;

    Instant lastLoginDateUtc;

    Instant lastActivityDateUtc;

}
