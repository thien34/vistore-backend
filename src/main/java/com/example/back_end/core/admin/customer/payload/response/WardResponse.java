package com.example.back_end.core.admin.customer.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WardResponse {

    private String code;

    private String nameEn;

    private String districtCode;

}
