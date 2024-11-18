package com.example.back_end.service.auth;

import com.example.back_end.core.client.auth.payload.request.LoginRequest;
import com.example.back_end.core.client.auth.payload.response.LoginResponse;

public interface AuthService {

    LoginResponse authenticate(LoginRequest loginRequest);

}
