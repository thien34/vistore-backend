package com.example.back_end.core.admin.order.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFilter {

    private String name;
    private Integer paymentMode;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant endDate;
}
