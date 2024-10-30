package com.example.back_end.service.address.impl;

import com.example.back_end.entity.Address;
import com.example.back_end.entity.Customer;
import org.springframework.data.jpa.domain.Specification;

public class AddressSpecification {

    public static Specification<Address> hasCustomerId(Customer customer) {

        return (root, query, criteriaBuilder) -> {
            if (customer == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get("customer"), customer);
        };
    }
}
