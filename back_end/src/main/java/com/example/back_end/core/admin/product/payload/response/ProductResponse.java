package com.example.back_end.core.admin.product.payload.response;

import com.example.back_end.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String name;
    private Boolean deleted;
    private Long categoryId;
    private Long manufacturerId;
    private String sku;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal productCost;
    private List<ProductAttributeValueResponse> attributes;

    public ProductResponse(Long id, String name, Boolean deleted, Long categoryId, Long manufacturerId) {
        this.id = id;
        this.name = name;
        this.deleted = deleted;
        this.categoryId = categoryId;
        this.manufacturerId = manufacturerId;
    }

    public static ProductResponse fromProduct(final Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDeleted(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getManufacturer() != null ? product.getManufacturer().getId() : null
        );
    }


    public static ProductResponse fromProductFull(final Product product, List<ProductAttributeValueResponse> attributes) {

        return new ProductResponse(
                product.getId(),
                product.getFullName(),
                product.getDeleted(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getManufacturer() != null ? product.getManufacturer().getId() : null,
                product.getSku(),
                product.getUnitPrice(),
                product.getQuantity(),
                product.getProductCost(),
                attributes
        );
    }

    @Data
    public static class ProductAttributeValueResponse {
        private Long id;
        private String value;
        private String imageUrl;
    }
}
