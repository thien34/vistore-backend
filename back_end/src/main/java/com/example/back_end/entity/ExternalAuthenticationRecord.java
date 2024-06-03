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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "external_authentication_record")
public class ExternalAuthenticationRecord extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "email", length = Integer.MAX_VALUE)
    private String email;

    @Column(name = "external_identifier", length = Integer.MAX_VALUE)
    private String externalIdentifier;

    @Column(name = "external_display_identifier", length = Integer.MAX_VALUE)
    private String externalDisplayIdentifier;

    @Column(name = "o_auth_token", length = Integer.MAX_VALUE)
    private String oAuthToken;

    @Column(name = "o_auth_access_token", length = Integer.MAX_VALUE)
    private String oAuthAccessToken;

    @Column(name = "provider_system_name", length = Integer.MAX_VALUE)
    private String providerSystemName;

}