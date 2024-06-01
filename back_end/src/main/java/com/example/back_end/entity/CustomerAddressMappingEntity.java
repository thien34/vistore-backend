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
@Table(name = "customer_address_mapping", schema = "public", catalog = "datn")
public class CustomerAddressMappingEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "address_id", nullable = true)
    private AddressEntity address;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private CustomerEntity customer;

}
