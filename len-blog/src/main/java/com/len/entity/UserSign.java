package com.len.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "boke_user_sign")
@Data
public class UserSign {
    //主键
    @Id
    @Column(name = "id")
    private Integer id;
    //微信用户ID
    @Column(name = "wxUserId")
    private Integer wxUserId;
    //签到时间
    @Column(name = "createdAt")
    private Date createdAt;

    @Transient
    private String userName;
}
