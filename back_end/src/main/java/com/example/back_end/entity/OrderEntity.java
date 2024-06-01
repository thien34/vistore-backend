package com.example.back_end.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


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
@Table(name = "order", schema = "public", catalog = "datn")
public class OrderEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "billing_address_id", nullable = true)
    private AddressEntity billingAddress;

    @ManyToOne
    @JoinColumn(name = "pickup_address_id", nullable = true)
    private AddressEntity pickupAddress;

    @ManyToOne
    @JoinColumn(name = "shipping_address_id", nullable = true)
    private AddressEntity shippingAddress;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = true)
    private StoreEntity store;

    @Basic
    @Column(name = "order_guid", nullable = true)
    private Object orderGuid;
    @Basic
    @Column(name = "pickup_in_store", nullable = true)
    private Boolean pickupInStore;
    @Basic
    @Column(name = "order_status_id", nullable = true)
    private Integer orderStatusId;
    @Basic
    @Column(name = "shipping_status_id", nullable = true)
    private Integer shippingStatusId;
    @Basic
    @Column(name = "payment_status_id", nullable = true)
    private Integer paymentStatusId;
    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = true)
    private PaymentMethodEntity paymentMethod;

    @ManyToOne
    @JoinColumn(name = "customer_language_id", nullable = true)
    private LanguageEntity customerLanguage;

    @Column(name = "customer_currency_code", nullable = true, length = 255)
    private String customerCurrencyCode;
    @Basic
    @Column(name = "currency_rate", nullable = true, precision = 2)
    private BigDecimal currencyRate;
    @Basic
    @Column(name = "order_subtotal", nullable = true, precision = 2)
    private BigDecimal orderSubtotal;
    @Basic
    @Column(name = "order_subtotal_discount", nullable = true, precision = 2)
    private BigDecimal orderSubtotalDiscount;
    @Basic
    @Column(name = "order_shipping", nullable = true, precision = 2)
    private BigDecimal orderShipping;
    @Basic
    @Column(name = "order_discount", nullable = true, precision = 2)
    private BigDecimal orderDiscount;
    @Basic
    @Column(name = "order_total", nullable = true, precision = 2)
    private BigDecimal orderTotal;
    @Basic
    @Column(name = "refunded_amount", nullable = true, precision = 2)
    private BigDecimal refundedAmount;
    @Basic
    @Column(name = "paid_date_utc", nullable = true)
    private LocalDateTime paidDateUtc;
    @Basic
    @Column(name = "deleted", nullable = true)
    private Boolean deleted;


}
