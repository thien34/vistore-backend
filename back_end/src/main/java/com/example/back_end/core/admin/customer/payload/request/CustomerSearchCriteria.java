package com.example.back_end.core.admin.customer.payload.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.util.List;

@Data
public class CustomerSearchCriteria {

    private String email;

    private String firstName;

    private String lastName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant dateOfBirth;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant registrationDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant registrationDateTo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant lastActivityFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant lastActivityTo;

    private String company;

    private List<Long> customerRoles;

}
