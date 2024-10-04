package com.example.back_end.core.admin.product.specification;

import com.example.back_end.core.admin.product.payload.request.ProductFilter;
import com.example.back_end.entity.Category;
import com.example.back_end.entity.Product;
import com.example.back_end.entity.ProductCategoryMapping;
import com.example.back_end.entity.ProductManufacturerMapping;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> buildWhere(ProductFilter productFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (productFilter.getName() != null && !productFilter.getName().isEmpty()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" + productFilter.getName().toLowerCase() + "%"
                        )
                );
            }

            if (productFilter.getCategoryId() != null) {
                Join<Product, ProductCategoryMapping> categoryJoin = root.join("productCategoryMappings", JoinType.INNER);
                Join<ProductCategoryMapping, Category> categoryEntity = categoryJoin.join("category", JoinType.INNER);

                if (Boolean.TRUE.equals(productFilter.getSearchSubCategory())) {

                    Subquery<Long> subQuery = query.subquery(Long.class);
                    Root<Category> subRoot = subQuery.from(Category.class);

                    subQuery.select(subRoot.get("id"))
                            .where(criteriaBuilder.or(
                                    criteriaBuilder.equal(subRoot.get("id"), productFilter.getCategoryId()),
                                    criteriaBuilder.equal(subRoot.get("categoryParent").get("id"), productFilter.getCategoryId())
                            ));

                    predicates.add(categoryEntity.get("id").in(subQuery));
                } else {
                    predicates.add(criteriaBuilder.equal(categoryEntity.get("id"), productFilter.getCategoryId()));
                }
            }

            if (productFilter.getManufacturerId() != null) {
                Join<Product, ProductManufacturerMapping> manufacturerJoin = root.join("productManufacturerMappings", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(manufacturerJoin.get("manufacturer").get("id"), productFilter.getManufacturerId()));
            }

            if (productFilter.getPublished() != null) {
                predicates.add(criteriaBuilder.equal(root.get("published"), productFilter.getPublished()));
            }

            if (productFilter.getSku() != null && !productFilter.getSku().isEmpty()) {
                predicates.add(
                        criteriaBuilder.equal(
                                criteriaBuilder.lower(root.get("sku")),
                                productFilter.getSku().toLowerCase()
                        )
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}