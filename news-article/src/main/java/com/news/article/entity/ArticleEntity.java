package com.news.article.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_article")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(1024) not null comment '文章标题'")
    private String title;
    @Column(columnDefinition = "bigint(11) not null comment '创建用户'")
    private Long uid;
    @Column(columnDefinition = "int(2) not null comment '文章类型 0-文章'")
    private Short type;
    @Column(columnDefinition = "int(1) not null comment '逻辑删除 0-否 1-是'")
    private Short deleteFlag;
    @Column(columnDefinition = "bigint(13) comment '创建时间 13位时间戳'")
    private Long createTime;
}
