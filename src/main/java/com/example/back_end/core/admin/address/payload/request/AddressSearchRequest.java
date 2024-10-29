package com.example.back_end.core.admin.address.payload.request;

import com.example.back_end.core.common.PageRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressSearchRequest extends PageRequest {

    private Long customerId;

}
