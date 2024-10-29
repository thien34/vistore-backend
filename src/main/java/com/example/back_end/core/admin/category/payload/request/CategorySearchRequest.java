package com.example.back_end.core.admin.category.payload.request;

import com.example.back_end.core.common.PageRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategorySearchRequest extends PageRequest {

    private String name;

}
