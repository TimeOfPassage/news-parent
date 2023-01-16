package com.news.article.vo;

import com.news.article.common.PageQueryVo;
import lombok.Data;

@Data
public class MaterialVo extends PageQueryVo {
    private Long id;
    private Long categoryId;
    private String url;
}
