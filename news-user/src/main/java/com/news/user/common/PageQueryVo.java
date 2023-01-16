package com.news.user.common;

import lombok.Data;

@Data
public class PageQueryVo {
    private Integer page;
    private Integer pageSize;

    public Integer getPage() {
        if (page == null) {
            page = 1;
        }
        return page;
    }

    public Integer getPageSize() {
        if (pageSize == null) {
            pageSize = 10;
        }
        return pageSize;
    }
}