package com.example.back_end.core.admin.discount.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VoucherApplyResponseWrapper {
    private BigDecimal totalDiscount;
    private List<Long> applicableVoucherIds;
    private List<VoucherApplyResponse> voucherResponses;
}
