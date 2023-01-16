package com.news.article.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(50) not null comment '分类名'")
    private String name;
    @Column(columnDefinition = "bigint(11) not null comment '创建用户'")
    private Long uid;
    @Column(columnDefinition = "bigint(13) comment '创建时间 13位时间戳'")
    private Long createTime;
}
