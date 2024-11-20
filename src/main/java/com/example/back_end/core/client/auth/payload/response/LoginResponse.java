package com.example.back_end.core.client.auth.payload.response;

import com.example.back_end.core.admin.customer.payload.response.CustomerFullResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private CustomerFullResponse customerInfo;

}
