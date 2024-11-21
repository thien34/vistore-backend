package com.example.back_end.core.admin.discount.controller;
import com.example.back_end.core.admin.discount.payload.request.DiscountFilterRequest;
import com.example.back_end.core.admin.discount.payload.request.VoucherRequest;
import com.example.back_end.core.admin.discount.payload.response.VoucherResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.discount.VoucherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/vouchers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VoucherController {

    VoucherService voucherService;
    @GetMapping
    public ResponseData<List<VoucherResponse>> getAllVouchers(
            @ModelAttribute DiscountFilterRequest filterRequest) {

        List<VoucherResponse> response = voucherService.getAllVouchers(filterRequest);

        return ResponseData.<List<VoucherResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Get all vouchers successfully")
                .data(response)
                .build();
    }
    @PostMapping("/generate-birthday-vouchers")
    public ResponseEntity<String> generateBirthdayVouchers() {
        try {
            voucherService.checkAndGenerateBirthdayVoucher();
            return ResponseEntity.ok("Birthday vouchers generated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Failed to generate birthday vouchers: " + e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Create a new voucher", description = "Create a new voucher with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Voucher created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    public ResponseData<Void> createDiscount(@Valid @RequestBody VoucherRequest discountRequest) {
        voucherService.createDiscount(discountRequest);

        return ResponseData.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Voucher created successfully")
                .data(null)
                .build();
    }
}