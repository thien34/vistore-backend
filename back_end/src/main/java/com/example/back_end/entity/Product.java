package com.example.back_end.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product", schema = "public", catalog = "store_db")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = true, length = 255)
    private String name;

    @Column(name = "sku", nullable = true, length = 255)
    private String sku;

    @Column(name = "manufacture_part_number", nullable = true, length = 255)
    private String manufacturePartNumber;

    @Column(name = "gtin", nullable = true, length = 255)
    private String gtin;

    @Column(name = "short_description", nullable = true, length = 255)
    private String shortDescription;

    @Column(name = "full_description", nullable = true, length = 255)
    private String fullDescription;

    @Column(name = "show_on_home_page", nullable = true)
    private Boolean showOnHomePage;

    @Column(name = "allow_customer_reviews", nullable = true)
    private Boolean allowCustomerReviews;

    @Column(name = "approved_rating_sum", nullable = true)
    private Integer approvedRatingSum;

    @Column(name = "not_approved_rating_sum", nullable = true)
    private Integer notApprovedRatingSum;

    @Column(name = "approve_total_reviews", nullable = true)
    private Integer approveTotalReviews;

    @Column(name = "not_approved_total_reviews", nullable = true)
    private Integer notApprovedTotalReviews;

    @Column(name = "is_ship_enabled", nullable = true)
    private Boolean isShipEnabled;

    @Column(name = "is_free_shipping", nullable = true)
    private Boolean isFreeShipping;

    @Column(name = "manage_inventory_method_id", nullable = true)
    private Integer manageInventoryMethodId;

    @Column(name = "product_availability_range_id", nullable = true)
    private Integer productAvailabilityRangeId;

    @Column(name = "display_stock_availability", nullable = true)
    private Boolean displayStockAvailability;

    @Column(name = "display_stock_quantity", nullable = true)
    private Boolean displayStockQuantity;

    @Column(name = "min_stock_quantity", nullable = true)
    private Integer minStockQuantity;

    @Column(name = "order_minimum_quantity", nullable = true)
    private Integer orderMinimumQuantity;

    @Column(name = "not_returnable", nullable = true)
    private Boolean notReturnable;

    @Column(name = "unit_price", nullable = true, precision = 2)
    private BigDecimal unitPrice;

    @Column(name = "old_price", nullable = true, precision = 2)
    private BigDecimal oldPrice;

    @Column(name = "product_cost", nullable = true, precision = 2)
    private BigDecimal productCost;

    @Column(name = "mark_as_new", nullable = true)
    private Boolean markAsNew;

    @Column(name = "mark_as_new_start_date_time_utc", nullable = true)
    private LocalDateTime markAsNewStartDateTimeUtc;

    @Column(name = "mark_as_new_end_date_time_utc", nullable = true)
    private LocalDateTime markAsNewEndDateTimeUtc;

    @Column(name = "weight", nullable = true, precision = 2)
    private BigDecimal weight;

    @Column(name = "length", nullable = true, precision = 2)
    private BigDecimal length;

    @Column(name = "width", nullable = true, precision = 2)
    private BigDecimal width;

    @Column(name = "height", nullable = true, precision = 2)
    private BigDecimal height;

    @Column(name = "available_start_date_time_utc", nullable = true)
    private LocalDateTime availableStartDateTimeUtc;

    @Column(name = "available_end_date_time_utc", nullable = true)
    private LocalDateTime availableEndDateTimeUtc;

    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;

    @Column(name = "published", nullable = true)
    private Boolean published;

    @Column(name = "deleted", nullable = true)
    private Boolean deleted;

}
