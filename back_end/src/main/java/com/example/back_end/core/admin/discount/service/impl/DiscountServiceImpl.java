package com.example.back_end.core.admin.discount.service.impl;

import com.example.back_end.core.admin.discount.mapper.DiscountMapper;
import com.example.back_end.core.admin.discount.payload.request.DiscountRequest;
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
    public PageResponse<List<DiscountResponse>> getAllDiscounts(int pageNo,
                                                                int pageSize) {
        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "id", SortType.DESC.getValue());
        Page<Discount> discountPage = discountRepository.findAll(pageable);

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
    public DiscountResponse createDiscount(DiscountRequest discountRequest) {

        String trimmedName = discountRequest.getName().trim().replaceAll("\\s+", " ");

        if (discountRepository.existsByName(trimmedName)) {
            throw new ExistsByNameException(ErrorCode.DISCOUNT_WITH_THIS_NAME_ALREADY_EXISTS.getMessage());
        }

        Discount discount = discountMapper.toEntity(discountRequest);
        validateDiscount(discount);

        if (discount.getDiscountPercentage() != null && discount.getMaxDiscountAmount() == null) {
            BigDecimal maxDiscountAmount = discount.getDiscountPercentage().multiply(new BigDecimal(100));
            discount.setMaxDiscountAmount(maxDiscountAmount);
        }

        Discount savedDiscount = discountRepository.save(discount);
        return discountMapper.toResponse(savedDiscount);

    }

    private void validateDiscount(Discount discount) {

        validateField(discount.getDiscountTypeId(), dtId -> dtId == 1 && !discount.getUsePercentage(),
                "For discount type 'Assigned to order total', 'usePercentage' must be true");

        validateField(discount.getDiscountAmount(), da -> discount.getDiscountPercentage() != null,
                "You should provide either 'discountAmount' or 'discountPercentage', not both.");

        validateField(discount.getRequiresCouponCode(), rcc -> rcc &&
                (discount.getCouponCode() == null || discount.getCouponCode().isEmpty()),
                "If 'requiresCouponCode' is true, 'couponCode' cannot be null or empty.");

        validateDate(discount.getStartDateUtc(), discount.getEndDateUtc());

    }

    private <T> void validateField(T field, Predicate<T> condition, String errorMessage) {
        if (condition.test(field))
            throw new IllegalArgumentException(errorMessage);

    }

    private void validateDate(Instant startDate, Instant endDate) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate))
            throw new InvalidDataException("The 'startDateUtc' must be before 'endDateUtc'.");

    }

    @Override
    @Transactional
    public DiscountResponse updateDiscount(Long id, DiscountRequest discountRequest) {

        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Discount not found with ID: " + id));

        if (discountRepository.existsByNameAndIdNot(discountRequest.getName(), id)) {
            throw new ExistsByNameException("Discount with name '" + discountRequest.getName() + "' already exists.");
        }

        discountMapper.updateEntityFromRequest(discountRequest, discount);

        Discount updatedDiscount = discountRepository.save(discount);

        return discountMapper.toResponse(updatedDiscount);
    }

    @Override
    public void deleteDiscount(Long id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DISCOUNT_NOT_FOUND.getMessage()));

        discountRepository.delete(discount);

    }

}
