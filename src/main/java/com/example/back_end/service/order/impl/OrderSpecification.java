package com.example.back_end.service.order.impl;

import com.example.back_end.core.admin.order.payload.OrderFilter;
import com.example.back_end.entity.Order;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderSpecification {
    public static Specification<Order> filterBy(OrderFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Optional.ofNullable(filter.getName())
                    .map(String::toLowerCase).ifPresent(nameFilter -> {
                        Path<String> firstNamePath = root.get("customer").get("firstName");
                        Path<String> lastNamePath = root.get("customer").get("lastName");
                        Expression<String> customerName = criteriaBuilder.concat(
                                criteriaBuilder.concat(firstNamePath, " "),
                                lastNamePath
                        );
                        predicates.add(criteriaBuilder.like(
                                criteriaBuilder.lower(customerName),
                                "%" + nameFilter + "%"
                        ));
                    });
            if (filter.getStartAmount() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("orderTotal"), filter.getStartAmount()));
            }
            if (filter.getEndAmount() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("orderTotal"), filter.getEndAmount()));
            }
            if (filter.getPaymentMode() != null) {
                predicates.add(criteriaBuilder.equal(root.get("paymentMethodId"), filter.getPaymentMode()));
            }
            if (filter.getStartDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), filter.getStartDate()));
            }
            if (filter.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("orderStatusId"), filter.getStatus()));
            }
            if (filter.getPaymentStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("paymentStatusId"), filter.getPaymentStatus()));
            }
            if (filter.getEndDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), filter.getEndDate()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
