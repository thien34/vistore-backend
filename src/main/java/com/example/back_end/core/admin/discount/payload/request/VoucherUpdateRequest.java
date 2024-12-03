package com.example.back_end.core.admin.discount.payload.request;

import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
public class VoucherUpdateRequest {
    private String name;
    private Instant startDate;
    private Instant endDate;
    private Integer maxUsageCount;
}
