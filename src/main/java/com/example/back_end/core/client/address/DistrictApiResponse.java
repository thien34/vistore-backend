package com.example.back_end.core.client.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistrictApiResponse {

    @JsonProperty("DistrictID")
    private Long districtId;

    @JsonProperty("DistrictName")
    private String districtName;

    @JsonProperty("ProvinceID")
    private Long provinceId;

}

