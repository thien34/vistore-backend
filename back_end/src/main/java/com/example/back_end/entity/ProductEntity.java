package com.example.back_end.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "product", schema = "public", catalog = "datn")
public class ProductEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Basic
    @Column(name = "sku", nullable = true, length = 255)
    private String sku;
    @Basic
    @Column(name = "manufacture_part_number", nullable = true, length = 255)
    private String manufacturePartNumber;
    @Basic
    @Column(name = "gtin", nullable = true, length = 255)
    private String gtin;
    @Basic
    @Column(name = "short_description", nullable = true, length = 255)
    private String shortDescription;
    @Basic
    @Column(name = "full_description", nullable = true, length = 255)
    private String fullDescription;
    @Basic
    @Column(name = "show_on_home_page", nullable = true)
    private Boolean showOnHomePage;
    @Basic
    @Column(name = "allow_customer_reviews", nullable = true)
    private Boolean allowCustomerReviews;
    @Basic
    @Column(name = "approved_rating_sum", nullable = true)
    private Integer approvedRatingSum;
    @Basic
    @Column(name = "not_approved_rating_sum", nullable = true)
    private Integer notApprovedRatingSum;
    @Basic
    @Column(name = "approve_total_reviews", nullable = true)
    private Integer approveTotalReviews;
    @Basic
    @Column(name = "not_approved_total_reviews", nullable = true)
    private Integer notApprovedTotalReviews;
    @Basic
    @Column(name = "is_ship_enabled", nullable = true)
    private Boolean isShipEnabled;
    @Basic
    @Column(name = "is_free_shipping", nullable = true)
    private Boolean isFreeShipping;
    @Basic
    @Column(name = "manage_inventory_method_id", nullable = true)
    private Integer manageInventoryMethodId;
    @Basic
    @Column(name = "product_availability_range_id", nullable = true)
    private Integer productAvailabilityRangeId;
    @Basic
    @Column(name = "display_stock_availability", nullable = true)
    private Boolean displayStockAvailability;
    @Basic
    @Column(name = "display_stock_quantity", nullable = true)
    private Boolean displayStockQuantity;
    @Basic
    @Column(name = "min_stock_quantity", nullable = true)
    private Integer minStockQuantity;
    @Basic
    @Column(name = "order_minimum_quantity", nullable = true)
    private Integer orderMinimumQuantity;
    @Basic
    @Column(name = "not_returnable", nullable = true)
    private Boolean notReturnable;
    @Basic
    @Column(name = "unit_price", nullable = true, precision = 2)
    private BigDecimal unitPrice;
    @Basic
    @Column(name = "old_price", nullable = true, precision = 2)
    private BigDecimal oldPrice;
    @Basic
    @Column(name = "product_cost", nullable = true, precision = 2)
    private BigDecimal productCost;
    @Basic
    @Column(name = "mark_as_new", nullable = true)
    private Boolean markAsNew;
    @Basic
    @Column(name = "mark_as_new_start_date_time_utc", nullable = true)
    private LocalDateTime  markAsNewStartDateTimeUtc;
    @Basic
    @Column(name = "mark_as_new_end_date_time_utc", nullable = true)
    private LocalDateTime  markAsNewEndDateTimeUtc;
    @Basic
    @Column(name = "weight", nullable = true, precision = 2)
    private BigDecimal weight;
    @Basic
    @Column(name = "length", nullable = true, precision = 2)
    private BigDecimal length;
    @Basic
    @Column(name = "width", nullable = true, precision = 2)
    private BigDecimal width;
    @Basic
    @Column(name = "height", nullable = true, precision = 2)
    private BigDecimal height;
    @Basic
    @Column(name = "available_start_date_time_utc", nullable = true)
    private LocalDateTime availableStartDateTimeUtc;
    @Basic
    @Column(name = "available_end_date_time_utc", nullable = true)
    private LocalDateTime  availableEndDateTimeUtc;
    @Basic
    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;
    @Basic
    @Column(name = "published", nullable = true)
    private Boolean published;
    @Basic
    @Column(name = "deleted", nullable = true)
    private Boolean deleted;


}
