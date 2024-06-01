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
@Table(name = "manufacturer", schema = "public", catalog = "datn")
public class ManufacturerEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Basic
    @Column(name = "description", nullable = true, length = 255)
    private String description;
    @ManyToOne
    @JoinColumn(name = "picture_id", nullable = true)
    private PictureEntity picture;
    @Basic
    @Column(name = "page_size", nullable = true)
    private Integer pageSize;
    @Basic
    @Column(name = "price_range_filtering", nullable = true)
    private Boolean priceRangeFiltering;
    @Basic
    @Column(name = "published", nullable = true)
    private Boolean published;
    @Basic
    @Column(name = "deleted", nullable = true)
    private Boolean deleted;
    @Basic
    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;

}
