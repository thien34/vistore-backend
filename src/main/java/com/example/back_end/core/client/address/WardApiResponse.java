package com.example.back_end.core.client.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WardApiResponse {

    @JsonProperty("WardCode")
    private String wardCode;

    @JsonProperty("WardName")
    private String wardName;

    @JsonProperty("DistrictID")
    private String districtId;

}

