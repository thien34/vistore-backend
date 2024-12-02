package com.example.back_end.core.admin.order.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReStockQuanityProductRequest {
    private Long productId;
    private Integer quantity;
}
