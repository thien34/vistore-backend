package com.example.back_end.service.role.impl;

import com.example.back_end.entity.CustomerRole;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class CustomerRoleSpecification {

    private CustomerRoleSpecification() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Specification<CustomerRole> filterByNameAndActive(String name, Boolean active) {
        return (root, query, criteriaBuilder) -> {
            Specification<CustomerRole> specification = Specification.where(null);

            if (StringUtils.hasText(name)) {
                specification = specification.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder1.like(root1.get("name"), "%" + name + "%"));
            }

            if (active != null) {
                specification = specification.and((root1, query1, criteriaBuilder1) ->
                        criteriaBuilder1.equal(root1.get("active"), active));
            }

            return specification.toPredicate(root, query, criteriaBuilder);
        };
    }
}
