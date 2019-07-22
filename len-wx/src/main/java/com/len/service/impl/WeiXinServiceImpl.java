package com.len.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.len.common.HttpSendUtil;
import com.len.common.RobotAPI;
import com.len.common.WxMessageUtil;
import com.len.entity.WxMessage;
import com.len.redis.RedisService;
import com.len.service.WeiXinService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;



@Service("weiXinService")
public class WeiXinServiceImpl implements WeiXinService {
	private final Logger logger =LoggerFactory.getLogger(WeiXinServiceImpl.class);

	/**
	 * 小程序的appId 和 secret
	 */
	@Value("${wx.xiaochengxu.appId}")
	private String wxXCXAppId;

	@Value("${wx.xiaochengxu.secret}")
	private String wxXCXSecret;
	@Autowired
	private RedisService redisService;

	/**
	 * 微信平台配置时验证
	 */
	@Override
	public Boolean checkConnection(String token, String timestamp, String nonce, String signature) {
		/*
		 * 1）将token、timestamp、nonce三个参数进行字典序排序
		 * 
		 * 2）将三个参数字符串拼接成一个字符串进行sha1加密
		 * 
		 * 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
		 */
		List<String> list = new ArrayList<String>();
		list.add(token);
		list.add(timestamp);
		list.add(nonce);
		// 排序
		Collections.sort(list);
		StringBuffer sb = new StringBuffer();
		for (String str : list) {
			sb.append(str);
		}
		// sha1加密(使用apache commons-codec.jar包)
		String sha1Str = DigestUtils.shaHex(sb.toString());

		// 对比签名
		boolean flag = sha1Str.equals(signature);
		return flag;
	}

	/**
	 * 根据用户输入的信息返回指定的信息给用户
	 */
	@Override
	public String getReturnMsg(WxMessage message, Map<String, String> xmlMap) {
		Map<String, String> logMap = xmlMap;
		// 将 FromUserName 和 ToUserName 进行调换
		String from = message.getFromUserName();
		String to = message.getToUserName();
		message.setFromUserName(to);
		message.setToUserName(from);
		// 当前时间戳
		message.setCreateTime(System.currentTimeMillis());
		// 返回的信息
		String remsg = getMsgByUserMsg(message, xmlMap);
		return remsg;
	}

	@Override
	public String getOpenIdByCode(String code) {
		//判断code是否在缓存中存在，如果存在则取缓存中的openId
		if(StringUtils.isNotEmpty(redisService.get(code))){
			return redisService.get(code);
		}

		String str  ="https://api.weixin.qq.com/sns/jscode2session?appid="+wxXCXAppId +
				"&secret="+ wxXCXSecret +"&js_code="+code+"&grant_type=authorization_code";
		String ret = HttpSendUtil.get(str);
		try{
			String openId = JSONObject.parseObject(ret).getString("openid");
			if(StringUtils.isNotEmpty(openId)){
				redisService.set(code,openId,86400l); //默认存储一天
			}
			return openId;
		}catch (Exception e){
			return null;
		}

	}

	/**
	 * 根据用户的信息返回对应的内容及类型
	 * 
	 * @param message
	 * @param xmlMap
	 */
	private String getMsgByUserMsg(WxMessage message, Map<String, String> xmlMap) {
		// 获取用户输入的内容
		String content = message.getContent();
		content = null == content ? "" : content.trim();
		logger.info("用户输入的信息为:" + content);
		// 获取消息类型
		String type = xmlMap.get("MsgType");
		// 回复的XML内容
		String remsg = "";
		// 根据类型判断属于那种文件类型
		switch (type) {
		/* 针对文本回复的内容 */
		case WxMessageUtil.TEXT:
			remsg = reText(content, message);
			break;
		/* 针对事件 */
		case WxMessageUtil.EVENT:
			remsg = reEven(xmlMap, message);
			break;
		/* 针对图片自动回复发的图片 */
		case WxMessageUtil.IMAGE:
			remsg = reImage(xmlMap, message);
			break;
		/*针对语音自动回复*/
		case WxMessageUtil.VOICE:
			logger.info("识别语音待开发");
			//解析语音中的信息
			remsg = reText(xmlMap.get("Recognition"), message);
			break;
		/* 其他情况 */
		default:
			remsg = reDefault(message);
			break;
		}
		
		return remsg;
	}
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	/**
	 * 针对其他情况
	 * 
	 * @param message
	 * @return
	 */
	private String reDefault(WxMessage message) {
		String msg = "不明信息，无法确认!";
		// message 转化为XML
		message.setContent(msg);
		message.setMsgType(WxMessageUtil.TEXT);
		String xmlStr = WxMessageUtil.toXml(message);
		logger.info("回复信息:" + xmlStr);
		return xmlStr;
	}

