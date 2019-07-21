package com.len.entity;

import lombok.Data;

@Data
public class WxMessage {
	/*
	 *按照微信的标准 ：正常的需要小写，因为大写的时候注入可能会出现问题
	 */
	//开发者微信号
	private String ToUserName;
	//关注者
	private String FromUserName;
	//信息创建时间
	private long CreateTime;
	//信息类型
	private String MsgType;	
	//内容
	private String Content;	
	//消息id
	private String MsgId;

}
