package com.example.back_end.core.admin.productAttribute.payload.request;

import com.example.back_end.core.common.PageRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdAttrSearchRequest extends PageRequest {

    private String name;

}
