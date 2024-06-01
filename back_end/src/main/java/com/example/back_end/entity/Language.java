package com.example.back_end.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "language", schema = "public", catalog = "store_db")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = true, length = 255)
    private String name;

    @Column(name = "_seo_code", nullable = true, length = 255)
    private String seoCode;

    @Column(name = "flag_image_file_name", nullable = true, length = 255)
    private String flagImageFileName;

    @ManyToOne
    @JoinColumn(name = "default_currency_id", nullable = true)
    private Currency defaultCurrency;

    @Column(name = "published", nullable = true)
    private Boolean published;

    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;

}
