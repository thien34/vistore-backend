package com.example.back_end.core.admin.discount.service.impl;

import com.example.back_end.core.admin.discount.mapper.DiscountMapper;
import com.example.back_end.core.admin.discount.payload.request.DiscountFilterRequest;
import com.example.back_end.core.admin.discount.payload.request.DiscountRequest;
import com.example.back_end.core.admin.discount.payload.response.DiscountFullResponse;
import com.example.back_end.core.admin.discount.payload.response.DiscountResponse;
import com.example.back_end.core.admin.discount.service.DiscountService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.Discount;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.constant.SortType;
import com.example.back_end.infrastructure.exception.ExistsByNameException;
import com.example.back_end.infrastructure.exception.InvalidDataException;
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.DiscountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DiscountServiceImpl implements DiscountService {
    DiscountRepository discountRepository;
    DiscountMapper discountMapper;

    @Override
    public PageResponse<List<DiscountResponse>> getAllDiscounts(DiscountFilterRequest filterRequest) {
        Pageable pageable = PageUtils.createPageable(
                filterRequest.getPageNo() != null ? filterRequest.getPageNo() : 1,
                filterRequest.getPageSize() != null ? filterRequest.getPageSize() : 6,
                "id",
                SortType.DESC.getValue()
        );

        Page<Discount> discountPage = discountRepository.searchDiscounts(
                filterRequest.getName(),
                filterRequest.getCouponCode(),
                filterRequest.getDiscountTypeId(),
                filterRequest.getStartDate(),
                filterRequest.getEndDate(),
                filterRequest.getIsActive(),
                pageable
        );

        List<DiscountResponse> discountResponseList = discountPage.stream()
                .map(discountMapper::toResponse)
                .toList();

        return PageResponse.<List<DiscountResponse>>builder()
                .page(discountPage.getNumber() + 1)
                .size(discountPage.getSize())
                .totalPage(discountPage.getTotalPages())
                .items(discountResponseList)
                .build();
    }

    @Override
    @Transactional
    public void createDiscount(DiscountRequest discountRequest) {
        String trimmedName = discountRequest.getName().trim().replaceAll("\\s+", " ");

        if (discountRepository.existsByName(trimmedName))
            throw new ExistsByNameException(ErrorCode.DISCOUNT_WITH_THIS_NAME_ALREADY_EXISTS.getMessage());

        Discount discount = discountMapper.toEntity(discountRequest);

        validateDiscount(discount);

        if (discount.getDiscountPercentage() != null && discount.getMaxDiscountAmount() == null) {
            BigDecimal maxDiscountAmount = discount.getDiscountPercentage().multiply(new BigDecimal(100));
            discount.setMaxDiscountAmount(maxDiscountAmount);
        }
        Discount savedDiscount = discountRepository.save(discount);
        discountMapper.toResponse(savedDiscount);

    }

    private void validateDiscount(Discount discount) {
        validateDiscountAmount(discount);
        validateCouponCodeRequirement(discount);
        validateDate(discount.getStartDateUtc(), discount.getEndDateUtc());
    }

    private void validateDiscountAmount(Discount discount) {
        Boolean usePercentage = discount.getUsePercentage();
        BigDecimal discountPercentage = discount.getDiscountPercentage();
        BigDecimal discountAmount = discount.getDiscountAmount();

        if (usePercentage != null && usePercentage) {
            if (discountAmount != null) {
                throw new IllegalArgumentException(
                        "When 'usePercentage' is true, 'discountAmount' should not be provided.");
            }
        } else {
            if (discountPercentage != null) {
                throw new IllegalArgumentException(
                        "When 'usePercentage' is false, 'discountPercentage' should not be provided.");
            }
        }
    }

    private void validateCouponCodeRequirement(Discount discount) {
        Predicate<Discount> requiresCouponCodeCheck = d ->
                d.getRequiresCouponCode() != null && d.getRequiresCouponCode();

        Runnable couponCodeValidation = () -> {
            if (discount.getCouponCode() == null || discount.getCouponCode().trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "If 'requiresCouponCode' is true, 'couponCode' cannot be null or empty.");
            }
        };

        if (requiresCouponCodeCheck.test(discount)) {
            couponCodeValidation.run();
        }
    }

    private void validateDate(Instant startDate, Instant endDate) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new InvalidDataException("The 'startDateUtc' must be before 'endDateUtc'.");
        }
    }

    @Override
    @Transactional
    public void updateDiscount(Long id, DiscountRequest discountRequest) {

        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Discount not found with ID: " + id));

        if (discountRepository.existsByNameAndIdNot(discountRequest.getName(), id))
            throw new ExistsByNameException("Discount with name '" + discountRequest.getName() + "' already exists.");

        discountMapper.updateEntityFromRequest(discountRequest, discount);

        validateDiscount(discount);

        Discount updatedDiscount = discountRepository.save(discount);

        discountMapper.toResponse(updatedDiscount);

    }

    @Override
    public DiscountFullResponse getDiscountById(Long id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Discount not found with ID: " + id));
        return discountMapper.toGetOneResponse(discount);
    }

    @Override
    public void deleteDiscount(Long id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DISCOUNT_NOT_FOUND.getMessage()));

        discountRepository.delete(discount);
    }

}
