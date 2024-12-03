package com.example.back_end.core.admin.address.controller;

import com.example.back_end.core.admin.address.payload.response.WardResponse;
import com.example.back_end.core.client.address.WardApiResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.address.WardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin/wards")
public class WardController {

    private final WardService wardService;

    @GetMapping("/{districtCode}")
    public ResponseData<List<WardResponse>> getAllWards(@PathVariable String districtCode) {

        List<WardResponse> response = wardService.getAllWardByDistrictCode(districtCode);

        return ResponseData.<List<WardResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Get all wards successfully")
                .data(response)
                .build();
    }

    @PostMapping("/sync")
    public void syncWards(@RequestBody List<WardApiResponse> wardApiResponses) {
        wardService.syncWard(wardApiResponses);
    }

}
