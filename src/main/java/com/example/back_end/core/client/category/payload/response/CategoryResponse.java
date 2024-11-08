package com.example.back_end.core.client.category.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {

    private Long id;

    private String slug;

    private String name;

    private String description;

    private String linkImg;

}
