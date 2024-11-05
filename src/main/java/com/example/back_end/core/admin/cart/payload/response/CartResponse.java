package com.example.back_end.core.admin.cart.payload.response;

import com.example.back_end.core.admin.product.payload.response.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CartResponse {
    private Long id;
    private String cartUUID;
    private Boolean isAdmin;
    private String parentId;
    private Integer quantity;
    private ProductResponse productResponse;
}
