package com.example.back_end.core.admin.product.payload.request;

import com.example.back_end.entity.Product;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequestUpdate {
    Long id;

    @NotEmpty
    String name;
    String sku;
    String gtin;
    String fullDescription;
    Integer quantity;
    BigDecimal unitPrice;
    BigDecimal productCost;
    BigDecimal weight;
    Boolean published;
    Boolean deleted;
    Long categoryId;
    Long manufacturerId;
    List<ProductAttribute> attributes;
    String imageUrl;

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
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ProductAttribute {
        Long attributeId;
        Long productId;
        String value;
    }

}


