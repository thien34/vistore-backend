package com.example.back_end.core.common;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
public class PageListResponse<T> implements Serializable {

    private int page;

    private int size;

    private long totalPage;

    private transient List<T> datas;

}