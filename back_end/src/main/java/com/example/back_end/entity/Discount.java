package com.example.back_end.entity;

import com.example.back_end.infrastructure.constant.DiscountLimitationType;
import com.example.back_end.infrastructure.constant.DiscountType;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "discount", schema = "public", catalog = "store_db")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "discount_type_id", nullable = true)
    private DiscountType discountType;

    @Column(name = "discount_limitation_id", nullable = true)
    private DiscountLimitationType discountLimitation;

    @Column(name = "name", nullable = true, length = 255)
    private String name;

    @Column(name = "coupon_code", nullable = true, length = 255)
    private String couponCode;

    @Column(name = "comment", nullable = true, length = 255)
    private String comment;

    @Column(name = "use_percentage", nullable = true)
    private Boolean usePercentage;

    @Column(name = "discount_percentage", nullable = true, precision = 2)
    private BigDecimal discountPercentage;

    @Column(name = "discount_amount", nullable = true, precision = 2)
    private BigDecimal discountAmount;

    @Column(name = "max_discount_amount", nullable = true, precision = 2)
    private BigDecimal maxDiscountAmount;

    @Column(name = "start_date_utc", nullable = true)
    private Timestamp startDateUtc;

    @Column(name = "end_date_utc", nullable = true)
    private LocalDateTime endDateUtc;

    @Column(name = "requires_coupon_code", nullable = true)
    private Boolean requiresCouponCode;

    @Column(name = "is_cumulative", nullable = true)
    private Boolean isCumulative;

    @Column(name = "limitation_times", nullable = true)
    private Integer limitationTimes;

    @Column(name = "max_discounted_quantity", nullable = true)
    private Integer maxDiscountedQuantity;

    @Column(name = "applied_to_sub_categories", nullable = true)
    private Boolean appliedToSubCategories;

    @Column(name = "min_oder_amount", nullable = true, precision = 2)
    private BigDecimal minOderAmount;

    @Column(name = "is_active", nullable = true)
    private Boolean isActive;

}
