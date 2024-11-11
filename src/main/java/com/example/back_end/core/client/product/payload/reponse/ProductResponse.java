package com.example.back_end.core.client.product.payload.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private Long id;

    private String slug;

    private String name;

    private BigDecimal unitPrice;

    private BigDecimal discountPrice;

    private String image;

    private String categoryName;

    private int sold;

}
