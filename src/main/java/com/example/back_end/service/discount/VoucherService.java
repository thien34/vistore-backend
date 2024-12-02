package com.example.back_end.service.discount;

import com.example.back_end.core.admin.discount.payload.request.DiscountFilterRequest;
import com.example.back_end.core.admin.discount.payload.request.VoucherRequest;
import com.example.back_end.core.admin.discount.payload.response.VoucherApplyResponseWrapper;
import com.example.back_end.core.admin.discount.payload.response.VoucherResponse;
import java.math.BigDecimal;
import java.util.List;

public interface VoucherService {

    List<VoucherResponse> getAllVouchers(DiscountFilterRequest filterRequest);

    void createDiscount(VoucherRequest voucherRequest);

    void checkAndGenerateBirthdayVoucher();

    VoucherApplyResponseWrapper validateAndCalculateDiscounts(BigDecimal subTotal, List<String> couponCodes, String email);

}
