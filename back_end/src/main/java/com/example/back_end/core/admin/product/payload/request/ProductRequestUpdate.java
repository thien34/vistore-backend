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
        existingProduct.setSku(this.sku);
        existingProduct.setGtin(this.gtin);
        existingProduct.setFullDescription(this.fullDescription);
        existingProduct.setQuantity(this.quantity);
        existingProduct.setUnitPrice(this.unitPrice);
        existingProduct.setProductCost(this.productCost);
        existingProduct.setWeight(this.weight);
        existingProduct.setPublished(this.published);
        existingProduct.setDeleted(this.deleted);
        existingProduct.setImage(this.imageUrl);

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


