package com.example.back_end.core.client.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProvinceApiResponse {

    @JsonProperty("ProvinceID")
    private Long provinceID;

    @JsonProperty("ProvinceName")
    private String provinceName;
}

