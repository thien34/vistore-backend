package com.example.back_end.core.common;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequest {

    @NotNull
    private int pageNo = 1;

    @NotNull
    private int pageSize = 1000000;

    private String sortBy = "createdDate";

    private String sortDir = "desc";

    private String Keywords;

    private String filters;
}
