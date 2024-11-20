package com.example.back_end.core.admin.discount.utils;

import com.example.back_end.entity.Discount;
import com.example.back_end.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DiscountStatusUpdater {
    private final DiscountRepository discountRepository;

    @Scheduled(cron = "0 0 * * * *")
    public void updateDiscountStatuses() {
        Instant now = Instant.now();
        List<Discount> discounts = discountRepository.findAll();
        for (Discount discount : discounts) {
            String status = getStatus(discount, now);
            if (!status.equals(discount.getStatus())) {
                discount.setStatus(status);
                discountRepository.save(discount);
            }
        }
    }

    private String getStatus(Discount discount, Instant now) {
        if (discount.getStartDateUtc() != null && now.isBefore(discount.getStartDateUtc())) {
            return "UPCOMING";
        } else if (discount.getEndDateUtc() != null && now.isAfter(discount.getEndDateUtc())) {
            return "EXPIRED";
        } else {
            return "ACTIVE";
        }
    }
}
