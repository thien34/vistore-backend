package com.example.back_end.core.admin.customer.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CustomerFullResponse {

    private Long id;

    private UUID customerGuid;

    private String email;

    private String firstName;

    private String lastName;

    private Integer gender;

    private Instant dateOfBirth;

    private List<Long> customerRoles;

    private Boolean active;

}
