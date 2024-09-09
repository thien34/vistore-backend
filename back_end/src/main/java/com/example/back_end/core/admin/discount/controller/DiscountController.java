package com.example.back_end.core.admin.discount.controller;

import com.example.back_end.core.admin.discount.payload.response.DiscountNameResponse;
import com.example.back_end.core.admin.discount.service.DiscountService;
import com.example.back_end.core.common.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/discounts")
public class DiscountController {

    private final DiscountService discountService;

    @GetMapping("/list-name")
    public ResponseData<List<DiscountNameResponse>> getAllDiscountNames(@RequestParam(name = "discountType") Integer discountType) {

        List<DiscountNameResponse> responses = discountService.getAllDiscounts(discountType);

        return ResponseData.<List<DiscountNameResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Get categories name success")
                .data(responses)
                .build();
    }

}
