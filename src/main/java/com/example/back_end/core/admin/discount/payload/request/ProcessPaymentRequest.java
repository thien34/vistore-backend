package com.example.back_end.core.admin.discount.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessPaymentRequest {
    private Long orderId;
    private List<Long> voucherIds;
}
