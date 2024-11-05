package com.example.back_end.core.admin.product.payload.request;

import com.example.back_end.entity.Product;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductRequestUpdate {
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
    private String imageUrl;

    public Product toEntity(Product existingProduct) {
        if (this.sku != null) existingProduct.setSku(this.sku);
        if (this.gtin != null) existingProduct.setGtin(this.gtin);
        if (this.fullDescription != null) existingProduct.setFullDescription(this.fullDescription);
        if (this.quantity != null) existingProduct.setQuantity(this.quantity);
        if (this.unitPrice != null) existingProduct.setUnitPrice(this.unitPrice);
        if (this.productCost != null) existingProduct.setProductCost(this.productCost);
        if (this.weight != null) existingProduct.setWeight(this.weight);
        if (this.published != null) existingProduct.setPublished(this.published);
        if (this.deleted != null) existingProduct.setDeleted(this.deleted);
        if (this.imageUrl != null) existingProduct.setImage(this.imageUrl);

        return existingProduct;
    }

    @Data
    @AllArgsConstructor
    public static class ProductAttribute {
        private Long attributeId;
        private Long productId;
        private String value;
    }

}