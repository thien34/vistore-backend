package com.example.back_end.core.admin.customer.controller;

import com.example.back_end.core.admin.customer.payload.response.ProvinceResponse;
import com.example.back_end.core.admin.customer.service.ProvinceService;
import com.example.back_end.core.common.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/provinces")
@RequiredArgsConstructor
public class ProvinceController {
    private final ProvinceService provinceService;

    @GetMapping
    public ResponseData<List<ProvinceResponse>> getAllProvinces() {

        List<ProvinceResponse> response = provinceService.getAllProvince();

        return ResponseData.<List<ProvinceResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Get all provinces successfully")
                .data(response)
                .build();
    }
}
