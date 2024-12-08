package com.example.back_end.core.admin.order.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceData {
    private String invoiceNumber;
    private String date;
    private String dueDate;
    private Company company;
    private Client client;
    private List<Item> items;
    private BigDecimal  subtotal;
    private BigDecimal discount;
    private BigDecimal  total;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Company {
        private String name;
        private String logo;
        private String address;
        private String phone;
        private String email;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Client {
        private String name;
        private String address;
        private String email;
        private String phone;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item {
        private String productName;
        private int quantity;
        private BigDecimal rate;
        private BigDecimal amount;

    }

}