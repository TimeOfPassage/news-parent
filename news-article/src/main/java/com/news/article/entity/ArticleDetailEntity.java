package com.news.article.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_article_detail")
public class ArticleDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "int(11) not null comment '文章ID'")
    private Long articleId;
    @Column(columnDefinition = "text not null comment '文章内容'")
    private String content;
    @Column(columnDefinition = "text not null comment '文章内容-html'")
    private String contentHtml;
}
