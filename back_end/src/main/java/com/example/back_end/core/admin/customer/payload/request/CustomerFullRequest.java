package com.example.back_end.core.admin.customer.payload.request;

import com.example.back_end.infrastructure.constant.GenderType;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class CustomerFullRequest {

    private UUID customerGuid;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private GenderType gender;

    private String phone;

    private Instant dateOfBirth;

    private Boolean hasShoppingCartItems;

    private Boolean requireReLogin;

    private Integer failedLoginAttempts;

    private Instant cannotLoginUntilDateUtc;

    private Boolean active;

    private Boolean deleted;

    private Instant lastLoginDateUtc;

    private Instant lastActivityDateUtc;

    private List<Long> customerRoles;

}
