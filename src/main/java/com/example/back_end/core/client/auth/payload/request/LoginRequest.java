package com.example.back_end.core.client.auth.payload.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {

    private String email;

    // todo: lên hash từ fe
    private String rawPassword;

}
