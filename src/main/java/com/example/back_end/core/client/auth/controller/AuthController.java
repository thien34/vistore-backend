package com.example.back_end.core.client.auth.controller;

import com.example.back_end.core.client.auth.payload.request.LoginRequest;
import com.example.back_end.core.client.auth.payload.response.LoginResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseData<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        
        LoginResponse response = authService.authenticate(loginRequest);

        return ResponseData.<LoginResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Login successfully")
                .data(response)
                .build();
    }

}
