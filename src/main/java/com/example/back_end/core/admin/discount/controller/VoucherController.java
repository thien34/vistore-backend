package com.example.back_end.core.admin.discount.controller;

import com.example.back_end.core.admin.discount.payload.request.DiscountFilterRequest;
import com.example.back_end.core.admin.discount.payload.request.VoucherRequest;
import com.example.back_end.core.admin.discount.payload.response.VoucherResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.entity.Order;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    /**
     * Apply a voucher to an order.
     *
     * @param orderId   ID of the order.
     * @param voucherId ID of the voucher.
     * @return ResponseEntity with the updated order details or error message.
     */
    @PostMapping("/apply")
    @Operation(summary = "Apply a voucher to an order", description = "Apply a voucher to a specified order by providing orderId and voucherId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voucher applied successfully"),
            @ApiResponse(responseCode = "404", description = "Order or voucher not found"),
            @ApiResponse(responseCode = "400", description = "Invalid voucher or order details"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> applyVoucherToOrder(
            @RequestParam Long orderId,
            @RequestParam Long voucherId) {
        try {
            Order updatedOrder = voucherService.applyVoucher(orderId, voucherId);

            return ResponseEntity.ok(ResponseData.<Order>builder()
                    .status(HttpStatus.OK.value())
                    .message("Voucher applied successfully")
                    .data(updatedOrder)
                    .build());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ResponseData.builder()
                            .status(HttpStatus.NOT_FOUND.value())
                            .message(e.getMessage())
                            .build());
        } catch (InvalidDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ResponseData.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ResponseData.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("An error occurred: " + e.getMessage())
                            .build());
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
