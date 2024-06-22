package com.example.back_end.core.common;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class PageResponse<T> implements Serializable {

    private int page;

    private int size;

    private long totalPage;

    private T items;

}