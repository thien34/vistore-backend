package com.example.back_end.entity;

import com.example.back_end.infrastructure.constant.GenderType;
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

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class Customer extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "language_id")
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "billing_address_id")
    private Address billingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "home_address_id")
    private Address homeAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "registered_in_store_id")
    private Store registeredInStore;

    @Column(name = "customer_guid")
    private UUID customerGuid;

    @Column(name = "username", length = Integer.MAX_VALUE)
    private String username;

    @Column(name = "email", length = Integer.MAX_VALUE)
    private String email;

    @Column(name = "first_name", length = Integer.MAX_VALUE)
    private String firstName;

    @Column(name = "last_name", length = Integer.MAX_VALUE)
    private String lastName;

    @Enumerated
    @Column(name = "gender")
    private GenderType gender;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "custom_customer_attribute_json", length = Integer.MAX_VALUE)
    private String customCustomerAttributeJson;

    @Column(name = "date_of_birth")
    private Instant dateOfBirth;

    @Column(name = "has_shopping_cart_items")
    private Boolean hasShoppingCartItems;

    @Column(name = "require_re_login")
    private Boolean requireReLogin;

    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts;

    @Column(name = "cannot_login_until_date_utc")
    private Instant cannotLoginUntilDateUtc;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "last_login_date_utc")
    private Instant lastLoginDateUtc;

    @Column(name = "last_activity_date_utc")
    private Instant lastActivityDateUtc;

}