package com.news.user.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(50) not null comment '账号'")
    private String username;
    @Column(columnDefinition = "varchar(255) not null comment '密码'")
    private String password;
    @Column(columnDefinition = "varchar(11) not null comment '手机号'")
    private String phone;
    @Column(columnDefinition = "varchar(50) comment '邮箱'")
    private String email;
    @Column(columnDefinition = "varchar(32) not null comment 'salt值'")
    private String salt;
    @Column(columnDefinition = "int(1) not null comment '逻辑删除 0-否 1-是'")
    private Short deleteFlag;
    @Column(columnDefinition = "bigint(13) comment '创建时间 13位时间戳'")
    private Long createTime;
    @Column(columnDefinition = "bigint(11) comment '创建人'")
    private Long createBy;
    @Column(columnDefinition = "bigint(13) comment '更新时间 13位时间戳'")
    private Long updateTime;
    @Column(columnDefinition = "bigint(11) comment '修改人'")
    private Long updateBy;
}
