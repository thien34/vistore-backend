package com.example.back_end.core.admin.customer.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerPasswordRequest {

    private Long customerId;

    private String password;

    private String passwordSalt;

}
