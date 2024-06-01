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
@Table(name = "shipment_item", schema = "public", catalog = "datn")
public class ShipmentItemEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "shipment_id", nullable = true)
    private Integer shipmentId;
    @Basic
    @Column(name = "order_item_id", nullable = true)
    private Integer orderItemId;
    @Basic
    @Column(name = "quantity", nullable = true)
    private Integer quantity;

}
