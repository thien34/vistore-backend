package com.example.back_end.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product")
public class Product extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "sku", length = Integer.MAX_VALUE)
    private String sku;

    @Column(name = "gtin", length = Integer.MAX_VALUE)
    private String gtin;

    @Column(name = "full_description", length = Integer.MAX_VALUE)
    private String fullDescription;

    @Column(name = "show_on_home_page")
    private Boolean showOnHomePage;

    @Column(name = "allow_customer_reviews")
    private Boolean allowCustomerReviews;

    @Column(name = "is_free_shipping")
    private Boolean isFreeShipping;

    @Column(name = "display_stock_availability")
    private Boolean displayStockAvailability;

    @Column(name = "display_stock_quantity")
    private Boolean displayStockQuantity;

    @Column(name = "min_stock_quantity")
    private Integer minStockQuantity;

    @Column(name = "not_returnable")
    private Boolean notReturnable;

    @Column(name = "unit_price", precision = 18, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "old_price", precision = 18, scale = 2)
    private BigDecimal oldPrice;

    @Column(name = "product_cost", precision = 18, scale = 2)
    private BigDecimal productCost;

    @Column(name = "mark_as_new")
    private Boolean markAsNew;

    @Column(name = "mark_as_new_start_date_time_utc")
    private Instant markAsNewStartDateTimeUtc;

    @Column(name = "mark_as_new_end_date_time_utc")
    private Instant markAsNewEndDateTimeUtc;

    @Column(name = "weight", precision = 18, scale = 2)
    private BigDecimal weight;

    @Column(name = "length", precision = 18, scale = 2)
    private BigDecimal length;

    @Column(name = "width", precision = 18, scale = 2)
    private BigDecimal width;

    @Column(name = "height", precision = 18, scale = 2)
    private BigDecimal height;

    @Column(name = "available_start_date_time_utc")
    private Instant availableStartDateTimeUtc;

    @Column(name = "available_end_date_time_utc")
    private Instant availableEndDateTimeUtc;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "published")
    private Boolean published;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "min_cart_qty")
    private Integer minCartQty;

    @Column(name = "max_cart_qty")
    private Integer maxCartQty;

    @JsonManagedReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<ProductSpecificationAttributeMapping> productSpecificationAttributeMappings;

    @JsonManagedReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<ProductCategoryMapping> productCategoryMappings;

    @JsonManagedReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<ProductProductTagMapping> productProductTagMappings;

    @JsonManagedReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<ProductManufacturerMapping> productManufacturerMappings;

    @JsonManagedReference
    @OneToMany(mappedBy = "product",cascade = CascadeType.REMOVE)
    private List<ProductPictureMapping> productPictureMappings;

}