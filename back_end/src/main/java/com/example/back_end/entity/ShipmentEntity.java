package com.example.back_end.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
@Table(name = "shipment", schema = "public", catalog = "datn")
public class ShipmentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "order_id", nullable = true)
    private Integer orderId;
    @Basic
    @Column(name = "tracking_number", nullable = true, length = 255)
    private String trackingNumber;
    @Basic
    @Column(name = "total_weight", nullable = true, precision = 2)
    private BigDecimal totalWeight;
    @Basic
    @Column(name = "shipped_date_utc", nullable = true)
    private LocalDateTime shippedDateUtc;
    @Basic
    @Column(name = "delivery_date_utc", nullable = true)
    private LocalDateTime  deliveryDateUtc;
    @Basic
    @Column(name = "ready_for_pickup_date_utc", nullable = true)
    private LocalDateTime  readyForPickupDateUtc;
    @Basic
    @Column(name = "admin_comment", nullable = true, length = 255)
    private String adminComment;


}
