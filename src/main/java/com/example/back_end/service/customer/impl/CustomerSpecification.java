package com.example.back_end.service.customer.impl;

import com.example.back_end.core.admin.customer.payload.request.CustomerSearchRequest;
import com.example.back_end.entity.Customer;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class CustomerSpecification {

    private CustomerSpecification() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Specification<Customer> filterCustomers(CustomerSearchRequest criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            addPredicateIfNotNull(predicates,
                    r -> cb.like(cb
                            .lower(root.get("email")), "%" + criteria.getEmail().toLowerCase() + "%"),
                    criteria.getEmail()
            );

            addPredicateIfNotNull(predicates,
                    r -> cb.like(cb
                            .lower(root.get("firstName")), "%" + criteria.getFirstName().toLowerCase() + "%"),
                    criteria.getFirstName()
            );

            addPredicateIfNotNull(predicates,
                    r -> cb.like(cb
                            .lower(root.get("lastName")), "%" + criteria.getLastName().toLowerCase() + "%"),
                    criteria.getLastName()
            );

            addPredicateIfNotNull(predicates,
                    r -> cb.equal(root.get("dateOfBirth"), criteria.getDateOfBirth()),
                    criteria.getDateOfBirth()
            );

            if (criteria.getRegistrationDateFrom() != null) {
                LocalDate fromDate = criteria.getRegistrationDateFrom().atZone(ZoneId.systemDefault()).toLocalDate();
                Date startDate = Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdDate"), startDate));
            }
            if (criteria.getRegistrationDateTo() != null) {
                LocalDate toDate = criteria.getRegistrationDateTo().atZone(ZoneId.systemDefault()).toLocalDate();
                Date endDate = Date.from(toDate
                        .plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
                predicates.add(cb.lessThan(root.get("createdDate"), endDate));
            }

            addPredicateIfNotNull(predicates,
                    r -> cb.greaterThanOrEqualTo(root.get("lastActivityDateUtc"), criteria.getLastActivityFrom()),
                    criteria.getLastActivityFrom()
            );

            addPredicateIfNotNull(predicates,
                    r -> cb.lessThanOrEqualTo(root.get("lastActivityDateUtc"), criteria.getLastActivityTo()),
                    criteria.getLastActivityTo()
            );

            if (criteria.getCustomerRoles() != null && !criteria.getCustomerRoles().isEmpty()) {
                predicates.add(root
                        .join("customerRoles")
                        .get("customerRole").get("id").in(criteria.getCustomerRoles()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addPredicateIfNotNull(
            List<Predicate> predicates, Function<Root<Customer>, Predicate> predicateFunction, Object value) {
        if (value != null && !(value instanceof String string && !StringUtils.hasText(string))) {
            predicates.add(predicateFunction.apply(null));
        }
    }

}
