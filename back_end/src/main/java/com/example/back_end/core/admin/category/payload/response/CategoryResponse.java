package com.example.back_end.core.admin.category.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {

    private Long id;

    private String name;

    private Long categoryParentId;

}