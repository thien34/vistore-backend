package com.example.back_end.core.admin.address.controller;

import com.example.back_end.core.admin.address.payload.response.DistrictResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.address.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/districts")
@RequiredArgsConstructor
public class DistrictController {

    private final DistrictService districtService;

    @GetMapping("/{provinceCode}")
    public ResponseData<List<DistrictResponse>> getAllDistricts(@PathVariable String provinceCode) {

        List<DistrictResponse> response = districtService.getAllDistrictByID(provinceCode);

        return ResponseData.<List<DistrictResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Get all districts successfully")
                .data(response)
                .build();
    }

}
