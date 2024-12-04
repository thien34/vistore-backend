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
import com.example.back_end.service.product.impl.ProductServiceImpl;
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
    @Transactional
    public void updateEndDateToNow(Long id) {
        Discount discount = findDiscountById(id);
        Instant now = Instant.now();
        discount.setEndDateUtc(now);
        updateDiscountStatus(discount);
        discountRepository.save(discount);
    }
    public void cancelDiscount(Long promotionId) {
        Discount discount = findDiscountById(promotionId);
        discount.setIsCanceled(Boolean.TRUE);
        discount.setStatus("CANCEL");
        discountRepository.save(discount);
    }

    @Override
    public List<DiscountResponse> getAllDiscounts(DiscountFilterRequest filterRequest) {
        List<Discount> discounts = discountRepository.searchDiscountsNoPage(
                filterRequest.getName(),
                filterRequest.getCouponCode(),
                filterRequest.getDiscountTypeId(),
                filterRequest.getStartDate(),
                filterRequest.getEndDate(),
                filterRequest.getStatus(),
                filterRequest.getIsPublished()
        );
        discounts.forEach(this::updateDiscountStatus);
        return discountMapper.toResponseList(discounts);
    }
    @Override
    public List<DiscountResponse> getDiscountsByProductId(Long productId) {
        List<DiscountAppliedToProduct> appliedDiscounts = discountAppliedToProductRepository.findByProductId(productId);

        if (appliedDiscounts.isEmpty()) {
            throw new NotFoundException("Không tìm thấy giảm giá cho sản phẩm có ID: " + productId);
        }

        List<Discount> discounts = new ArrayList<>();
        for (DiscountAppliedToProduct discountAppliedToProduct : appliedDiscounts) {
            discounts.add(discountAppliedToProduct.getDiscount());
        }

        return discounts.stream()
                .map(discountMapper::toResponse)
                .toList();
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
            throw new InvalidDataException("Phải chọn ít nhất một mẫu mã sản phẩm để áp dụng ưu đãi giảm giá.");
        }

        saveDiscountAppliedToProducts(discount, discountRequest.getSelectedProductVariantIds());
    }

    private void saveDiscountAppliedToProducts(Discount discount, List<Long> selectedProductVariantIds) {
        if (selectedProductVariantIds == null || selectedProductVariantIds.isEmpty()) {
            throw new InvalidDataException("Phải chọn ít nhất một mẫu mã sản phẩm để áp dụng ưu đãi giảm giá.");
        }

        List<Long> validProductIds = new ArrayList<>();
        for (Long productId : selectedProductVariantIds) {

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new NotFoundException("Sản phẩm không được tìm thấy với ID: " + productId));

            if (product.getParentProductId() != null) {
                validProductIds.add(productId);
            }
        }

        if (validProductIds.isEmpty()) {
            throw new InvalidDataException("Ít nhất một mẫu mã sản phẩm phải có mã định danh chính không phải là null để áp dụng ưu đãi giảm giá.");
        }

        for (Long validProductId : validProductIds) {
            Product validProduct = productRepository.findById(validProductId)
                    .orElseThrow(() -> new NotFoundException("Sản phẩm không được tìm thấy với ID: " + validProductId));

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
        if (!"ACTIVE".equals(discount.getStatus())) {
            product.setDiscountPrice(BigDecimal.ZERO);
        } else if (discount.getDiscountPercentage() != null) {
            BigDecimal discountPercentage = discount.getDiscountPercentage();
            BigDecimal discountAmount = product.getUnitPrice()
                    .multiply(discountPercentage)
                    .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
            BigDecimal discountPrice = product.getUnitPrice().subtract(discountAmount);
            product.setDiscountPrice(discountPrice);
        }
        productRepository.save(product);
    }

    private void updateDiscountStatus(Discount discount) {
        ProductServiceImpl.discountStatus(discount, discountRepository);
    }

    @Override
    @Transactional
    public void updateDiscount(Long id, DiscountRequest discountRequest) {
        Discount discount = findDiscountById(id);

        String discountName = StringUtils.sanitizeText(discountRequest.getName());
        if (discountRepository.existsByNameAndIdNot(discountName, id))
            throw new ExistsByNameException("Giảm giá có tên '" + discountRequest.getName() + "' đã tồn tại.");

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
                .forEach(productId -> saveDiscountAppliedToProduct(discount, productId));
    }


    private void saveDiscountAppliedToProduct(Discount discount, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new EntityNotFoundException("Sản phẩm không được tìm thấy với id: " + productId));
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
                            product.getParentProductId(),
                            product.getImage(),
                            product.getFullName()
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
            throw new InvalidDataException("Vui lòng sử dụng giảm giá dựa trên tỷ lệ phần trăm để giảm giá dành riêng cho sản phẩm.");
        }

        if (usePercentage != null && usePercentage) {
            if (discountAmount != null) {
                throw new IllegalArgumentException(
                        "Khi 'usePercentage' là true, 'discountAmount' không nên được cung cấp.");
            }
        } else {
            if (discountPercentage != null) {
                throw new IllegalArgumentException(
                        "Khi 'usePercentage' là sai, không nên cung cấp 'discountPercentage'.");
            }
        }
    }

    private void validateDiscountNameLength(String discountName) {
        if (discountName.length() > 50) {
            throw new IllegalArgumentException("'Name' không được vượt quá 50 ký tự.");
        }
    }

    private void validateDiscountPercentage(BigDecimal discountPercentage) {
        if (discountPercentage == null) {
            throw new IllegalArgumentException("The 'discountPercentage' cannot be null.");
        }
        if (discountPercentage.compareTo(BigDecimal.ZERO) < 0 || discountPercentage.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("'discountPercentage' phải nằm trong khoảng từ 0 đến 100.");
        }
    }

    private void validateMaxDiscountAmount(Discount discount) {
        BigDecimal maxDiscountAmount = discount.getMaxDiscountAmount();
        if (maxDiscountAmount != null && maxDiscountAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("'maxDiscountAmount' phải là giá trị dương.");
        }
    }

    private void validateCouponCodeRequirement(Discount discount) {
        Predicate<Discount> requiresCouponCodeCheck = d ->
                d.getRequiresCouponCode() != null && d.getRequiresCouponCode();

        Runnable couponCodeValidation = () -> {
            if (discount.getCouponCode() == null || discount.getCouponCode().trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "Nếu 'requiresCouponCode' là true, 'couponCode' không thể rỗng hoặc trống.");
            }
        };

        if (requiresCouponCodeCheck.test(discount)) {
            couponCodeValidation.run();
        }
    }

    private void validateDate(Instant startDate, Instant endDate) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new InvalidDataException("'startDateUtc' phải đứng trước 'endDateUtc'.");
        }
    }

    private Discount findDiscountById(Long id) {
        return discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DISCOUNT_NOT_FOUND.getMessage()));
    }

}
