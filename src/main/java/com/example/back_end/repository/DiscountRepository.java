package com.example.back_end.repository;

import com.example.back_end.entity.Discount;
import com.example.back_end.infrastructure.constant.DiscountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

    @Query("SELECT d FROM Discount d WHERE d.couponCode = :couponCode AND (d.status = 'ACTIVE' OR d.status = 'UPCOMING')")
    Optional<Discount> findActiveVoucherByCouponCode(@Param("couponCode") String couponCode);


    boolean existsByNameAndIdNot(String name, Long id);

    boolean existsByCouponCode(String couponCode);

    @Query("""
        SELECT d FROM Discount d WHERE
        (:name IS NULL OR d.name LIKE %:name%) AND
        (:couponCode IS NULL OR d.couponCode LIKE %:couponCode%) AND
        (:discountTypeId IS NULL OR d.discountTypeId = :discountTypeId) AND
        ((cast(:startDate as date) IS NULL) OR d.startDateUtc >= :startDate) AND
        ((cast(:endDate as date) IS NULL) OR d.endDateUtc <= :endDate) AND
        (:isPublished IS NULL OR d.isPublished = :isPublished) AND
        (:status IS NULL OR d.status = :status)
        ORDER BY d.createdDate desc
        """)
    List<Discount> searchDiscountsNoPage(@Param("name") String name,
                                         @Param("couponCode") String couponCode,
                                         @Param("discountTypeId") DiscountType discountTypeId,
                                         @Param("startDate") Instant startDate,
                                         @Param("endDate") Instant endDate,
                                         @Param("status") String status,
                                         @Param("isPublished") Boolean isPublished);

}