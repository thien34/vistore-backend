package com.example.back_end.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "address")
public class Address extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", length = Integer.MAX_VALUE)
    private String firstName;

    @Column(name = "last_name", length = Integer.MAX_VALUE)
    private String lastName;

    @Column(name = "email", length = Integer.MAX_VALUE)
    private String email;

    @Column(name = "company", length = Integer.MAX_VALUE)
    private String company;

    @Column(name = "county", length = Integer.MAX_VALUE)
    private String county;

    @Column(name = "city", length = Integer.MAX_VALUE)
    private String city;

    @Column(name = "address_name", length = Integer.MAX_VALUE)
    private String addressName;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "custom_attributes", length = Integer.MAX_VALUE)
    private String customAttributes;

}