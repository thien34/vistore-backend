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
@Table(name = "product_attribute_value_picture", schema = "public", catalog = "datn")
public class ProductAttributeValuePictureEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_attribute_value_id", nullable = true)
    private ProductAttributeValueEntity productAttributeValue;

    @ManyToOne
    @JoinColumn(name = "picture_id", nullable = true)
    private PictureEntity picture;

}
