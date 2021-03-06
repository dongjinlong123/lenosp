package com.len.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Table(name = "boke_wx_user")
@Data
public class WxUser implements Serializable {
    //主键
    @Id
    @Column(name = "id")
    private Integer id;
    //用户openId
    @Column(name = "userPic")
    private String userPic;

    @Column(name = "openId")
    private String openId;
    //用户昵称
    @Column(name = "nickName")
    private String nickName;
    //性别 0：未知、1：男、2：女
    @Column(name = "gender")
    private String gender;
    //省份
    @Column(name = "province")
    private String province;
    //城市
    @Column(name = "city")
    private String city;
    //区域
    @Column(name = "country")
    private String country;

    @Column(name = "createTime")
    private Date createTime;

    @Transient
    private String rawData;
    @Transient
    private String signature;
    @Transient
    private String encryptedData;
    @Transient
    private String iv;
    @Transient
    private String code;
}
