package com.len.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "boke_article_comment")
@Data
public class ArticleComment {
    //主键
    @Id
    @Column(name = "id")
    private Integer id;

    //文章ID
    @Column(name = "articleId")
    private Integer articleId;

    @Column(name = "replyerId")
    private Integer replyerId; //评论者ID

    @Column(name = "userId")
    private Integer userId; //用户ID


    //评论时间
    @Column(name = "createdAt")
    private Date createdAt;
    //评论的表单ID
    @Column(name = "formId")
    private String formId;
    //评论内容
    @Column(name = "content")
    private String content;


    @Transient
    private String excerpt; //文章简介

    @Transient
    private String title; //标题
    @Transient
    private String replyer; //评论者
    @Transient
    private String userName; //被评论的用户ID



}
