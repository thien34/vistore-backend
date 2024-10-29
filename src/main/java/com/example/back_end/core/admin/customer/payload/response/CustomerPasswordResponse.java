package com.example.back_end.core.admin.customer.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerPasswordResponse {

    private Long id;

    private Long customerId;

    private String password;

    private String passwordSalt;

}
