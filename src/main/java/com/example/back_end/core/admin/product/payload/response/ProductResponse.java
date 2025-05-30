package com.example.back_end.core.admin.product.payload.response;

import com.example.back_end.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ProductResponse {

    private Long id;
    private String name;
    private Boolean deleted;
    private Long categoryId;
    private Long manufacturerId;
    private String sku;
    private BigDecimal price;
    private BigDecimal largestDiscountPercentage;
    private BigDecimal discountPrice;
    private Integer quantity;
    private BigDecimal productCost;
    private List<ProductAttribute> attributes;
    private String imageUrl;
    private BigDecimal weight;
    private String gtin;
    private String categoryName;
    private String manufacturerName;
    private String fullDescription;

    public ProductResponse(Long id, String name, Boolean deleted, Long categoryId, Long manufacturerId, String categoryName, String manufacturerName, BigDecimal weight, String fullDescription) {
        this.id = id;
        this.name = name;
        this.deleted = deleted;
        this.categoryId = categoryId;
        this.manufacturerId = manufacturerId;
        this.categoryName = categoryName;
        this.manufacturerName = manufacturerName;
        this.weight = weight;
        this.fullDescription = fullDescription;
    }

    public ProductResponse(Long id, String fullName, Boolean deleted, Long aLong, Long aLong1, String sku, BigDecimal unitPrice, Integer quantity, BigDecimal productCost, List<ProductAttribute> attributes, String image, String gtin, BigDecimal discountPrice, String categoryName, String manufacturerName) {
        this.id = id;
        this.name = fullName;
        this.deleted = deleted;
        this.categoryId = aLong;
        this.manufacturerId = aLong1;
        this.sku = sku;
        this.price = unitPrice;
        this.quantity = quantity;
        this.productCost = productCost;
        this.attributes = attributes;
        this.imageUrl = image;
        this.gtin = gtin;
        this.discountPrice = discountPrice;
        this.categoryName = categoryName;
        this.manufacturerName = manufacturerName;
    }

    public ProductResponse(Long id, String fullName, Boolean deleted, Long aLong, Long aLong1, String sku, BigDecimal unitPrice, Integer quantity, BigDecimal productCost, List<ProductAttribute> attributes, String image, String gtin, String s, String s1, BigDecimal discountPrice) {
        this.id = id;
        this.name = fullName;
        this.deleted = deleted;
        this.categoryId = aLong;
        this.manufacturerId = aLong1;
        this.sku = sku;
        this.price = unitPrice;
        this.quantity = quantity;
        this.productCost = productCost;
        this.attributes = attributes;
        this.imageUrl = image;
        this.gtin = gtin;
        this.categoryName = s;
        this.manufacturerName = s1;
        this.discountPrice = discountPrice;
    }

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getFullName();
        this.sku = product.getSku();
        this.price = product.getUnitPrice();
        this.quantity = product.getQuantity();
        this.productCost = product.getProductCost();
        this.imageUrl = product.getImage();
        this.gtin = product.getGtin();
        this.categoryName = product.getCategory().getName();
        this.manufacturerName = product.getManufacturer().getName();
        this.attributes = product.getProductAttributeValues().stream()
                .map(attrValue -> new ProductAttribute(
                        attrValue.getProductAttribute().getId(),
                        attrValue.getProductAttribute().getName(),
                        attrValue.getValue()
                ))
                .toList();
        this.discountPrice = product.getDiscountPrice();
    }

    public static ProductResponse fromProduct(final Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDeleted(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getManufacturer() != null ? product.getManufacturer().getId() : null,
                product.getCategory() != null ? product.getCategory().getName() : null,
                product.getManufacturer() != null ? product.getManufacturer().getName() : null,
                product.getWeight(),
                product.getFullDescription()
        );
    }

    public static ProductResponse fromProductFull(final Product product, List<ProductAttribute> attributes) {

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
                product.getProductAttributeValues().stream()
                        .map(attrValue -> new ProductAttribute(
                                attrValue.getProductAttribute().getId(),
                                attrValue.getProductAttribute().getName(),
                                attrValue.getValue()
                        ))
                        .toList(),
                product.getImage(),
                product.getGtin(),
                product.getDiscountPrice(),
                product.getCategory() != null ? product.getCategory().getName() : null,
                product.getManufacturer() != null ? product.getManufacturer().getName() : null

        );
    }


    public static ProductResponse fromProductParentId(final Product product, List<ProductAttribute> attributes) {

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
                attributes,
                product.getImage(),
                product.getGtin(),
                product.getCategory() != null ? product.getCategory().getName() : null,
                product.getManufacturer() != null ? product.getManufacturer().getName() : null,
                product.getDiscountPrice()
        );
    }

    @Data
    @AllArgsConstructor
    public static class ProductAttributeValueResponse {

        private Long id;
        private String value;

    }

    @Data
    @AllArgsConstructor
    public static class ProductAttribute {
        private Long id;
        private String name;
        private String value;
    }
}
