package com.len.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import com.len.entity.WxMessage;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;


/**
 * 微信信息转换工具类
 * 针对微信公众号
 * @author
 *
 */
@Component
public class WxMessageUtil {
	private final static Logger logger =LoggerFactory.getLogger(WxMessageUtil.class);
	/**
	 * 常用微信传输类型
	 */
	//1.文本信息类型
	public static final String TEXT = "text";
	//2.事件推送
	public static final String EVENT = "event";
	//2-1.关注
	public static final String SUBSCRIBE = "subscribe";
	//2-2.取消
	public static final String UNSUBSCRIBE = "unsubscribe";
	
	//3.图片信息
	public static final String IMAGE = "image";
	//4.语音信息
	public static final String VOICE ="voice";
	//5.视频信息
	public static final String VIDEO ="video";
	//6.小视频信息 shortvideo
	public static final String SHORTVIDEO ="shortvideo";
	//7.地理位置信息 
	public static final String LOCATION ="location";
	//8.链接信息
	public static final String LINK="link";
	//9.菜单点击CLICK VIEW
	public static final String CLICK="click";
	public static final String VIEW="VIEW";
	//10.获取access_token 每2小时会失效
	public static String ACCESS_TOKEN;
	// appId
	public final static String APPID = "wxb2575c2a7bc5f239";
	//appSecret
	public final static String APPSECRET = "603938c3e390a093ab1162b3fb37f4f3";
	// h5 使用的 sapi_ticket
	public static String JSAPI_TICKET;
	/**
	 * 获取ACCESS_TOKEN 的url
	 */
	private final static String AT_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
	
	
	//获取ACCESS_TOKEN 方法
	public static void getAccessToken() {
		StringBuffer sb = new StringBuffer(AT_URL);
		sb.append("&appid=").append(APPID).append("&secret=").append(APPSECRET);
		String url =sb.toString();
		logger.info("获取的链接"+url);
		String result = HttpSendUtil.get(url);
		JSONObject jb = JSON.parseObject(result);
		ACCESS_TOKEN = jb.getString("access_token");
		if(StringUtils.isEmpty(ACCESS_TOKEN)) {
			logger.info("获取 access_token 异常");
		}
	}
	//获取小程序的ACCESS_TOKEN 方法
	public static String XCX_ACCESS_TOKEN;
	public static void getXCXAccessToken() {
		StringBuffer sb = new StringBuffer(AT_URL);
		sb.append("&appid=").append("wx6959c49a70d52bf6").append("&secret=")
				.append("6cffcf8a55680b88f2079ca02c470a93");
		String url =sb.toString();
		logger.info("获取的链接"+url);
		String result = HttpSendUtil.get(url);
		JSONObject jb = JSON.parseObject(result);
		XCX_ACCESS_TOKEN = jb.getString("access_token");
		if(StringUtils.isEmpty(XCX_ACCESS_TOKEN)) {
			logger.info("获取 access_token 异常");
		}
	}
	/**
	 * 将request 接收到的XML格式数据转化为Map
	 * @param inputStream
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static Map<String, String> toMap(InputStream inputStream) throws DocumentException, IOException {

		//获取SAXReader:dom4j-1.6.1.jar 包下的方法
		SAXReader reader = new SAXReader();
		//获取文档
		Document doc = reader.read(inputStream);
		//获取根元素
		Element root = doc.getRootElement();
		
		@SuppressWarnings("unchecked")
		List<Element> elemetns = root.elements();
		//遍历封装到map集合
		Map<String,String> map = new HashMap<String,String> ();
		for(Element e: elemetns){
			map.put(e.getName(), e.getText());
		}
		//关闭流
		inputStream.close();
		return map;
	}
	/**
	 * 将Map转化为对象
	 * @param xmlMap
	 * @return
	 */
	public static WxMessage toMessage(Map<String, String> xmlMap) {
		WxMessage message = new WxMessage();
		message.setMsgType(xmlMap.get("MsgType"));
		message.setFromUserName(xmlMap.get("FromUserName"));
		message.setCreateTime(Integer.valueOf(xmlMap.get("CreateTime")));
		message.setMsgId(xmlMap.get("MsgId"));
		message.setContent(xmlMap.get("Content"));
		message.setToUserName(xmlMap.get("ToUserName"));
		return message;
	}
	/**
	 * 将Message 对象转化为XML
	 * @param message
	 * @return
	 */
	public static String toXml(WxMessage message) {
		//获取XStream:xstream-1.3.1.jar包下的方法
		XStream stream = new XStream();
		//使用别名，作为根元素，否则根元素为报名.类名 即替换根元素名称
		stream.alias("xml", message.getClass());
		return stream.toXML(message);
	}
	/**
	 * 菜单功能初始化
	 * @return
	 */
	public static void initMenu(String menuStr){
		logger.info("菜单的json 数据:"+menuStr);
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+ACCESS_TOKEN;
		try {
			HttpSendUtil.post(url, menuStr);
		} catch (UnsupportedEncodingException e) {
			logger.info("初始化菜单失败......");
		}
	}	
	/**
	 * 初始化jsapi_ticket
	 */
	public static void getJsapiTicket() {
		String url ="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ACCESS_TOKEN+"&type=jsapi";
		String result = HttpSendUtil.get(url);
		JSONObject jb = JSON.parseObject(result);
		JSAPI_TICKET = jb.getString("ticket");
		if(StringUtils.isEmpty(JSAPI_TICKET)) {
			logger.info("获取 jsapi_ticket 异常");
		}
	}
	/**
	 * 自定义长度随机字符串
	 * @param length
	 * @return
	 */
	public static String createConceStr(int length) {
		String strs = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String str = "";
		for (int i = 0; i < length; i++) {
			char achar = strs.charAt(new Random().nextInt(strs.length() - 1));
			str += achar;
		}
		return str;
	}
	/**
	 * 获取当前系统的时间戳 9 位
	 * @return
	 */
	public static String getTimeStamp() {
		int time = (int) (System.currentTimeMillis() / 1000);
		return String.valueOf(time);
	}
	/**
	 * 创建sha1签名
	 * 规则： map 中所有的key 排序，然后拼接字符串，sha1加密
	 * @param map
	 * @return
	 */
	public  static String createSignSha1(Map<String, String> map) {
		List<String> list = new ArrayList<String>();
		Set<Entry<String, String>> set = map.entrySet();
		Iterator<Entry<String, String>> it= set.iterator();
		while(it.hasNext()) {
			list.add(it.next().getKey());
		}
		// 排序
		Collections.sort(list);
		StringBuffer sb = new StringBuffer();
		for (String key : list) {
			//所有参数名必须小写
			sb.append(key.toLowerCase()).append("=").append(map.get(key)).append("&");
		}
		String str = sb.toString().substring(0, sb.toString().length()-1);
		System.out.println("解密生产signature 的字符串"+str);
		// sha1加密(使用apache commons-codec.jar包)
		String signature = DigestUtils.shaHex(str);
		return signature;
	}

}
