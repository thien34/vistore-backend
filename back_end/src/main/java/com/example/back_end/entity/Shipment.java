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

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "shipment", schema = "public", catalog = "store_db")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "order_id", nullable = true)
    private Integer orderId;

    @Column(name = "tracking_number", nullable = true, length = 255)
    private String trackingNumber;

    @Column(name = "total_weight", nullable = true, precision = 2)
    private BigDecimal totalWeight;

    @Column(name = "shipped_date_utc", nullable = true)
    private LocalDateTime shippedDateUtc;

    @Column(name = "delivery_date_utc", nullable = true)
    private LocalDateTime deliveryDateUtc;

    @Column(name = "ready_for_pickup_date_utc", nullable = true)
    private LocalDateTime readyForPickupDateUtc;

    @Column(name = "admin_comment", nullable = true, length = 255)
    private String adminComment;

}
