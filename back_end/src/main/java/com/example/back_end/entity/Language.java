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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "language")
public class Language extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "unique_seo_code", length = Integer.MAX_VALUE)
    private String uniqueSeoCode;

    @Column(name = "flag_image_file_name", length = Integer.MAX_VALUE)
    private String flagImageFileName;

    @Column(name = "default_currency_id")
    private Integer defaultCurrencyId;

    @Column(name = "published")
    private Boolean published;

    @Column(name = "display_order")
    private Integer displayOrder;

}