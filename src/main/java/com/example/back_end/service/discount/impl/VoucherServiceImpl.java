package com.example.back_end.service.discount.impl;

import com.example.back_end.core.admin.discount.mapper.VoucherMapper;
import com.example.back_end.core.admin.discount.payload.request.DiscountFilterRequest;
import com.example.back_end.core.admin.discount.payload.request.VoucherRequest;
import com.example.back_end.core.admin.discount.payload.response.VoucherResponse;
import com.example.back_end.entity.Customer;
import com.example.back_end.entity.CustomerVoucher;
import com.example.back_end.entity.Discount;
import com.example.back_end.infrastructure.exception.InvalidDataException;
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.repository.CustomerRepository;
import com.example.back_end.repository.CustomerVoucherRepository;
import com.example.back_end.repository.DiscountRepository;
import com.example.back_end.service.discount.EmailService;
import com.example.back_end.service.discount.VoucherService;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VoucherServiceImpl implements VoucherService {
    DiscountRepository discountRepository;
    VoucherMapper voucherMapper;
    CustomerRepository customerRepository;
    CustomerVoucherRepository customerVoucherRepository;
    EmailService emailService;

    private static final Random RANDOM = new Random();
    private static final String CHARACTERS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    @Override
    public List<VoucherResponse> getAllVouchers(DiscountFilterRequest filterRequest) {
        List<Discount> discounts = discountRepository.searchDiscountsNoPage(
                filterRequest.getName(),
                filterRequest.getCouponCode(),
                filterRequest.getDiscountTypeId(),
                filterRequest.getStartDate(),
                filterRequest.getEndDate()
        );
        discounts.forEach(this::updateVoucherStatus);
        return voucherMapper.toResponseList(discounts);
    }
    public static String generateVoucherCode(String prefix, Long id) {
        StringBuilder randomPart = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            randomPart.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return prefix + id + randomPart;
    }

    public void updateVoucherStatus(Discount discount) {
        updateStatusVoucher(discount, discountRepository);
    }

    public static void updateStatusVoucher(Discount discount, DiscountRepository discountRepository) {
        Instant now = Instant.now();
        if (discount.getIsCanceled() != null && discount.getIsCanceled()) {
            discount.setStatus("CANCEL");
        } else if (discount.getEndDateUtc() != null && now.isAfter(discount.getEndDateUtc())) {
            discount.setStatus("EXPIRED");
        } else if (discount.getStartDateUtc() != null && now.isBefore(discount.getStartDateUtc())) {
            discount.setStatus("UPCOMING");
        } else {
            discount.setStatus("ACTIVE");
        }
        discountRepository.save(discount);
    }

    @Override
    @Transactional
    public void createDiscount(VoucherRequest voucherRequest) {
        Discount discount = voucherMapper.toEntity(voucherRequest);
        validateDiscount(discount);

        if (discount.getStartDateUtc() == null)
            discount.setStartDateUtc(Instant.now());

        if (discount.getEndDateUtc() == null)
            discount.setEndDateUtc(discount.getStartDateUtc().plusSeconds(86400));

        updateVoucherStatus(discount);
        discount = discountRepository.save(discount);

        if (voucherRequest.getSelectedCustomerIds() == null || voucherRequest.getSelectedCustomerIds().isEmpty()) {
            throw new InvalidDataException("At least one customer must be selected to apply the discount.");
        }

        saveDiscountAppliedToCustomers(discount, voucherRequest.getSelectedCustomerIds());
        sendVoucherEmailsToCustomers(voucherRequest.getSelectedCustomerIds(), discount.getCouponCode(), discount.getName(), discount.getStartDateUtc(), discount.getEndDateUtc(), discount.getDiscountPercentage(), discount.getDiscountAmount());
    }

    private void sendVoucherEmailsToCustomers(List<Long> customerIds, String voucherCode, String discountDetails, Instant startDate, Instant endDate, BigDecimal discountPercentage, BigDecimal discountAmount) {
        if (voucherCode == null || voucherCode.trim().isEmpty()) {
            log.warn("Voucher code is missing, no email will be sent.");
            return;
        }

        List<String> customerEmails = customerRepository.findByIdIn(customerIds).stream()
                .map(Customer::getEmail)
                .toList();

        try {
            if (discountPercentage != null) {
                emailService.sendVoucherEmails(customerEmails, voucherCode, discountDetails, startDate, endDate, discountPercentage, null);
                log.info("Voucher emails with percentage sent successfully.");
            } else if (discountAmount != null) {
                emailService.sendVoucherEmails(customerEmails, voucherCode, discountDetails, startDate, endDate, null, discountAmount);
                log.info("Voucher emails with fixed amount sent successfully.");
            } else {
                log.warn("Neither discount percentage nor discount amount found, no email sent.");
            }
        } catch (MessagingException e) {
            log.error("Failed to send voucher emails.", e);
        }
    }

    @Scheduled(cron = "0 1 0 * * *")
    @Override
    @Transactional
    public void checkAndGenerateBirthdayVoucher() {
        LocalDate today = LocalDate.now();
        List<Customer> customers = customerRepository.findAllByBirthday(today);
        for (Customer customer : customers) {
            createBirthdayVoucher(customer);
        }
        log.info("Processed birthday vouchers for {} customers", customers.size());
    }

    @Transactional
    public void createBirthdayVoucher(Customer customer) {
        BigDecimal discountPercent = BigDecimal.TEN;
        Instant startDate = Instant.now();
        Instant endDate = startDate.plusSeconds(86400L * 5);
        VoucherRequest voucherRequest = VoucherRequest.builder()
                .name("Birthday Voucher for " + customer.getFirstName() + " " + customer.getLastName())
                .couponCode(generateVoucherCode("BDAY_",customer.getId()))
                .discountPercentage(discountPercent)
                .startDateUtc(startDate)
                .endDateUtc(endDate)
                .usePercentage(true)
                .selectedCustomerIds(List.of(customer.getId()))
                .requiresCouponCode(true)
                .build();
        createDiscount(voucherRequest);
        log.info("Created birthday voucher for customer: {}", customer.getEmail());
    }

    private void validateDiscount(Discount discount) {
        validateDiscountAmount(discount);
        validateCouponCodeRequirement(discount);
        validateDate(discount.getStartDateUtc(), discount.getEndDateUtc());
        validateDiscountNameLength(discount.getName());
        validateMaxDiscountAmount(discount);
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

    private void validateDiscountNameLength(String discountName) {
        if (discountName.length() > 50) {
            throw new IllegalArgumentException("The 'name' must not exceed 50 characters.");
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

    private void saveDiscountAppliedToCustomers(Discount discount, List<Long> selectedCustomerIds) {
        if (selectedCustomerIds == null || selectedCustomerIds.isEmpty()) {
            throw new InvalidDataException("At least one customer must be selected to apply the discount.");
        }

        for (Long customerId : selectedCustomerIds) {
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new NotFoundException("Customer not found with ID: " + customerId));

            CustomerVoucher customerVoucher = CustomerVoucher.builder()
                    .customer(customer)
                    .discount(discount)
                    .build();

            customerVoucherRepository.save(customerVoucher);
        }
    }

//    private void saveDiscountAppliedToProduct(Discount discount, Product product) {
//        DiscountAppliedToProduct discountAppliedToProduct = DiscountAppliedToProduct.builder()
//                .discount(discount)
//                .product(product)
//                .build();
//
//        discountAppliedToProductRepository.save(discountAppliedToProduct);
//    }
//    private void updateProductDiscountPrice(Product product, Discount discount) {
//        if (!"ACTIVE".equals(discount.getStatus())) {
//            product.setDiscountPrice(BigDecimal.ZERO);
//        } else if (discount.getDiscountPercentage() != null) {
//            BigDecimal discountPercentage = discount.getDiscountPercentage();
//            BigDecimal discountAmount = product.getUnitPrice()
//                    .multiply(discountPercentage)
//                    .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
//            BigDecimal discountPrice = product.getUnitPrice().subtract(discountAmount);
//            product.setDiscountPrice(discountPrice);
//        }
//        productRepository.save(product);
//    }

}
