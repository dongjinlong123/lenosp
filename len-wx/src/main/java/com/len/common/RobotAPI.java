package com.len.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class RobotAPI {
	private final static Logger logger =LoggerFactory.getLogger(RobotAPI.class);
	/**
	 * 得到机器人的回复信息
	 * @param content
	 * @return
	 */
	public static String getRobot(String content) {
		StringBuffer sb = new StringBuffer();
		try {
			String url = "http://www.tuling123.com/openapi/api";
			/**
			 * 返回为json数据格式字符串
			 */
			String param = "{\"key\":\"41c381b80f444194a5145f3b857ab6e1\",\"userid\":\"12345678\",\"info\":\""+content+"\"}";
			String jsonStr = HttpSendUtil.post(url,param);
			logger.info("返回的json信息为:" + jsonStr);
			JSONObject jobject = JSON.parseObject(jsonStr);

			if ((int)jobject.get("code") == 100000) {
				//文本类
				sb.append(jobject.get("text"));
			}else if((int)jobject.get("code") == 200000){
				//链接类
				sb.append(jobject.get("text")).append("\n地址如下：").append(jobject.get("url"));
			}else if((int)jobject.get("code") == 302000){
				//新闻类
				sb.append(jobject.get("text")).append("\n");
				JSONArray jarr = jobject.getJSONArray("list");
				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jb = jarr.getJSONObject(i);
					sb.append("来源：").append(jb.get("source")).append("\n").append("标题：").append(jb.get("article")).append("\n")
					.append("链接：").append(jb.get("detailurl")).append("\n");
				}		
			}else if((int)jobject.get("code") ==308000){
				//菜谱类
				sb.append(jobject.get("text")).append("\n");
				JSONArray jarr = jobject.getJSONArray("list");
				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jb = jarr.getJSONObject(i);
					sb.append("名称：").append(jb.get("name")).append("\n").append("菜谱信息：").append(jb.get("info")).append("\n")
					.append("链接：").append(jb.get("detailurl")).append("\n");
				}		
			}
		} catch (Exception e) {
			logger.info("机器人自动回复接口异常！"+e.fillInStackTrace());
			return "";
		}

		return sb.toString();
	}
	/**
	 * 输入城市名称查询天气信息 
	 */
	private static String  getWeather(String content) {
		StringBuffer sb = new StringBuffer();
		try {
			String url = "http://www.sojson.com/open/api/weather/json.shtml?city=" + content;
			/**
			 * 返回为json数据格式字符串
			 */
			String jsonStr = HttpSendUtil.get(url);
			logger.info("返回的json信息为:" + jsonStr);
			JSONObject jobject = JSON.parseObject(jsonStr);

			if ((int)jobject.get("status") == 200) {
				sb.append("查询城市:").append(content);
				JSONObject data = (JSONObject) jobject.get("data");
				JSONArray jarr = data.getJSONArray("forecast");
				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jb = jarr.getJSONObject(i);
					sb.append("\n").append("日期：").append(jb.get("date")).append("\n").append("天气：").append(jb.get("type")).append("\n")
					.append("最高温度：").append(jb.get("high")).append("\n").append("最低温度：").append(jb.get("low")).append("\n");
				}
			}
		} catch (Exception e) {
			logger.info("调用天气查询接口异常！");
			return "";
		}

		return sb.toString();
	}
}
