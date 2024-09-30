package com.example.back_end.core.admin.customer.controller;

import com.example.back_end.core.admin.customer.payload.response.WardResponse;
import com.example.back_end.core.admin.customer.service.WardService;
import com.example.back_end.core.common.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/wards")
@RequiredArgsConstructor
public class WardController {
    private final WardService wardService;

    @GetMapping
    public ResponseData<List<WardResponse>> getAllWards() {

        List<WardResponse> response = wardService.getAllWard();

        return ResponseData.<List<WardResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Get all wards successfully")
                .data(response)
                .build();
    }
}
