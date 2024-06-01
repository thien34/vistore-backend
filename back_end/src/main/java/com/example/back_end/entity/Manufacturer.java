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
@Table(name = "manufacturer", schema = "public", catalog = "store_db")
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = true, length = 255)
    private String name;

    @Column(name = "description", nullable = true, length = 255)
    private String description;

    @ManyToOne
    @JoinColumn(name = "picture_id", nullable = true)
    private Picture picture;

    @Column(name = "page_size", nullable = true)
    private Integer pageSize;

    @Column(name = "price_range_filtering", nullable = true)
    private Boolean priceRangeFiltering;

    @Column(name = "published", nullable = true)
    private Boolean published;

    @Column(name = "deleted", nullable = true)
    private Boolean deleted;

    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;

}
