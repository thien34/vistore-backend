package com.example.back_end.core.admin.customer.payload.request;

import com.example.back_end.core.common.PageRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class CustomerSearchRequest extends PageRequest {

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

    private List<Long> customerRoles;

}
