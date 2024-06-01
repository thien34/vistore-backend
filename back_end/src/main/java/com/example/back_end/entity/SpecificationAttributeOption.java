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
@Builder
@NoArgsConstructor
@Entity
@Table(name = "specification_attribute_option", schema = "public", catalog = "store_db")
public class SpecificationAttributeOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "specification_attribute_id", nullable = true)
    private SpecificationAttribute specificationAttribute;

    @Column(name = "name", nullable = true, length = 255)
    private String name;

    @Column(name = "color_squares_rgb", nullable = true, length = 255)
    private String colorSquaresRgb;

    @Column(name = "display_order", nullable = true)
    private Integer displayOrder;

}
