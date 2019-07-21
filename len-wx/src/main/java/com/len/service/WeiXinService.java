package com.len.service;

import com.len.entity.WxMessage;

import java.util.Map;


public interface WeiXinService {
	/**
	 * 微信参数验证
	 * @param token	令牌
	 * @param timestamp 时间戳
	 * @param nonce	随机数
	 * @param signature 微信加密签名
	 * @return
	 */
	public Boolean checkConnection(String token, String timestamp, String nonce, String signature);
	/**
	 * 根据用户输入的信息返回指定的信息给用户
	 * @param message
	 * @param xmlMap
	 * @return
	 */
	public String getReturnMsg(WxMessage message, Map<String, String> xmlMap);

	/**
	 * 根据code 获取openId
	 * @param code
	 * @return
	 */
	public String getOpenIdByCode(String code);
}
