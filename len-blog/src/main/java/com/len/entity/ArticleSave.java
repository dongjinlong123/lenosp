package com.len.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "boke_article_save")
@Data
public class ArticleSave {
    //主键
    @Id
    @Column(name = "id")
    private Integer id;
    //用户ID
    @Column(name = "articleId")
    private Integer articleId;
    //是否收藏：0 收藏，1 取消收藏
    @Column(name = "flag")
    private Integer flag;

    @Transient
    private String excerpt; //文章简介

    @Transient
    private String title; //标题

    @Transient
    private String userName;
}
