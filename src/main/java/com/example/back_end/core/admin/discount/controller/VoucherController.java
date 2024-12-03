package com.example.back_end.core.admin.discount.controller;

import com.example.back_end.core.admin.discount.payload.request.ValidateCouponsRequest;
import com.example.back_end.core.admin.discount.payload.request.DiscountFilterRequest;
import com.example.back_end.core.admin.discount.payload.request.VoucherRequest;
import com.example.back_end.core.admin.discount.payload.request.VoucherUpdateRequest;
import com.example.back_end.core.admin.discount.payload.response.DiscountFullResponse;
import com.example.back_end.core.admin.discount.payload.response.VoucherApplyResponseWrapper;
import com.example.back_end.core.admin.discount.payload.response.VoucherFullResponse;
import com.example.back_end.core.admin.discount.payload.response.VoucherResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.infrastructure.exception.InvalidDataException;
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.service.discount.VoucherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

    @PutMapping("/{voucherId}")
    @Operation(summary = "Update an existing voucher", description = "Update the details of an existing voucher by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voucher updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "404", description = "Voucher not found")
    })
    public ResponseEntity<?> updateVoucher(
            @PathVariable Long voucherId,
            @RequestBody @Valid VoucherUpdateRequest updateRequest) {
        try {
            voucherService.updateVoucher(voucherId, updateRequest);
            return ResponseEntity.ok(
                    Map.of("message", "Voucher updated successfully")
            );
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("message", e.getMessage())
            );
        } catch (InvalidDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of("message", e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("message", "Unexpected error occurred: " + e.getMessage())
            );
        }
    }
    @GetMapping("/{voucherId}")
    @Operation(summary = "Get a voucher by ID", description = "Retrieve details of a voucher by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voucher retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Voucher not found")
    })
    public ResponseEntity<?> getVoucherById(@PathVariable Long voucherId) {
        try {
            VoucherFullResponse voucherResponse = voucherService.getVoucherById(voucherId);
            return ResponseEntity.ok(voucherResponse);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("message", e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("message", "Unexpected error occurred: " + e.getMessage())
            );
        }
    }


    @PostMapping("/validate-coupons")
    @Operation(summary = "Validate and Calculate Discounts", description = "Check the validity of multiple coupon codes and calculate discount amounts.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Validation completed"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    public ResponseEntity<?> validateCoupons(@RequestBody ValidateCouponsRequest request) {
        try {
            BigDecimal subTotal = request.getSubTotal();
            List<String> couponCodes = request.getCouponCodes();
            String email = request.getEmail();
            VoucherApplyResponseWrapper response = voucherService.validateAndCalculateDiscounts(subTotal, couponCodes, email);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of("message", e.getMessage())
            );
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
