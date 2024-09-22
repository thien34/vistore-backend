package com.example.back_end.core.admin.customer.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerResponse {

    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private Boolean active;

    private List<Long> customerRoles;

}