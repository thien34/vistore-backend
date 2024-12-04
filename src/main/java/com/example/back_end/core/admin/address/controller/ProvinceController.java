package com.example.back_end.core.admin.address.controller;

import com.example.back_end.core.admin.address.payload.response.ProvinceResponse;
import com.example.back_end.core.client.address.ProvinceApiResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.address.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin/provinces")
public class ProvinceController {

    private final ProvinceService provinceService;

    @GetMapping
    public ResponseData<List<ProvinceResponse>> getAllProvinces() {

        List<ProvinceResponse> response = provinceService.getAllProvince();

        return ResponseData.<List<ProvinceResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy tất cả tỉnh thành công")
                .data(response)
                .build();
    }

    @PostMapping("/sync")
    public void syncProvinces(@RequestBody List<ProvinceApiResponse> provinceApiResponses) {
        provinceService.syncProvinces(provinceApiResponses);
    }

}
