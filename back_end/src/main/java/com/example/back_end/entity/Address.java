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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "address", schema = "public", catalog = "store_db")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = true, length = 255)
    private String firstName;

    @Column(name = "last_name", nullable = true, length = 255)
    private String lastName;

    @Column(name = "email", nullable = true, length = 255)
    private String email;

    @Column(name = "company", nullable = true, length = 255)
    private String company;

    @Column(name = "county", nullable = true, length = 255)
    private String county;

    @Column(name = "city", nullable = true, length = 255)
    private String city;

    @Column(name = "address", nullable = true, length = 255)
    private String address;

    @Column(name = "phone_number", nullable = true, length = 255)
    private String phoneNumber;

    @Column(name = "custom_attributes", nullable = true, length = 255)
    private String customAttributes;

}
