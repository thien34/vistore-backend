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
                .message("Lấy tất cả mã giảm giá thành công")
                .data(response)
                .build();
    }


    @PostMapping("/generate-birthday-vouchers")
    public ResponseEntity<String> generateBirthdayVouchers() {
        try {
            voucherService.checkAndGenerateBirthdayVoucher();
            return ResponseEntity.ok("Phiếu quà tặng sinh nhật đã được tạo thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Không tạo được phiếu quà tặng sinh nhật:" + e.getMessage());
        }
    }

    @PutMapping("/{voucherId}")
    @Operation(summary = "Cập nhật phiếu giảm giá hiện có", description = "Cập nhật chi tiết phiếu giảm giá hiện có theo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voucher được cập nhật thành công"),
            @ApiResponse(responseCode = "400", description = "Tải trọng yêu cầu không hợp lệ"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy phiếu giảm giá")
    })
    public ResponseEntity<?> updateVoucher(
            @PathVariable Long voucherId,
            @RequestBody @Valid VoucherUpdateRequest updateRequest) {
        try {
            voucherService.updateVoucher(voucherId, updateRequest);
            return ResponseEntity.ok(
                    Map.of("message", "Voucher được cập nhật thành công")
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
                    Map.of("message", "Đã xảy ra lỗi không mong muốn: " + e.getMessage())
            );
        }
    }
    @GetMapping("/{voucherId}")
    @Operation(summary = "Nhận voucher bằng ID", 
               description = "Truy xuất thông tin chi tiết của phiếu giảm giá theo ID của nó.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Đã lấy phiếu giảm giá thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy phiếu giảm giá")
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
                    Map.of("message", "Đã xảy ra lỗi không mong muốn: " + e.getMessage())
            );
        }
    }


    @PostMapping("/validate-coupons")
    @Operation(summary = "Xác nhận và tính toán giảm giá", 
            description = "Kiểm tra tính hợp lệ của nhiều phiếu giảm giá và tính toán số tiền chiết khấu.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Xác thực hoàn tất"),
            @ApiResponse(responseCode = "400", description = "Xác thực không thành công")
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
    @Operation(summary = "Tạo một phiếu giảm giá mới", description = "Tạo một phiếu giảm giá mới với các chi tiết được cung cấp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Đã tạo voucher thành công"),
            @ApiResponse(responseCode = "400", description = "Tải trọng yêu cầu không hợp lệ")
    })
    public ResponseData<Void> createDiscount(@Valid @RequestBody VoucherRequest discountRequest) {
        voucherService.createDiscount(discountRequest);

        return ResponseData.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Đã tạo voucher thành công")
                .data(null)
                .build();
    }
}
