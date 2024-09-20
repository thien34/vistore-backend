package com.example.back_end.entity;

import com.example.back_end.infrastructure.constant.GenderType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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