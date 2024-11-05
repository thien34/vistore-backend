package com.example.back_end.core.admin.product.payload.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    private List<ProductAttribute> attributes = new ArrayList<>();

    private MultipartFile image;

    @Data
    @AllArgsConstructor
    public static class ProductAttribute {
        private Long id;
        private Long productId;
        private String value;
    }

}
