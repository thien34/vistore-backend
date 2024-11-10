package com.example.back_end.service.discount.impl;

import com.example.back_end.core.admin.discount.mapper.DiscountMapper;
import com.example.back_end.core.admin.discount.payload.request.DiscountFilterRequest;
import com.example.back_end.core.admin.discount.payload.request.DiscountRequest;
import com.example.back_end.core.admin.discount.payload.response.DiscountFullResponse;
import com.example.back_end.core.admin.discount.payload.response.DiscountNameResponse;
import com.example.back_end.core.admin.discount.payload.response.DiscountResponse;
import com.example.back_end.core.admin.discount.payload.response.ProductResponseDetails;
import com.example.back_end.entity.Discount;
import com.example.back_end.entity.DiscountAppliedToProduct;
import com.example.back_end.entity.Product;
import com.example.back_end.infrastructure.constant.DiscountType;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.exception.ExistsByNameException;
import com.example.back_end.infrastructure.exception.InvalidDataException;
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.infrastructure.utils.ConvertEnumTypeUtils;
import com.example.back_end.infrastructure.utils.StringUtils;
import com.example.back_end.repository.DiscountAppliedToProductRepository;
import com.example.back_end.repository.DiscountRepository;
import com.example.back_end.repository.ProductRepository;
import com.example.back_end.service.discount.DiscountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DiscountServiceImpl implements DiscountService {

    DiscountRepository discountRepository;
    DiscountMapper discountMapper;
    ProductRepository productRepository;
    DiscountAppliedToProductRepository discountAppliedToProductRepository;

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
    public List<DiscountResponse> getAllDiscounts(DiscountFilterRequest filterRequest) {
        List<Discount> discounts = discountRepository.searchDiscountsNoPage(
                filterRequest.getName(),
                filterRequest.getCouponCode(),
                filterRequest.getDiscountTypeId(),
                filterRequest.getStartDate(),
                filterRequest.getEndDate(),
                filterRequest.getIsActive()
        );

        return discountMapper.toResponseList(discounts);
    }


    @Override
    public List<DiscountResponse> getDiscountsByType(Integer type) {
        DiscountType discountType = ConvertEnumTypeUtils.converDiscountType(type);

        List<Discount> discounts = discountRepository.findAll();

        return discounts.stream()
                .filter(discount -> discount.getDiscountTypeId() == discountType)
                .map(discountMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void createDiscount(DiscountRequest discountRequest) {
        String discountName = StringUtils.sanitizeText(discountRequest.getName()).trim();
        if (discountRepository.existsByName(discountName))
            throw new ExistsByNameException(ErrorCode.DISCOUNT_WITH_THIS_NAME_ALREADY_EXISTS.getMessage());

        Discount discount = discountMapper.toEntity(discountRequest);
        validateDiscount(discount);

        if (discount.getStartDateUtc() == null)
            discount.setStartDateUtc(Instant.now());

        if (discount.getEndDateUtc() == null)
            discount.setEndDateUtc(discount.getStartDateUtc().plusSeconds(86400));

        if (discount.getDiscountPercentage() != null && discount.getMaxDiscountAmount() == null) {
            BigDecimal maxDiscountAmount = discount.getDiscountPercentage().multiply(BigDecimal.valueOf(100));
            discount.setMaxDiscountAmount(maxDiscountAmount);
        }

        updateDiscountStatus(discount);

        discount = discountRepository.save(discount);

        if (discountRequest.getSelectedProductVariantIds() == null || discountRequest.getSelectedProductVariantIds().isEmpty()) {
            throw new InvalidDataException("At least one product variant must be selected to apply the discount.");
        }

        saveDiscountAppliedToProducts(discount, discountRequest.getSelectedProductVariantIds());
    }

    private void saveDiscountAppliedToProducts(Discount discount, List<Long> selectedProductVariantIds) {
        if (selectedProductVariantIds == null || selectedProductVariantIds.isEmpty()) {
            throw new InvalidDataException("At least one product variant must be selected to apply the discount.");
        }

        List<Long> validProductIds = new ArrayList<>();
        for (Long productId : selectedProductVariantIds) {

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new NotFoundException("Product not found with ID: " + productId));

            if (product.getParentProductId() != null) {
                validProductIds.add(productId);
            }
        }

        if (validProductIds.isEmpty()) {
            throw new InvalidDataException("At least one product variant must have a non-null parent ID to apply the discount.");
        }

        for (Long validProductId : validProductIds) {
            Product validProduct = productRepository.findById(validProductId)
                    .orElseThrow(() -> new NotFoundException("Product not found with ID: " + validProductId));

            updateProductDiscountPrice(validProduct, discount);

            saveDiscountAppliedToProduct(discount, validProduct);
        }
    }

    private void saveDiscountAppliedToProduct(Discount discount, Product product) {
        DiscountAppliedToProduct discountAppliedToProduct = DiscountAppliedToProduct.builder()
                .discount(discount)
                .product(product)
                .build();

        discountAppliedToProductRepository.save(discountAppliedToProduct);
    }

    private void updateProductDiscountPrice(Product product, Discount discount) {
        if (discount.getDiscountPercentage() != null) {
            BigDecimal discountPercentage = discount.getDiscountPercentage();
            BigDecimal discountAmount = product.getUnitPrice()
                    .multiply(discountPercentage)
                    .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
            BigDecimal discountPrice = product.getUnitPrice().subtract(discountAmount);
            product.setDiscountPrice(discountPrice);
            productRepository.save(product);
        }
    }

    private void updateDiscountStatus(Discount discount) {
        Instant now = Instant.now();

        if (discount.getEndDateUtc() != null && now.isAfter(discount.getEndDateUtc())) {
            discount.setStatus("EXPIRED");
        } else if (discount.getStartDateUtc() != null && now.isBefore(discount.getStartDateUtc())) {
            discount.setStatus("UPCOMING");
        } else {
            discount.setStatus("ACTIVE");
        }
    }

    @Override
    @Transactional
    public void updateDiscount(Long id, DiscountRequest discountRequest) {
        Discount discount = findDiscountById(id);

        String discountName = StringUtils.sanitizeText(discountRequest.getName());
        if (discountRepository.existsByNameAndIdNot(discountName, id))
            throw new ExistsByNameException("Discount with name '" + discountRequest.getName() + "' already exists.");

        discountMapper.updateEntityFromRequest(discountRequest, discount);

        validateDiscount(discount);
        updateDiscountStatus(discount);
        discountRepository.save(discount);

        List<Long> existingProductIds = discountAppliedToProductRepository.findProductIdsByDiscountId(id);
        List<Long> newProductIds = discountRequest.getSelectedProductVariantIds();

        existingProductIds.stream()
                .filter(productId -> !newProductIds.contains(productId))
                .forEach(productId -> discountAppliedToProductRepository.deleteByDiscountIdAndProductId(id, productId));

        newProductIds.stream()
                .filter(productId -> !existingProductIds.contains(productId))
                .forEach(productId -> {
                    long activeDiscountCount = discountAppliedToProductRepository.countActiveDiscountsForProduct(productId);
                    if (activeDiscountCount >= 1) {
                        throw new InvalidDataException("A product can have a maximum of 1 active discounts.");
                    }
                    saveDiscountAppliedToProduct(discount, productId);
                });
    }


    private void saveDiscountAppliedToProduct(Discount discount, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new EntityNotFoundException("Product not found with id: " + productId));
        DiscountAppliedToProduct discountAppliedToProduct = DiscountAppliedToProduct.builder()
                .discount(discount)
                .product(product)
                .build();
        discountAppliedToProductRepository.save(discountAppliedToProduct);
    }

    @Override
    public DiscountFullResponse getDiscountById(Long id) {
        Discount discount = findDiscountById(id);
        updateDiscountStatus(discount);

        List<DiscountAppliedToProduct> appliedProducts = discountAppliedToProductRepository.findByDiscountId(id);
        List<ProductResponseDetails> productDetails = appliedProducts.stream()
                .map(appliedProduct -> {
                    Product product = appliedProduct.getProduct();
                    return new ProductResponseDetails(
                            product.getId(),
                            product.getName(),
                            product.getCategory().getName(),
                            product.getManufacturer().getName(),
                            product.getSku(),
                            product.getParentProductId()
                    );
                })
                .toList();

        DiscountFullResponse response = discountMapper.toGetOneResponse(discount);
        response.setAppliedProducts(productDetails);
        return response;
    }


    @Override
    @Transactional
    public void deleteDiscount(Long id) {
        Discount discount = findDiscountById(id);
        discountRepository.delete(discount);
    }

    private void validateDiscount(Discount discount) {
        validateDiscountAmount(discount);
        validateCouponCodeRequirement(discount);
        validateDate(discount.getStartDateUtc(), discount.getEndDateUtc());
        validateDiscountNameLength(discount.getName());
        validateDiscountPercentage(discount.getDiscountPercentage());
        validateMaxDiscountAmount(discount);
    }

    private void validateDiscountAmount(Discount discount) {
        Boolean usePercentage = discount.getUsePercentage();
        BigDecimal discountPercentage = discount.getDiscountPercentage();
        BigDecimal discountAmount = discount.getDiscountAmount();
        DiscountType discountType = discount.getDiscountTypeId();

        if (discountType == DiscountType.ASSIGNED_TO_PRODUCTS && (usePercentage == null || !usePercentage)) {
            throw new InvalidDataException("Please use percentage-based discounts for product-specific discounts.");
        }

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

    private void validateDiscountNameLength(String discountName) {
        if (discountName.length() > 50) {
            throw new IllegalArgumentException("The 'name' must not exceed 50 characters.");
        }
    }

    private void validateDiscountPercentage(BigDecimal discountPercentage) {
        if (discountPercentage == null) {
            throw new IllegalArgumentException("The 'discountPercentage' cannot be null.");
        }
        if (discountPercentage.compareTo(BigDecimal.ZERO) < 0 || discountPercentage.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("The 'discountPercentage' must be between 0 and 100.");
        }
    }

    private void validateMaxDiscountAmount(Discount discount) {
        BigDecimal maxDiscountAmount = discount.getMaxDiscountAmount();
        if (maxDiscountAmount != null && maxDiscountAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The 'maxDiscountAmount' must be a positive value.");
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
