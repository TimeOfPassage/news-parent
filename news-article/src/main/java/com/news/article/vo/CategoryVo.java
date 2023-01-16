package com.news.article.vo;

import com.news.article.common.PageQueryVo;
import lombok.Data;

@Data
public class CategoryVo extends PageQueryVo {

    private Long id;
    private String name;
}
