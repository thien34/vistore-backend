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


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "currency", schema = "public", catalog = "store_db")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "rounding_type_id", nullable = true)
    private Integer roundingTypeId;

    @Column(name = "name", nullable = true, length = 255)
    private String name;

    @Column(name = "currency_code", nullable = true, length = 255)
    private String currencyCode;

    @Column(name = "display_locale", nullable = true, length = 255)
    private String displayLocale;

    @Column(name = "rate", nullable = true, precision = 2)
    private BigDecimal rate;

    @Column(name = "published", nullable = true)
    private Boolean published;

    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;

}
