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
public class TotalRevenueResponse {
    private BigDecimal doanhThu;
    private int soLuongSanPhamBanDuoc;
    private int donHangThanhCong;
    private int donHuy;
}
