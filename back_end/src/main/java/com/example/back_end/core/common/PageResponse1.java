package com.example.back_end.core.common;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class PageResponse1<T> implements Serializable {

    private long totalItems;

    private long totalPages;

    private T items;

}
