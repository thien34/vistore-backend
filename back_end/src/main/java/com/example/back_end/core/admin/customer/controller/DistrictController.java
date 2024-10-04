package com.example.back_end.core.admin.customer.controller;

import com.example.back_end.core.admin.customer.payload.response.DistrictResponse;
import com.example.back_end.service.customer.DistrictService;
import com.example.back_end.core.common.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/districts")
@RequiredArgsConstructor
public class DistrictController {

    private final DistrictService districtService;

    @GetMapping
    public ResponseData<List<DistrictResponse>> getAllDistricts() {

        List<DistrictResponse> response = districtService.getAllDistrict();

        return ResponseData.<List<DistrictResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Get all districts successfully")
                .data(response)
                .build();
    }

}
