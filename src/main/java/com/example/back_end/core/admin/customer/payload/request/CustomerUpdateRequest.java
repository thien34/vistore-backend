package com.example.back_end.core.admin.customer.payload.request;

import com.example.back_end.infrastructure.constant.GenderType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerUpdateRequest {

    String firstName;

    String lastName;

    GenderType gender;

    Instant dateOfBirth;

}
