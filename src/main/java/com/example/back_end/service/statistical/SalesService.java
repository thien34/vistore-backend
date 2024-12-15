package com.example.back_end.service.statistical;

import com.example.back_end.core.admin.statistical.payload.AllSalesResponse;
import com.example.back_end.core.admin.statistical.payload.DynamicResponse;
import java.time.Instant;
public interface SalesService {
    AllSalesResponse getAllSaless();
    DynamicResponse getSalesSummary(Instant startDate, Instant endDate);
}
