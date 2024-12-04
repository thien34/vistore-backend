package com.example.back_end.core.client.cart.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartResponse {

    private Long id;

    private Long idProduct;
    private String slug;
    private String name;
    private String image;
    private BigDecimal unitPrice;
    private BigDecimal discountPrice;
    private String attributeProduct;
    private Integer quantityProduct;

    private Integer quantity;

}
