package com.news.article.vo;

import com.news.article.common.PageQueryVo;
import lombok.Data;

@Data
public class DocumentVo extends PageQueryVo {
    private Long id;
    private String title;
    private String content;
    private String contentHtml;
}
