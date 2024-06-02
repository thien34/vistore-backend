package com.example.back_end.entity;

import com.example.back_end.infrastructure.constant.RoundingType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "currency")
public class Currency extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated
    @Column(name = "rounding_type_id")
    private RoundingType roundingTypeId;

    @Column(name = "name", length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "currency_code", length = Integer.MAX_VALUE)
    private String currencyCode;

    @Column(name = "display_locale", length = Integer.MAX_VALUE)
    private String displayLocale;

    @Column(name = "rate", precision = 18, scale = 2)
    private BigDecimal rate;

    @Column(name = "published")
    private Boolean published;

    @Column(name = "display_order")
    private Integer displayOrder;

}