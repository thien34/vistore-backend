package com.example.back_end.entity;

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

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customer", schema = "public", catalog = "store_db")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = true)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = true)
    private Language language;

    @ManyToOne
    @JoinColumn(name = "billing_address_id", nullable = true)
    private Address billingAddress;

    @ManyToOne
    @JoinColumn(name = "shipping_address_id", nullable = true)
    private Address shippingAddress;

    @ManyToOne
    @JoinColumn(name = "home_address_id", nullable = true)
    private Address homeAddress;

    @ManyToOne
    @JoinColumn(name = "registered_in_store_id", nullable = true)
    private Store registeredInStore;

    @Column(name = "customer_guid", nullable = true)
    private String customerGuid;

    @Column(name = "username", nullable = true, length = 255)
    private String username;

    @Column(name = "email", nullable = true, length = 255)
    private String email;

    @Column(name = "first_name", nullable = true, length = 255)
    private String firstName;

    @Column(name = "last_name", nullable = true, length = 255)
    private String lastName;

    @Column(name = "gender", nullable = true)
    private Boolean gender;

    @Column(name = "phone", nullable = true, length = 255)
    private String phone;

    @Column(name = "custom_customer_attribute_json", nullable = true, length = 255)
    private String customCustomerAttributeJson;

    @Column(name = "date_of_birth", nullable = true)
    private LocalDateTime dateOfBirth;

    @Column(name = "has_shopping_cart_items", nullable = true)
    private Boolean hasShoppingCartItems;

    @Column(name = "require_re_login", nullable = true)
    private Boolean requireReLogin;

    @Column(name = "failed_login_attempts", nullable = true)
    private Integer failedLoginAttempts;

    @Column(name = "cannot_login_until_date_utc", nullable = true)
    private LocalDateTime cannotLoginUntilDateUtc;

    @Column(name = "active", nullable = true)
    private Boolean active;

    @Column(name = "deleted", nullable = true)
    private Boolean deleted;

    @Column(name = "last_login_date_utc", nullable = true)
    private LocalDateTime lastLoginDateUtc;

    @Column(name = "last_activity_date_utc", nullable = true)
    private LocalDateTime lastActivityDateUtc;

}
