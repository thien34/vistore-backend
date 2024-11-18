package com.example.back_end.repository;

import com.example.back_end.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {

    @Query("SELECT a FROM Address a WHERE a.customer.id = :customerId " +
            "AND a.addressName = :addressName " +
            "AND a.phoneNumber = :phoneNumber " +
            "AND a.district.code = :districtId " +
            "AND a.province.code = :provinceId " +
            "AND a.ward.code = :wardId")
    Address findByCustomerIdAndFullAddress(@Param("customerId") Long customerId,
                                           @Param("addressName") String addressName,
                                           @Param("phoneNumber") String phoneNumber,
                                           @Param("districtId") String districtId,
                                           @Param("provinceId") String provinceId,
                                           @Param("wardId") String wardId);

    List<Address> findByCustomerId(Long customerId);
}

