package com.example.back_end.core.admin.discount.controller;

import com.example.back_end.core.admin.discount.payload.request.DiscountFilterRequest;
import com.example.back_end.core.admin.discount.payload.request.ValidateCouponsRequest;
import com.example.back_end.core.admin.discount.payload.request.VoucherBirthdayUpdateRequest;
import com.example.back_end.core.admin.discount.payload.request.VoucherRequest;
import com.example.back_end.core.admin.discount.payload.request.VoucherUpdateRequest;
import com.example.back_end.core.admin.discount.payload.response.VoucherApplyResponseWrapper;
import com.example.back_end.core.admin.discount.payload.response.VoucherFullResponse;
import com.example.back_end.core.admin.discount.payload.response.VoucherResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.entity.Discount;
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.service.discount.VoucherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
public class VoucherController {

    private final VoucherService voucherService;

    @GetMapping
    public ResponseData<List<VoucherResponse>> getAllVouchers(@ModelAttribute DiscountFilterRequest filterRequest) {

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

    @PutMapping("/default-birthday-discount")
    public ResponseData<Void> updateDefaultBirthdayDiscount(@RequestBody VoucherBirthdayUpdateRequest request) {

        voucherService.updateDefaultBirthdayDiscount(request);
        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Cập nhật thành công")
                .build();
    }

    @PostMapping("/birthday")
    public ResponseEntity<String> updateBirthdayDiscount(@RequestBody Map<String, BigDecimal> request) {
        BigDecimal discountPercentage = request.get("discountPercentageBirthday");
        if (discountPercentage == null || discountPercentage.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("discountPercentageBirthday phải lớn hơn 0.");
        }
        voucherService.setDefaultBirthdayDiscountPercentage(discountPercentage);
        return ResponseEntity.ok("Giá trị giảm giá sinh nhật mặc định đã được cập nhật thành công.");
    }

    @GetMapping("/default-birthday")
    public ResponseData<Discount> getDefaultBirthdayVoucher() {
        Discount discount = voucherService.getDefaultBirthdayDiscount();

        return ResponseData.<Discount>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy mã giảm giá sinh nhật mặc định thành công")
                .data(discount)
                .build();
    }

    @PutMapping("/{voucherId}")
    public ResponseEntity<?> updateVoucher(
            @PathVariable Long voucherId,
            @RequestBody @Valid VoucherUpdateRequest updateRequest) {
        try {
            voucherService.updateVoucher(voucherId, updateRequest);
            return ResponseEntity.ok(
                    Map.of("message", "Voucher được cập nhật thành công")
            );
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of("message", e.getMessage())
            );
        }
    }

    @GetMapping("/{voucherId}")
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
    public ResponseData<Void> createDiscount(@Valid @RequestBody VoucherRequest discountRequest) {

        voucherService.createDiscount(discountRequest);
        return ResponseData.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Đã tạo voucher thành công")
                .data(null)
                .build();
    }
}
