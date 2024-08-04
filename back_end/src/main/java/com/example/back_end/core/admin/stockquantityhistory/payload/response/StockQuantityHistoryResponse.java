package com.example.back_end.core.admin.stockquantityhistory.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockQuantityHistoryResponse {
    private Long id;
    private Long productId;
    private Integer quantityAdjustment;
    private Integer stockQuantity;
    private String message;
}
