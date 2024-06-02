package com.example.back_end.entity;

import com.example.back_end.infrastructure.constant.OrderStatusType;
import com.example.back_end.infrastructure.constant.PaymentStatusType;
import com.example.back_end.infrastructure.constant.ShippingStatusType;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order", schema = "public", catalog = "store_db")

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "billing_address_id", nullable = true)
    private Address billingAddress;

    @ManyToOne
    @JoinColumn(name = "pickup_address_id", nullable = true)
    private Address pickupAddress;

    @ManyToOne
    @JoinColumn(name = "shipping_address_id", nullable = true)
    private Address shippingAddress;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = true)
    private Store store;

    @Column(name = "order_guid", nullable = true)
    private String orderGuid;

    @Column(name = "pickup_in_store", nullable = true)
    private Boolean pickupInStore;

    @Column(name = "order_status_id", nullable = true)
    private OrderStatusType orderStatus;

    @Column(name = "shipping_status_id", nullable = true)
    private ShippingStatusType shippingStatus;

    @Column(name = "payment_status_id", nullable = true)
    private PaymentStatusType paymentStatus;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = true)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "customer_language_id", nullable = true)
    private Language customerLanguage;

    @Column(name = "customer_currency_code", nullable = true, length = 255)
    private String customerCurrencyCode;

    @Column(name = "currency_rate", nullable = true, precision = 2)
    private BigDecimal currencyRate;

    @Column(name = "order_subtotal", nullable = true, precision = 2)
    private BigDecimal orderSubtotal;

    @Column(name = "order_subtotal_discount", nullable = true, precision = 2)
    private BigDecimal orderSubtotalDiscount;

    @Column(name = "order_shipping", nullable = true, precision = 2)
    private BigDecimal orderShipping;

    @Column(name = "order_discount", nullable = true, precision = 2)
    private BigDecimal orderDiscount;

    @Column(name = "order_total", nullable = true, precision = 2)
    private BigDecimal orderTotal;

    @Column(name = "refunded_amount", nullable = true, precision = 2)
    private BigDecimal refundedAmount;

    @Column(name = "paid_date_utc", nullable = true)
    private LocalDateTime paidDateUtc;

    @Column(name = "deleted", nullable = true)
    private Boolean deleted;

}
