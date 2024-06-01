package com.example.back_end.entity;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "customer", schema = "public", catalog = "datn")
public class CustomerEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = true)
    private CurrencyEntity currency;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = true)
    private LanguageEntity language;

    @ManyToOne
    @JoinColumn(name = "billing_address_id", nullable = true)
    private AddressEntity billingAddress;

    @ManyToOne
    @JoinColumn(name = "shipping_address_id", nullable = true)
    private AddressEntity shippingAddress;

    @ManyToOne
    @JoinColumn(name = "home_address_id", nullable = true)
    private AddressEntity homeAddress;

    @ManyToOne
    @JoinColumn(name = "registered_in_store_id", nullable = true)
    private StoreEntity registeredInStore;

    @Basic
    @Column(name = "customer_guid", nullable = true)
    private String customerGuid;

    @Basic
    @Column(name = "username", nullable = true, length = 255)
    private String username;
    @Basic
    @Column(name = "email", nullable = true, length = 255)
    private String email;
    @Basic
    @Column(name = "first_name", nullable = true, length = 255)
    private String firstName;
    @Basic
    @Column(name = "last_name", nullable = true, length = 255)
    private String lastName;
    @Basic
    @Column(name = "gender", nullable = true)
    private Boolean gender;
    @Basic
    @Column(name = "phone", nullable = true, length = 255)
    private String phone;
    @Basic
    @Column(name = "custom_customer_attribute_json", nullable = true, length = 255)
    private String customCustomerAttributeJson;
    @Basic
    @Column(name = "date_of_birth", nullable = true)
    private LocalDateTime dateOfBirth;
    @Basic
    @Column(name = "has_shopping_cart_items", nullable = true)
    private Boolean hasShoppingCartItems;
    @Basic
    @Column(name = "require_re_login", nullable = true)
    private Boolean requireReLogin;
    @Basic
    @Column(name = "failed_login_attempts", nullable = true)
    private Integer failedLoginAttempts;
    @Basic
    @Column(name = "cannot_login_until_date_utc", nullable = true)
    private LocalDateTime  cannotLoginUntilDateUtc;
    @Basic
    @Column(name = "active", nullable = true)
    private Boolean active;
    @Basic
    @Column(name = "deleted", nullable = true)
    private Boolean deleted;
    @Basic
    @Column(name = "last_login_date_utc", nullable = true)
    private LocalDateTime  lastLoginDateUtc;
    @Basic
    @Column(name = "last_activity_date_utc", nullable = true)
    private LocalDateTime  lastActivityDateUtc;

}
