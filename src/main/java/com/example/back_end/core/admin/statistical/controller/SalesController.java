package com.example.back_end.core.admin.statistical.controller;

import com.example.back_end.core.admin.statistical.payload.AllSalesResponse;
import com.example.back_end.core.admin.statistical.payload.DynamicResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.statistical.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.Instant;
@RestController
@RequestMapping("/admin/sales")
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;

    @GetMapping("/statistics")
    public ResponseData<AllSalesResponse> getAllSalesStatistics() {
        AllSalesResponse allSalesResponse = salesService.getAllSaless();
        return new ResponseData<>(HttpStatus.OK.value(), "Lấy thống kê doanh số thành công", allSalesResponse);
    }
    @GetMapping("/summary")
    public ResponseData<DynamicResponse> getSalesSummary(
            @RequestParam(required = false) Instant startDate,
            @RequestParam(required = false) Instant endDate
    ) {
        try {
            DynamicResponse response = salesService.getSalesSummary(startDate, endDate);
            return new ResponseData<>(HttpStatus.OK.value(), "Lấy tóm tắt doanh số thành công", response);
        } catch (IllegalArgumentException e) {
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), "Tham số không hợp lệ", null);
        }
    }

}
