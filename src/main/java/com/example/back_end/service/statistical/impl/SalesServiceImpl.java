package com.example.back_end.service.statistical.impl;

import com.example.back_end.core.admin.statistical.payload.AllSalesResponse;
import com.example.back_end.core.admin.statistical.payload.DynamicResponse;
import com.example.back_end.core.admin.statistical.payload.GrowthRate;
import com.example.back_end.core.admin.statistical.payload.GrowthRateResponse;
import com.example.back_end.core.admin.statistical.payload.OrderStatusSummaryResponse;
import com.example.back_end.core.admin.statistical.payload.ProductOutOfStockResponse;
import com.example.back_end.core.admin.statistical.payload.ProductSaleResponse;
import com.example.back_end.core.admin.statistical.payload.SalesResponse;
import com.example.back_end.core.admin.statistical.payload.TotalRevenueResponse;
import com.example.back_end.entity.Order;
import com.example.back_end.entity.OrderItem;
import com.example.back_end.infrastructure.constant.OrderStatusTranslator;
import com.example.back_end.infrastructure.constant.OrderStatusType;
import com.example.back_end.infrastructure.constant.PaymentStatusType;
import com.example.back_end.repository.OrderRepository;
import com.example.back_end.repository.ProductRepository;
import com.example.back_end.service.statistical.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public SalesResponse getTodaySales() {
        Instant startOfDay = Instant.now().truncatedTo(ChronoUnit.DAYS);
        Instant endOfDay = startOfDay.plus(1, ChronoUnit.DAYS);
        return getSalesResponse(startOfDay, endOfDay);
    }

    private SalesResponse getSalesResponse(Instant startOfDay, Instant endOfDay) {
        List<Order> todayOrders = orderRepository.findAllByPaidDateUtcBetween(startOfDay, endOfDay);
        BigDecimal totalRevenue = todayOrders.stream()
                .map(Order::getOrderTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        int successfulOrders = (int) todayOrders.stream()
                .filter(order -> order.getPaymentStatusId() == PaymentStatusType.PAID)
                .count();
        int cancelledOrders = (int) todayOrders.stream()
                .filter(order -> order.getPaymentStatusId() == PaymentStatusType.CANCELLED)
                .count();
        int totalProducts = todayOrders.stream()
                .mapToInt(order -> order.getOrderItems().stream()
                        .mapToInt(OrderItem::getQuantity)
                        .sum())
                .sum();
        return new SalesResponse(todayOrders.size(), totalRevenue, successfulOrders, cancelledOrders, totalProducts);
    }

    public SalesResponse getWeekSales() {
        Instant startOfWeek = LocalDate.now()
                .minusDays(LocalDate.now().getDayOfWeek().getValue() - 1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
        Instant endOfWeek = startOfWeek.plus(7, ChronoUnit.DAYS);
        return getSalesResponse(startOfWeek, endOfWeek);
    }

    public SalesResponse getThisMonthSales() {
        Instant startOfMonth = LocalDate.now()
                .withDayOfMonth(1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
        Instant endOfMonth = LocalDate.now()
                .plusMonths(1)
                .withDayOfMonth(1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
        return getSalesResponse(startOfMonth, endOfMonth);
    }

    public SalesResponse getThisYearSales() {
        Instant startOfYear = LocalDate.now()
                .withDayOfYear(1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
        Instant endOfYear = LocalDate.now()
                .plusYears(1)
                .withDayOfYear(1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
        return getSalesResponse(startOfYear, endOfYear);
    }

    @Override
    public AllSalesResponse getAllSales() {
        SalesResponse todaySales = getTodaySales();
        SalesResponse weekSales = getWeekSales();
        SalesResponse monthSales = getThisMonthSales();
        SalesResponse yearSales = getThisYearSales();
        GrowthRateResponse growthRateResponse = getGrowthRate();
        return new AllSalesResponse(todaySales, weekSales, monthSales, yearSales, growthRateResponse);
    }

    private GrowthRate calculateGrowthRate(SalesResponse current, SalesResponse previous) {
        BigDecimal revenueGrowthRate = calculateGrowth(current.getTotalRevenue(), previous.getTotalRevenue());
        BigDecimal orderGrowthRate = calculateGrowth(new BigDecimal(current.getTotalInvoices()), new BigDecimal(previous.getTotalInvoices()));
        BigDecimal productGrowthRate = calculateGrowth(new BigDecimal(current.getTotalProducts()), new BigDecimal(previous.getTotalProducts()));

        return new GrowthRate(revenueGrowthRate, orderGrowthRate, productGrowthRate);
    }

    private BigDecimal calculateGrowth(BigDecimal current, BigDecimal previous) {
        if (previous.compareTo(BigDecimal.ZERO) == 0) {
            return current.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : BigDecimal.valueOf(100);
        }
        return current.subtract(previous).divide(previous, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
    }

    public GrowthRateResponse getGrowthRate() {
        SalesResponse todaySales = getTodaySales();
        SalesResponse weekSales = getWeekSales();
        SalesResponse monthSales = getThisMonthSales();
        SalesResponse yearSales = getThisYearSales();
        GrowthRate todayGrowthRate = calculateGrowthRate(todaySales, getPreviousSales("day"));
        GrowthRate weekGrowthRate = calculateGrowthRate(weekSales, getPreviousSales("week"));
        GrowthRate monthGrowthRate = calculateGrowthRate(monthSales, getPreviousSales("month"));
        GrowthRate yearGrowthRate = calculateGrowthRate(yearSales, getPreviousSales("year"));

        return new GrowthRateResponse(todayGrowthRate, weekGrowthRate, monthGrowthRate, yearGrowthRate);
    }

    private SalesResponse getPreviousSales(String period) {
        return switch (period) {
            case "day" -> getYesterdaySales();
            case "week" -> getLastWeekSales();
            case "month" -> getLastMonthSales();
            case "year" -> getLastYearSales();
            default -> new SalesResponse(0, BigDecimal.ZERO, 0, 0, 0);
        };
    }

    private SalesResponse getLastMonthSales() {
        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
        Instant startOfLastMonth = lastMonth.toLocalDate().withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endOfLastMonth = lastMonth.plusMonths(1).toLocalDate().withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

        return getSalesResponse(startOfLastMonth, endOfLastMonth);
    }

    private SalesResponse getLastYearSales() {
        LocalDateTime lastYear = LocalDateTime.now().minusYears(1);
        Instant startOfLastYear = lastYear.toLocalDate().withDayOfYear(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endOfLastYear = lastYear.plusYears(1).toLocalDate().withDayOfYear(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

        return getSalesResponse(startOfLastYear, endOfLastYear);
    }

    private SalesResponse getYesterdaySales() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        Instant startOfYesterday = yesterday.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endOfYesterday = yesterday.plusDays(1).toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant();

        return getSalesResponse(startOfYesterday, endOfYesterday);
    }

    private SalesResponse getLastWeekSales() {
        LocalDateTime lastWeek = LocalDateTime.now().minusWeeks(1);
        Instant startOfLastWeek = lastWeek.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endOfLastWeek = lastWeek.plusWeeks(1).toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant();

        return getSalesResponse(startOfLastWeek, endOfLastWeek);
    }

    @Override
    public DynamicResponse getSalesSummary(Instant startDate, Instant endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Start date must not be null");
        }
        if (endDate == null) {
            endDate = Instant.now();
        }

        List<Order> orders = orderRepository.findAllByPaidDateUtcBetween(startDate, endDate);
        long totalOrders = orders.size();

        BigDecimal totalRevenue = orders.stream()
                .map(Order::getOrderTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalProducts = orders.stream()
                .mapToInt(order -> order.getOrderItems().stream()
                        .mapToInt(OrderItem::getQuantity)
                        .sum())
                .sum();

        int successfulOrders = (int) orders.stream()
                .filter(order -> order.getPaymentStatusId() == PaymentStatusType.PAID)
                .count();

        int cancelledOrders = (int) orders.stream()
                .filter(order -> order.getPaymentStatusId() == PaymentStatusType.CANCELLED)
                .count();

        List<ProductSaleResponse> bestSellingProducts = productRepository.findTopSellingProducts(startDate, endDate);

        List<ProductOutOfStockResponse> outOfStockProducts = productRepository.findOutOfStockProducts().stream()
                .map(product -> new ProductOutOfStockResponse(
                        product.getName(),
                        product.getQuantity(),
                        product.getUnitPrice(),
                        product.getImage()))
                .toList();

        List<OrderStatusSummaryResponse> orderStatusSummary = Arrays.stream(OrderStatusType.values())
                .map(status -> {
                    long count = orders.stream().filter(order -> order.getOrderStatusId() == status).count();
                    return new OrderStatusSummaryResponse(
                            OrderStatusTranslator.translate(status),
                            count,
                            calculatePercentage(count, totalOrders)
                    );
                })
                .toList();

        TotalRevenueResponse revenueResponse = new TotalRevenueResponse(
                totalRevenue,
                totalProducts,
                successfulOrders,
                cancelledOrders
        );

        DynamicResponse response = new DynamicResponse();
        response.setTotalRevenue(revenueResponse);
        response.setBestSellingProducts(bestSellingProducts);
        response.setOutOfStockProducts(outOfStockProducts);
        response.setOrderStatusChart(orderStatusSummary);

        return response;
    }

    private double calculatePercentage(long count, long total) {
        return total == 0 ? 0 : (double) count / total * 100;
    }

}
