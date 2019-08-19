package com.len.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Table(name = "boke_article")
@Data
public class Article implements Serializable {
    //主键
    @Id
    @Column(name = "id")
    private Integer id;
    //文章标题
    @Column(name = "title")
    private String title;
    //阅读量
    @Column(name = "readCounts")
    private Integer readCounts;
    //简介
    @Column(name = "excerpt")
    private String excerpt;
    //作者
    @Column(name = "author")
    private String author;
    //发布日期
    @Column(name = "createdAt")
    private Date createdAt;
    //类别
    @Transient
    private String category;
    //主图url
    @Column(name = "listPic")
    private String listPic;
    //文章内容
    @Column(name = "mdcontent")
    private String mdcontent;
    //海报分享二维码url
    @Column(name = "shareCode")
    private String shareCode;

    //0:取消置頂,1:置頂
    @Column(name = "topFlag")
    private Integer topFlag;
    @Transient
    private Integer commentCounts;

}
