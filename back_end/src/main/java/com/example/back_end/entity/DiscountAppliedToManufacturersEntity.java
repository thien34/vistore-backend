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
@Table(name = "discount_applied_to_manufacturers", schema = "public", catalog = "datn")
public class DiscountAppliedToManufacturersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "discount_id", nullable = true)
    private DiscountEntity discount;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id", nullable = true)
    private ManufacturerEntity manufacturer;

}
