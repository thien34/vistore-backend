package com.example.back_end.core.admin.customer.payload.request;

import com.example.back_end.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPasswordRequest {

    private Customer customer;

    private String rawPassword;

}
