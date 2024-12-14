package com.example.back_end.service.discount;

import com.example.back_end.core.admin.discount.payload.request.DiscountFilterRequest;
import com.example.back_end.core.admin.discount.payload.request.VoucherBirthdayUpdateRequest;
import com.example.back_end.core.admin.discount.payload.request.VoucherRequest;
import com.example.back_end.core.admin.discount.payload.request.VoucherUpdateRequest;
import com.example.back_end.core.admin.discount.payload.response.VoucherApplyResponseWrapper;
import com.example.back_end.core.admin.discount.payload.response.VoucherFullResponse;
import com.example.back_end.core.admin.discount.payload.response.VoucherResponse;
import com.example.back_end.entity.Discount;

import java.math.BigDecimal;
import java.util.List;

public interface VoucherService {

    List<VoucherResponse> getAllVouchers(DiscountFilterRequest filterRequest);

    Discount createDiscount(VoucherRequest voucherRequest);

    Discount getDefaultBirthdayDiscount();

    void checkAndGenerateBirthdayVoucher();

    void updateVoucher(Long voucherId, VoucherUpdateRequest request);

    void updateDefaultBirthdayDiscount(VoucherBirthdayUpdateRequest request);

    VoucherFullResponse getVoucherById(Long id);

    void setDefaultBirthdayDiscountPercentage(BigDecimal discountPercentage);

    VoucherApplyResponseWrapper validateAndCalculateDiscounts(BigDecimal subTotal, List<String> couponCodes, String email);

}
