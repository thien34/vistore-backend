package com.example.back_end.core.admin.category.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryNameResponse {

    private Long id;

    private String name;

    private List<CategoryNameResponse> children = new ArrayList<>();

}
