package com.example.back_end.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shipment")
public class Shipment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "tracking_number", length = Integer.MAX_VALUE)
    private String trackingNumber;

    @Column(name = "total_weight", precision = 18, scale = 2)
    private BigDecimal totalWeight;

    @Column(name = "shipped_date_utc")
    private Instant shippedDateUtc;

    @Column(name = "delivery_date_utc")
    private Instant deliveryDateUtc;

    @Column(name = "ready_for_pickup_date_utc")
    private Instant readyForPickupDateUtc;

    @Column(name = "admin_comment", length = Integer.MAX_VALUE)
    private String adminComment;

}