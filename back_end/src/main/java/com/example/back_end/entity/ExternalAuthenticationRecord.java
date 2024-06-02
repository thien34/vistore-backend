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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "external_authentication_record", schema = "public", catalog = "store_db")
public class ExternalAuthenticationRecord {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;

    @Column(name = "email", nullable = true, length = 255)
    private String email;

    @Column(name = "external_identifier", nullable = true, length = 255)
    private String externalIdentifier;

    @Column(name = "external_display_identifier", nullable = true, length = 255)
    private String externalDisplayIdentifier;

    @Column(name = "o_auth_token", nullable = true, length = 255)
    private String oAuthToken;

    @Column(name = "o_auth_access_token", nullable = true, length = 255)
    private String oAuthAccessToken;

    @Column(name = "provider_system_name", nullable = true, length = 255)
    private String providerSystemName;

}
