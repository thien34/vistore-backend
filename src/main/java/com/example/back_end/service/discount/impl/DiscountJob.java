package com.example.back_end.service.discount.impl;


import com.example.back_end.entity.Discount;
import com.example.back_end.entity.DiscountAppliedToProduct;
import com.example.back_end.entity.Product;
import com.example.back_end.repository.DiscountRepository;
import com.example.back_end.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DiscountJob {

    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;

    @Scheduled(fixedRate = 1000)
    public void checkAndUpdateExpiredDiscounts() {
        Instant now = Instant.now();
        List<Discount> expiredDiscounts = discountRepository.findAll()
                .parallelStream()
                .filter(discount -> discount.getEndDateUtc() != null)
                .filter(discount -> discount.getDiscountTypeId().getId() == 1)
                .filter(discount -> discount.getEndDateUtc().isBefore(now))
                .filter(discount -> "ACTIVE".equals(discount.getStatus()))
                .toList();


        for (Discount discount : expiredDiscounts) {
            updateDiscountForProducts(discount);
            discount.setDiscountPercentage(null);
            discount.setStatus("EXPIRED");
            discountRepository.save(discount);
        }
    }

    private void updateDiscountForProducts(Discount discount) {
        for (DiscountAppliedToProduct appliedToProduct : discount.getAppliedToProducts()) {
            Product product = appliedToProduct.getProduct();
            if (discount.getUsePercentage() != null && discount.getUsePercentage()) {
                product.setDiscountPrice(null);
            }
            productRepository.save(product);
        }
    }
}