	/**
	 * 针对图片自动回复发的图片
	 * 
	 * @param xmlMap
	 * @return
	 */
	private String reImage(Map<String, String> xmlMap, WxMessage message) {
		// 微信系统产生的id 用于标记该图片
		String MediaId = xmlMap.get("MediaId");
		// 将message 先转化为XML字符串
		String xmlStr = WxMessageUtil.toXml(message);
		logger.info("转换前的XML信息:" + xmlStr);
		// 解析XML
		SAXReader reader = new SAXReader();
		// 获取文档
		Document doc = null;
		// 返回的信息
		String remsg = "";
		try {
			doc = DocumentHelper.parseText(xmlStr);
			// 获取根节点
			Element rootElt = doc.getRootElement();
			rootElt.addElement("Image");
			Element image = rootElt.element("Image");
			image.addElement("MediaId");
			Element mediaId = image.element("MediaId");
			mediaId.addText(xmlMap.get("MediaId"));
			// 转换后的XML信息
			remsg = rootElt.asXML();
			logger.info("转换后的XML信息:" + remsg);
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		return remsg;
	}

	/**
	 * 针对取消关注的事件回复内容
	 * 
	 * @param xmlMap
	 * @return
	 */
	private String reEven(Map<String, String> xmlMap, WxMessage message) {
		String reply = "";
		// 获取事件类型
		String eventType = xmlMap.get("Event").toLowerCase();
		if(WxMessageUtil.VIEW.equalsIgnoreCase(eventType)){
			// 用户点击了VIEW菜单
			logger.info("用户点击了菜单！");

			return null;
		}
		// 关注事件
		if (WxMessageUtil.SUBSCRIBE.equalsIgnoreCase(eventType)) {
		
			reply = "谢谢您的关注，祝您每天都有一个好心情^…^";
		} else if (WxMessageUtil.UNSUBSCRIBE.equalsIgnoreCase(eventType)) {
			// 退出了不可能再发信息
			reply = "真的要残忍离开我吗@__@";
			logger.info("用户取消了关注！");
		}
		message.setContent(reply);
		// 回复的时候需要转换类型，因为回复的是文本信息
		message.setMsgType(WxMessageUtil.TEXT);
		// message 转化为XML
		String xmlStr = WxMessageUtil.toXml(message);
		logger.info("回复信息:" + xmlStr);
		return xmlStr;
	}

	/**
	 * 针对文本回复的内容
	 */
	private String reText(String content, WxMessage message) {
		/**
		 * 返回的XML信息
		 */
		String remsg = "";
		
		/* 输入城市名称查询天气信息 */
		/*if ("".equals(remsg)) {
			remsg = getWeather(content);
		}*/
		
		//如果不能满足以上的信息让机器人来回答
		remsg = RobotAPI.getRobot(content);

		if("".equals(remsg)){
			remsg = "你说什么，我听不懂@__@";
		}
		if(remsg.length() >= 1000 ) {
			remsg.substring(0, 1000);
		}
		message.setContent(remsg);
		message.setMsgType(WxMessageUtil.TEXT);
		// message 转化为XML
		String xmlStr = WxMessageUtil.toXml(message);
		logger.info("回复信息:" + xmlStr);
		return xmlStr;
	}
	

}
