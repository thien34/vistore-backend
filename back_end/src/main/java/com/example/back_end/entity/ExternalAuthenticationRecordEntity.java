package com.example.back_end.entity;


import jakarta.persistence.*;

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
@Table(name = "external_authentication_record", schema = "public", catalog = "datn")
public class ExternalAuthenticationRecordEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private CustomerEntity customer;
    @Basic
    @Column(name = "email", nullable = true, length = 255)
    private String email;
    @Basic
    @Column(name = "external_identifier", nullable = true, length = 255)
    private String externalIdentifier;
    @Basic
    @Column(name = "external_display_identifier", nullable = true, length = 255)
    private String externalDisplayIdentifier;
    @Basic
    @Column(name = "o_auth_token", nullable = true, length = 255)
    private String oAuthToken;
    @Basic
    @Column(name = "o_auth_access_token", nullable = true, length = 255)
    private String oAuthAccessToken;
    @Basic
    @Column(name = "provider_system_name", nullable = true, length = 255)
    private String providerSystemName;

}
