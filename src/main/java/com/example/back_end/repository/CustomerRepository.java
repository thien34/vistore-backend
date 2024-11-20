package com.example.back_end.repository;

import com.example.back_end.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByEmailAndDeletedFalse(String email);

    List<Customer> findByIdIn(List<Long> ids);

    @Query(value = "SELECT c FROM Customer c WHERE EXTRACT(MONTH FROM CAST(c.dateOfBirth AS DATE)) = EXTRACT(MONTH FROM CAST(:today AS DATE)) AND EXTRACT(DAY FROM CAST(c.dateOfBirth AS DATE)) = EXTRACT(DAY FROM CAST(:today AS DATE))")
    List<Customer> findAllByBirthday(@Param("today") LocalDate today);


}