package com.example.back_end.entity;

import com.example.back_end.infrastructure.constant.OrderStatusType;
import com.example.back_end.infrastructure.constant.PaymentStatusType;
import com.example.back_end.infrastructure.constant.ShippingStatusType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
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
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"order\"")
public class Order extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_address_id")
    private Address billingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pickup_address_id")
    private Address pickupAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;

    @Column(name = "store_id")
    private Integer storeId;

    @Column(name = "order_guid")
    private UUID orderGuid;

    @Column(name = "pickup_in_store")
    private Boolean pickupInStore;

    @Enumerated
    @Column(name = "order_status_id")
    private OrderStatusType orderStatusId;

    @Enumerated
    @Column(name = "shipping_status_id")
    private ShippingStatusType shippingStatusId;

    @Enumerated
    @Column(name = "payment_status_id")
    private PaymentStatusType paymentStatusId;

    @Enumerated
    @Column(name = "payment_method_id")
    private PaymentStatusType paymentMethodId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "customer_language_id")
    private Language language;

    @Column(name = "customer_currency_code", length = Integer.MAX_VALUE)
    private String customerCurrencyCode;

    @Column(name = "currency_rate", precision = 18, scale = 2)
    private BigDecimal currencyRate;

    @Column(name = "order_subtotal", precision = 18, scale = 2)
    private BigDecimal orderSubtotal;

    @Column(name = "order_subtotal_discount", precision = 18, scale = 2)
    private BigDecimal orderSubtotalDiscount;

    @Column(name = "order_shipping", precision = 18, scale = 2)
    private BigDecimal orderShipping;

    @Column(name = "order_discount", precision = 18, scale = 2)
    private BigDecimal orderDiscount;

    @Column(name = "order_total", precision = 18, scale = 2)
    private BigDecimal orderTotal;

    @Column(name = "refunded_amount", precision = 18, scale = 2)
    private BigDecimal refundedAmount;

    @Column(name = "paid_date_utc")
    private Instant paidDateUtc;

    @Column(name = "deleted")
    private Boolean deleted;

}