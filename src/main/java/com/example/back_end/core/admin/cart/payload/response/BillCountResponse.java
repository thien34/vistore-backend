package com.example.back_end.core.admin.cart.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BillCountResponse {
    private Long numberBill;
    private Integer totalItems;
}
