package com.example.back_end.service.discount.impl;

import com.example.back_end.core.admin.customer.payload.response.CustomerResponse;
import com.example.back_end.core.admin.discount.mapper.VoucherMapper;
import com.example.back_end.core.admin.discount.payload.request.DiscountFilterRequest;
import com.example.back_end.core.admin.discount.payload.request.VoucherBirthdayUpdateRequest;
import com.example.back_end.core.admin.discount.payload.request.VoucherRequest;
import com.example.back_end.core.admin.discount.payload.request.VoucherUpdateRequest;
import com.example.back_end.core.admin.discount.payload.response.VoucherApplyResponse;
import com.example.back_end.core.admin.discount.payload.response.VoucherApplyResponseWrapper;
import com.example.back_end.core.admin.discount.payload.response.VoucherFullResponse;
import com.example.back_end.core.admin.discount.payload.response.VoucherResponse;
import com.example.back_end.entity.Customer;
import com.example.back_end.entity.CustomerVoucher;
import com.example.back_end.entity.Discount;
import com.example.back_end.infrastructure.constant.DiscountType;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.exception.InvalidDataException;
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.repository.CustomerRepository;
import com.example.back_end.repository.CustomerVoucherRepository;
import com.example.back_end.repository.DiscountRepository;
import com.example.back_end.service.discount.EmailService;
import com.example.back_end.service.discount.VoucherService;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final DiscountRepository discountRepository;
    private final VoucherMapper voucherMapper;
    private final CustomerRepository customerRepository;
    private final CustomerVoucherRepository customerVoucherRepository;
    private final EmailService emailService;

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

    private static int getCurrentLimitationTimes(VoucherUpdateRequest request, Discount discount, int currentUsageCount) {
        int currentLimitationTimes = (discount.getLimitationTimes() != null) ? discount.getLimitationTimes() : 0;

        if (request.getMaxUsageCount() < currentUsageCount) {
            throw new InvalidDataException(
                    "Không thể giảm số lượng sử dụng tối đa dưới số lượng sử dụng hiện tại (" + currentUsageCount + ")."
            );
        }

        if ("ACTIVE".equals(discount.getStatus()) && request.getMaxUsageCount() < currentLimitationTimes) {
            throw new InvalidDataException(
                    "Không thể giảm số lượng sử dụng tối đa trong khi voucher đang ACTIVE."
            );
        }
        return currentLimitationTimes;
    }

    @Override
    public List<VoucherResponse> getAllVouchers(DiscountFilterRequest filterRequest) {
        List<Discount> discounts = discountRepository.searchDiscountsNoPage(
                filterRequest.getName(),
                filterRequest.getCouponCode(),
                filterRequest.getDiscountTypeId(),
                filterRequest.getStartDate(),
                filterRequest.getEndDate(),
                filterRequest.getStatus(),
                filterRequest.getIsPublished()
        );
        discounts.sort(Comparator.comparing(Discount::getCreatedDate).reversed());
        discounts.forEach(this::updateVoucherStatus);
        return voucherMapper.toResponseList(discounts);
    }

    @Override
    public Discount getDefaultBirthdayDiscount() {
        Optional<Discount> discount = discountRepository.findByName("Birthday Default Discount");
        return discount.orElseThrow(() -> new NotFoundException("Không tìm thấy giảm giá sinh nhật mặc định."));
    }

    @PostConstruct
    public void createDefaultBirthdayDiscountIfNotExist() {
        boolean exists = discountRepository.findByName("Birthday Default Discount").isPresent();

        if (!exists) {
            Discount defaultDiscount = Discount.builder()
                    .name("Birthday Default Discount")
                    .discountPercentage(BigDecimal.TEN)
                    .usePercentage(true)
                    .isPublished(false)
                    .build();
            discountRepository.save(defaultDiscount);
        }
    }

    public void updateVoucherStatus(Discount discount) {
        updateStatusVoucher(discount, discountRepository);
    }

    @Override
    @Transactional
    public Discount createDiscount(VoucherRequest voucherRequest) {
        checkDuplicateCouponCode(voucherRequest.getCouponCode(), voucherRequest.getIsPublished());
        Discount discount = voucherMapper.toEntity(voucherRequest);
        validateDiscount(discount);
        boolean checkCode = discountRepository.existsByCouponCode(voucherRequest.getCouponCode());
        if (checkCode) {
            throw new InvalidDataException("Mã code đã tồn tại !");
        }

        if (discount.getStartDateUtc() == null)
            discount.setStartDateUtc(Instant.now());

        if (discount.getEndDateUtc() == null)
            discount.setEndDateUtc(discount.getStartDateUtc().plusSeconds(86400));

        updateVoucherStatus(discount);
        discount = discountRepository.save(discount);
        saveDiscountAppliedToCustomers(discount, voucherRequest.getSelectedCustomerIds());
        sendVoucherEmailsToCustomers(
                voucherRequest.getSelectedCustomerIds(),
                discount.getCouponCode(),
                discount.getName(),
                discount.getStartDateUtc(),
                discount.getEndDateUtc(),
                discount.getDiscountPercentage(),
                discount.getDiscountAmount());
        return discount;
    }

    private void checkDuplicateCouponCode(String couponCode, Boolean isPublished) {
        if (Boolean.FALSE.equals(isPublished)) {
            if (couponCode == null || couponCode.trim().isEmpty()) {
                throw new InvalidDataException("Mã phiếu giảm giá không được rỗng hoặc trống đối với các phiếu giảm giá chưa được công bố.");
            }
        }
    }

    private void sendVoucherEmailsToCustomers(List<Long> customerIds, String voucherCode, String discountDetails, Instant startDate, Instant endDate, BigDecimal discountPercentage, BigDecimal discountAmount) {
        if (voucherCode == null || voucherCode.trim().isEmpty()) {
            log.warn("Mã phiếu thưởng bị thiếu, không có email nào được gửi.");
            return;
        }

        List<String> customerEmails = customerRepository.findByIdIn(customerIds).stream()
                .map(Customer::getEmail)
                .toList();

        try {
            BigDecimal validDiscountPercentage = (discountPercentage != null && discountPercentage.compareTo(BigDecimal.ZERO) > 0)
                    ? discountPercentage
                    : null;
            BigDecimal validDiscountAmount = (discountAmount != null && discountAmount.compareTo(BigDecimal.ZERO) > 0)
                    ? discountAmount
                    : null;
            if (validDiscountPercentage != null) {
                emailService.sendVoucherEmails(customerEmails, voucherCode, discountDetails, startDate, endDate, validDiscountPercentage, null);
                log.info("Email phiếu giảm giá với tỷ lệ phần trăm được gửi thành công.");
            } else if (validDiscountAmount != null) {
                emailService.sendVoucherEmails(customerEmails, voucherCode, discountDetails, startDate, endDate, null, validDiscountAmount);
                log.info("Email voucher với số tiền cố định được gửi thành công.");
            } else {
                log.warn("Không tìm thấy tỷ lệ phần trăm chiết khấu và số tiền chiết khấu, không có email nào được gửi.");
            }
        } catch (MessagingException e) {
            log.error("Không gửi được email voucher.", e);
        }

    }

    @Scheduled(cron = "0 1 0 * * *")
    @Transactional
    public void checkAndGenerateBirthdayVoucher() {
        LocalDate today = LocalDate.now();
        String formattedToday = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<Customer> customers = customerRepository.findAllByBirthday(today);

        if (customers.isEmpty()) {
            log.info("Không có khách hàng nào có sinh nhật hôm nay.");
            return;
        }

        Discount defaultDiscount = discountRepository.findByName("Birthday Default Discount")
                .orElseThrow(() -> new NotFoundException("Giảm giá mặc định sinh nhật không tồn tại."));
        Instant startDate = Instant.now();
        Instant endDate = startDate.plusSeconds(86400L * 5);

        VoucherRequest voucherRequest = VoucherRequest.builder()
                .name("Voucher sinh nhật ngày " + formattedToday)
                .couponCode("BDAY" + formattedToday)
                .discountPercentage(defaultDiscount.getDiscountPercentage())
                .discountAmount(defaultDiscount.getDiscountAmount())
                .startDateUtc(startDate)
                .endDateUtc(endDate)
                .limitationTimes(customers.size())
                .perCustomerLimit(1)
                .comment("Happy birthday")
                .isPublished(defaultDiscount.getIsPublished())
                .minOderAmount(defaultDiscount.getMinOderAmount())
                .isCumulative(defaultDiscount.getIsCumulative())
                .maxDiscountAmount(defaultDiscount.getMaxDiscountAmount())
                .discountTypeId(DiscountType.ASSIGNED_TO_ORDER_TOTAL)
                .usePercentage(defaultDiscount.getUsePercentage())
                .selectedCustomerIds(customers.stream().map(Customer::getId).toList())
                .requiresCouponCode(true)
                .build();

        createDiscount(voucherRequest);
    }

    @Override
    public void setDefaultBirthdayDiscountPercentage(BigDecimal discountPercentage) {
        Discount defaultDiscount = discountRepository.findByName("Birthday Default Discount")
                .orElseGet(() -> Discount.builder()
                        .name("Birthday Default Discount")
                        .usePercentage(true)
                        .isPublished(false)
                        .build()
                );
        defaultDiscount.setDiscountPercentage(discountPercentage);
        discountRepository.save(defaultDiscount);
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
            if (discountAmount != null && discountAmount.compareTo(BigDecimal.ZERO) > 0) {
                throw new IllegalArgumentException(
                        "Khi 'usePercentage' là true, không nên cung cấp 'discountAmount' lớn hơn 0.");
            }
        } else {
            if (discountPercentage != null && discountPercentage.compareTo(BigDecimal.ZERO) > 0) {
                throw new IllegalArgumentException(
                        "Khi 'usePercentage' là false, không nên cung cấp 'discountPercentage' lớn hơn 0.");
            }
        }

    }

    private void validateDiscountNameLength(String discountName) {
        if (discountName.length() > 50) {
            throw new IllegalArgumentException("'Tên' không được vượt quá 50 ký tự.");
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

    private void saveDiscountAppliedToCustomers(Discount discount, List<Long> selectedCustomerIds) {
        initializeUsageCount(discount);
        for (Long customerId : selectedCustomerIds) {
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new NotFoundException("Khách hàng không tìm thấy ID: " + customerId));
            CustomerVoucher customerVoucher = CustomerVoucher.builder()
                    .customer(customer)
                    .discount(discount)
                    .usageCountPerCustomer(0)
                    .build();
            customerVoucherRepository.save(customerVoucher);
        }
    }

    private void initializeUsageCount(Discount discount) {
        if (discount.getUsageCount() == null) {
            discount.setUsageCount(discount.getLimitationTimes());
            discountRepository.save(discount);
        } else if (discount.getUsageCount() <= 0) {
            throw new InvalidDataException("Voucher đã được đổi đủ.");
        }
    }

    @Transactional
    @Override
    public void updateVoucher(Long voucherId, VoucherUpdateRequest request) {
        Discount discount = discountRepository.findById(voucherId)
                .orElseThrow(() -> new NotFoundException("Voucher không tìm thấy với ID: " + voucherId));

        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            validateDiscountNameLength(request.getName());
            discount.setName(request.getName());
        }

        if (request.getStartDate() != null && request.getEndDate() != null) {
            validateDate(request.getStartDate(), request.getEndDate());
            discount.setStartDateUtc(request.getStartDate());
            discount.setEndDateUtc(request.getEndDate());
        }
        if (request.getIsCumulative() != null) {
            discount.setIsCumulative(request.getIsCumulative());
        }

        if (request.getComment() != null && !request.getComment().trim().isEmpty()) {
            discount.setComment(request.getComment());
        }
        if (request.getMaxUsageCount() != null) {
            int currentUsageCount = (discount.getUsageCount() != null) ? discount.getUsageCount() : 0;
            int currentLimitationTimes = getCurrentLimitationTimes(request, discount, currentUsageCount);
            if (request.getMaxUsageCount() != currentLimitationTimes) {
                if (request.getMaxUsageCount() > currentLimitationTimes) {
                    int difference = request.getMaxUsageCount() - currentLimitationTimes;
                    discount.setUsageCount(currentUsageCount + difference);
                }
                discount.setLimitationTimes(request.getMaxUsageCount());
            }
        }

        discountRepository.save(discount);
        log.info("Voucher cập nhật với ID: {}", voucherId);
    }

    @Transactional
    public void updateDefaultBirthdayDiscount(VoucherBirthdayUpdateRequest request) {

        Discount defaultDiscount = discountRepository.findByName("Birthday Default Discount")
                .orElseThrow(() -> new NotFoundException("Giảm giá mặc định sinh nhật không tồn tại."));

        if (request.getDiscountAmount() != null || request.getDiscountPercentage() != null) {
            if (defaultDiscount.getUsePercentage() != null) {
                if (Boolean.TRUE.equals(defaultDiscount.getUsePercentage())) {
                    defaultDiscount.setDiscountAmount(null);
                } else {
                    defaultDiscount.setDiscountPercentage(null);
                }
            }
            if (request.getDiscountAmount() != null) {
                defaultDiscount.setDiscountAmount(request.getDiscountAmount());
                defaultDiscount.setUsePercentage(false);
            }
            if (request.getDiscountPercentage() != null) {
                defaultDiscount.setDiscountPercentage(request.getDiscountPercentage());
                defaultDiscount.setUsePercentage(true);
            }
        }

        if (request.getUsePercentage() != null) {
            defaultDiscount.setUsePercentage(request.getUsePercentage());
        }

        if (request.getMaxDiscountAmount() != null) {
            defaultDiscount.setMaxDiscountAmount(request.getMaxDiscountAmount());
        }

        if (request.getIsCumulative() != null) {
            defaultDiscount.setIsCumulative(request.getIsCumulative());
        }

        if (request.getLimitationTimes() != null) {
            defaultDiscount.setLimitationTimes(request.getLimitationTimes());
        }

        if (request.getPerCustomerLimit() != null) {
            defaultDiscount.setPerCustomerLimit(request.getPerCustomerLimit());
        }

        if (request.getMinOrderAmount() != null) {
            defaultDiscount.setMinOderAmount(request.getMinOrderAmount());
        }

        discountRepository.save(defaultDiscount);
    }


    @Override
    public VoucherFullResponse getVoucherById(Long id) {
        Discount discount = findDiscountById(id);
        updateStatusVoucher(discount, discountRepository);
        List<CustomerVoucher> customerVouchers = customerVoucherRepository.findByDiscount(discount);
        List<CustomerResponse> appliedCustomers = customerVouchers.stream()
                .map(customerVoucher -> {
                    Customer customer = customerVoucher.getCustomer();
                    return CustomerResponse.builder()
                            .id(customer.getId())
                            .firstName(customer.getFirstName())
                            .lastName(customer.getLastName())
                            .email(customer.getEmail())
                            .build();
                })
                .toList();

        VoucherFullResponse response = voucherMapper.toFullResponse(discount);
        response.setAppliedCustomers(appliedCustomers);

        return response;
    }

    private Discount findDiscountById(Long id) {
        return discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DISCOUNT_NOT_FOUND.getMessage()));
    }

    private void validatePrivateVoucherForEmail(String email, Discount discount) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy khách hàng bằng email: " + email));
        boolean isVoucherAssignedToCustomer = customerVoucherRepository.existsByCustomerAndDiscount(customer, discount);
        if (!isVoucherAssignedToCustomer) {
            throw new InvalidDataException("Voucher là riêng tư và không được chỉ định cho email này.");
        }
    }

    private void validateVoucherApplicability(BigDecimal subTotal, Discount discount, Instant now) {
        if (Boolean.TRUE.equals(discount.getIsCanceled())) {
            throw new InvalidDataException("Voucher đã bị hủy.");
        }
        if (discount.getUsageCount() == null || discount.getUsageCount() <= 0) {
            throw new InvalidDataException("Voucher đã được sử dụng hết.");
        }

        if (discount.getEndDateUtc() != null && now.isAfter(discount.getEndDateUtc())) {
            throw new InvalidDataException("Voucher đã hết hạn.");
        }

        if (discount.getStartDateUtc() != null && now.isBefore(discount.getStartDateUtc())) {
            throw new InvalidDataException("Voucher chưa hợp lệ.");
        }

        if (discount.getMinOderAmount() != null &&
                subTotal.compareTo(discount.getMinOderAmount()) < 0) {
            throw new InvalidDataException("Đơn hàng không đáp ứng số tiền tối thiểu cho voucher.");
        }
    }

    private BigDecimal calculateDiscount(BigDecimal subTotal, Discount discount) {
        BigDecimal discountAmount = BigDecimal.ZERO;

        if (discount.getUsePercentage() != null && discount.getUsePercentage()) {
            discountAmount = subTotal
                    .multiply(discount.getDiscountPercentage().divide(BigDecimal.valueOf(100)));
        } else if (discount.getDiscountAmount() != null) {
            discountAmount = discount.getDiscountAmount();
        }
        if (discount.getMaxDiscountAmount() != null) {
            discountAmount = discountAmount.min(discount.getMaxDiscountAmount());
        }
        return discountAmount;
    }

    @Override
    @Transactional
    public VoucherApplyResponseWrapper validateAndCalculateDiscounts(BigDecimal subTotal, List<String> couponCodes, String email) {
        Instant now = Instant.now();
        List<VoucherApplyResponse> responses = new ArrayList<>();
        BigDecimal totalDiscount = BigDecimal.ZERO;
        List<Long> applicableVoucherIds = new ArrayList<>();

        boolean hasNonCumulativeVoucher = false;

        for (String code : couponCodes) {
            VoucherApplyResponse response = VoucherApplyResponse.builder()
                    .couponCode(code)
                    .build();
            try {
                Optional<Discount> discountOptional = discountRepository.findActiveVoucherByCouponCode(code);
                if (discountOptional.isEmpty()) {
                    response.setIsApplicable(false);
                    response.setReason("Không tìm thấy mã phiếu giảm giá hoặc mã đã hết hạn.");
                    responses.add(response);
                    continue;
                }
                Discount discount = discountOptional.get();

                if (Boolean.TRUE.equals(!discount.getIsCumulative()) && !applicableVoucherIds.isEmpty()) {
                    response.setIsApplicable(false);
                    response.setReason("Voucher này không thể kết hợp với những voucher khác.");
                    responses.add(response);
                    continue;
                }

                if (hasNonCumulativeVoucher) {
                    response.setIsApplicable(false);
                    response.setReason("Không thể thêm phiếu giảm giá vì đã áp dụng voucher không tích lũy.");
                    responses.add(response);
                    continue;
                }

                if (Boolean.TRUE.equals(!discount.getIsCumulative())) {
                    hasNonCumulativeVoucher = true;
                }

                if (Boolean.TRUE.equals(discount.getRequiresCouponCode()) && Boolean.TRUE.equals(!discount.getIsPublished())) {
                    if (email != null) {
                        validatePrivateVoucherForEmail(email, discount);
                    } else {
                        response.setIsApplicable(false);
                        response.setReason("Voucher là riêng tư. Vui lòng cung cấp email.");
                        responses.add(response);
                        continue;
                    }
                }

                validateVoucherApplicability(subTotal, discount, now);

                BigDecimal discountAmount = calculateDiscount(subTotal, discount);
                totalDiscount = totalDiscount.add(discountAmount);
                response.setIsApplicable(true);
                response.setDiscountAmount(Boolean.TRUE.equals(discount.getUsePercentage()) ? null : discountAmount);
                response.setDiscountPercent(Boolean.TRUE.equals(discount.getUsePercentage()) ? discount.getDiscountPercentage() : null);
                response.setId(discount.getId());
                response.setMaxDiscountAmount(discount.getMaxDiscountAmount());

                applicableVoucherIds.add(discount.getId());
            } catch (InvalidDataException | NotFoundException e) {
                response.setIsApplicable(false);
                response.setReason(e.getMessage());
            }

            responses.add(response);
        }

        return VoucherApplyResponseWrapper.builder()
                .totalDiscount(totalDiscount)
                .voucherResponses(responses)
                .applicableVoucherIds(applicableVoucherIds)
                .build();
    }

}
