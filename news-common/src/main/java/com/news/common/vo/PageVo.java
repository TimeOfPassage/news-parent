package com.news.common.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageVo<T> {
    private int page;
    private int pageSize;
    private int pages;
    private long total;
    private List<T> content;
}
