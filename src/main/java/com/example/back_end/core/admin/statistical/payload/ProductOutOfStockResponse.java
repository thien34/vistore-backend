package com.example.back_end.core.admin.statistical.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductOutOfStockResponse {
    private String tenSanPham;
    private int soLuong;
    private BigDecimal giaTien;
    private String anh;
}
