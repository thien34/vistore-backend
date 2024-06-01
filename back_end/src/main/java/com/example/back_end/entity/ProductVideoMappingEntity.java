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
@Table(name = "product_video_mapping", schema = "public", catalog = "datn")
public class ProductVideoMappingEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "video_id", nullable = true)
    private VideoEntity video;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private ProductEntity product;
    @Basic
    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;


}
