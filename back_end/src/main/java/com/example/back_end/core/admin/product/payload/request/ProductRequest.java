package com.example.back_end.core.admin.product.payload.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductRequest {

    private Long id;

    @NotEmpty
    private String name;

    private String sku;

    private String gtin;

    private String fullDescription;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal productCost;

    private BigDecimal weight;

    private Boolean published;

    private Boolean deleted;

    private Long categoryId;

    private Long manufacturerId;

    private List<ProductAttribute> attributes;

    @Data
    static class ProductAttribute {
        private Long id;
        private Long productId;
        private String value;
    }

}
