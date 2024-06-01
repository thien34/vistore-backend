package com.example.back_end.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
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
@Table(name = "currency", schema = "public", catalog = "datn")
public class CurrencyEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "rounding_type_id", nullable = true)
    private Integer roundingTypeId;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Basic
    @Column(name = "currency_code", nullable = true, length = 255)
    private String currencyCode;
    @Basic
    @Column(name = "display_locale", nullable = true, length = 255)
    private String displayLocale;
    @Basic
    @Column(name = "rate", nullable = true, precision = 2)
    private BigDecimal rate;
    @Basic
    @Column(name = "published", nullable = true)
    private Boolean published;
    @Basic
    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;

}
