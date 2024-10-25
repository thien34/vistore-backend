package com.example.back_end.core.admin.productTag.payload.request;

import com.example.back_end.core.common.PageRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductTagSearchRequest extends PageRequest {

    private String name = "";

}
