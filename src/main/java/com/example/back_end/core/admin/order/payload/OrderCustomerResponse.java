package com.example.back_end.core.admin.order.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCustomerResponse {
    private Long id;
    private Long customerId;
    private String billId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String delivery;
    private Long orderStatusType;
}
