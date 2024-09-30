package com.example.back_end.repository;

import com.example.back_end.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("""
            SELECT a FROM Address a JOIN CustomerAddressMapping cam ON a.id = cam.address.id
            WHERE cam.customer.id = :customerId
            """)
    Page<Address> findAllByCustomerId(@Param("customerId") Long customerId, Pageable pageable);
}
