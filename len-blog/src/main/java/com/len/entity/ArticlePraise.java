package com.len.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "boke_article_praise")
@Data
public class ArticlePraise {
    //主键
    @Id
    @Column(name = "id")
    private Integer id;
    //用户ID
    @Column(name = "userId")
    private Integer userId;

    @Column(name = "articleId")
    private Integer articleId;
    //是否点赞：0 点赞，1 取消点赞
    @Column(name = "flag")
    private Integer flag;

    @Column(name = "createdAt")
    private Date createdAt;
    @Transient
    private String excerpt; //文章简介

    @Transient
    private String title; //标题

    @Transient
    private String userName;
}
