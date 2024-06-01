package com.example.back_end.entity;


import jakarta.persistence.*;

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
@Table(name = "language", schema = "public", catalog = "datn")
public class LanguageEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Basic
    @Column(name = "_seo_code", nullable = true, length = 255)
    private String seoCode;
    @Basic
    @Column(name = "flag_image_file_name", nullable = true, length = 255)
    private String flagImageFileName;
    @ManyToOne
    @JoinColumn(name = "default_currency_id", nullable = true)
    private CurrencyEntity defaultCurrency;
    @Basic
    @Column(name = "published", nullable = true)
    private Boolean published;
    @Basic
    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;

}
