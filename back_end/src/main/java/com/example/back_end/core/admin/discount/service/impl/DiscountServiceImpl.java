package com.example.back_end.core.admin.discount.service.impl;

import com.example.back_end.core.admin.discount.mapper.DiscountMapper;
import com.example.back_end.core.admin.discount.payload.request.DiscountFilterRequest;
import com.example.back_end.core.admin.discount.payload.request.DiscountRequest;
import com.example.back_end.core.admin.discount.payload.response.DiscountFullResponse;
import com.example.back_end.core.admin.discount.payload.response.DiscountNameResponse;
import com.example.back_end.core.admin.discount.payload.response.DiscountResponse;
import com.example.back_end.core.admin.discount.service.DiscountService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.Discount;
import com.example.back_end.infrastructure.constant.DiscountType;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.constant.SortType;
import com.example.back_end.infrastructure.exception.ExistsByNameException;
import com.example.back_end.infrastructure.exception.InvalidDataException;
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.infrastructure.utils.ConvertEnumTypeUtils;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.infrastructure.utils.StringUtils;
import com.example.back_end.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;

    @Override
    public List<DiscountNameResponse> getAllDiscounts(Integer type) {

        List<Discount> discounts = discountRepository.findAll();

        DiscountType discountType = ConvertEnumTypeUtils.converDiscountType(type);

        return discounts
                .stream()
                .filter(discount -> discount.getDiscountTypeId() == discountType)
                .map(discountMapper::toDiscountNameResponse)
                .toList();
    }

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

        List<DiscountResponse> discountResponseList = discountMapper.toResponseList(discountPage.getContent());

        return PageResponse.<List<DiscountResponse>>builder()
                .page(discountPage.getNumber())
                .size(discountPage.getSize())
                .totalPage(discountPage.getTotalPages())
                .items(discountResponseList)
                .build();
    }

    @Override
    public void createDiscount(DiscountRequest discountRequest) {

        String discountName = StringUtils.sanitizeText(discountRequest.getName());
        if (discountRepository.existsByName(discountName))
            throw new ExistsByNameException(ErrorCode.DISCOUNT_WITH_THIS_NAME_ALREADY_EXISTS.getMessage());

        Discount discount = discountMapper.toEntity(discountRequest);

        validateDiscount(discount);

        if (discount.getDiscountPercentage() != null && discount.getMaxDiscountAmount() == null) {
            BigDecimal maxDiscountAmount = discount.getDiscountPercentage().multiply(BigDecimal.valueOf(100));
            discount.setMaxDiscountAmount(maxDiscountAmount);
        }

        discountRepository.save(discount);
    }

    @Override
    public void updateDiscount(Long id, DiscountRequest discountRequest) {

        Discount discount = findDiscountById(id);

        String discountName = StringUtils.sanitizeText(discountRequest.getName());
        if (discountRepository.existsByNameAndIdNot(discountName, id))
            throw new ExistsByNameException("Discount with name '" + discountRequest.getName() + "' already exists.");

        discountMapper.updateEntityFromRequest(discountRequest, discount);

        validateDiscount(discount);

        discountRepository.save(discount);
    }

    @Override
    public DiscountFullResponse getDiscountById(Long id) {
        Discount discount = findDiscountById(id);
        return discountMapper.toGetOneResponse(discount);
    }

    @Override
    public void deleteDiscount(Long id) {
        Discount discount = findDiscountById(id);
        discountRepository.delete(discount);
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

    private Discount findDiscountById(Long id) {
        return discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DISCOUNT_NOT_FOUND.getMessage()));
    }


}
