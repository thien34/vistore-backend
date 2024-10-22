package com.example.back_end.core.admin.manufacturer.payload.request;

import com.example.back_end.core.common.PageRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManufacturerSearchRequest extends PageRequest {

    private String name;

}
