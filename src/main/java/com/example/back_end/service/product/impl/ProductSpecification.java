package com.example.back_end.service.product.impl;

import com.example.back_end.core.admin.product.payload.request.ProductFilter;
import com.example.back_end.entity.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> filterBy(ProductFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getName() != null && !filter.getName().isEmpty()) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" + filter.getName().toLowerCase() + "%"
                        ),
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("fullName")),
                                "%" + filter.getName().toLowerCase() + "%"
                        )
                ));
            }

            if (filter.getCategoryId() != null && filter.getCategoryId() != -1) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), filter.getCategoryId()));
            }
            if (filter.getManufacturerId() != null && filter.getManufacturerId() != -1) {
                predicates.add(criteriaBuilder.equal(root.get("manufacturer").get("id"), filter.getManufacturerId()));
            }
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
