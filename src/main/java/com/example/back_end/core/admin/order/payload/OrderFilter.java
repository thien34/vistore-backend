package com.example.back_end.core.admin.order.payload;

import com.example.back_end.infrastructure.constant.OrderStatusType;
import com.example.back_end.infrastructure.constant.PaymentStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFilter {

    private String name;
    private Integer paymentMode;
    private BigDecimal startAmount;
    private BigDecimal endAmount;
    private OrderStatusType status;
    private PaymentStatusType paymentStatus;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant endDate;
}
