package com.len.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "boke_user_message")
@Data
public class BoKeUserMessage {
    //主键
    @Id
    @Column(name = "id")
    private Integer id;
    //用户ID
    @Column(name = "userId")
    private Integer userId;
    //信息类型
    @Column(name = "type")
    private String type;
    //信息地址
    @Column(name = "url")
    private String url;
    //状态：0 未读 1 已读
    @Column(name = "status")
    private Integer status;
    @Column(name = "formId")
    private String formId;

    @Column(name = "createdAt")
    private Date createdAt;



    @Column(name = "content")
    private String content;
    @Transient
    private Integer replyerId;

    @Transient
    private String createdAtStr;

}
