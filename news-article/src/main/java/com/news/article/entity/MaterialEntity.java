package com.news.article.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_material")
public class MaterialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(1024) not null comment '素材url'")
    private String url;
    @Column(columnDefinition = "int(2) not null comment '素材类型 0-图片'")
    private Short type;
    @Column(columnDefinition = "int(11) not null comment '素材所属分类id'")
    private Long categoryId;
    @Column(columnDefinition = "bigint(11) not null comment '创建用户'")
    private Long uid;
    @Column(columnDefinition = "varchar(255) not null comment '素材原名称'")
    private String fileName;
    @Column(columnDefinition = "int(11) not null comment '素材大小'")
    private Long size;
    @Column(columnDefinition = "int(1) not null comment '逻辑删除 0-否 1-是'")
    private Short deleteFlag;
    @Column(columnDefinition = "bigint(13) comment '创建时间 13位时间戳'")
    private Long createTime;
}
