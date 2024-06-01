package com.example.back_end.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;


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
@Table(name = "discount", schema = "public", catalog = "datn")
public class DiscountEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "discount_type_id", nullable = true)
    private Integer discountTypeId;
    @Basic
    @Column(name = "discount_limitation_id", nullable = true)
    private Integer discountLimitationId;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Basic
    @Column(name = "coupon_code", nullable = true, length = 255)
    private String couponCode;
    @Basic
    @Column(name = "comment", nullable = true, length = 255)
    private String comment;
    @Basic
    @Column(name = "use_percentage", nullable = true)
    private Boolean usePercentage;
    @Basic
    @Column(name = "discount_percentage", nullable = true, precision = 2)
    private BigDecimal discountPercentage;
    @Basic
    @Column(name = "discount_amount", nullable = true, precision = 2)
    private BigDecimal discountAmount;
    @Basic
    @Column(name = "max_discount_amount", nullable = true, precision = 2)
    private BigDecimal maxDiscountAmount;
    @Basic
    @Column(name = "start_date_utc", nullable = true)
    private Timestamp startDateUtc;
    @Basic
    @Column(name = "end_date_utc", nullable = true)
    private LocalDateTime endDateUtc;
    @Basic
    @Column(name = "requires_coupon_code", nullable = true)
    private Boolean requiresCouponCode;
    @Basic
    @Column(name = "is_cumulative", nullable = true)
    private Boolean isCumulative;
    @Basic
    @Column(name = "limitation_times", nullable = true)
    private Integer limitationTimes;
    @Basic
    @Column(name = "max_discounted_quantity", nullable = true)
    private Integer maxDiscountedQuantity;
    @Basic
    @Column(name = "applied_to_sub_categories", nullable = true)
    private Boolean appliedToSubCategories;
    @Basic
    @Column(name = "min_oder_amount", nullable = true, precision = 2)
    private BigDecimal minOderAmount;
    @Basic
    @Column(name = "is_active", nullable = true)
    private Boolean isActive;

}
