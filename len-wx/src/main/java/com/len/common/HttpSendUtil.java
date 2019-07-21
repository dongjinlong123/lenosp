package com.len.common;

import java.io.UnsupportedEncodingException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HttpSendUtil {
    private final static Logger logger = LoggerFactory.getLogger(HttpSendUtil.class);

    /**
     * post请求API
     *
     * @param url
     * @param param json格式字符串
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String post(String url, String param) throws UnsupportedEncodingException {
        logger.info("传递的参数为:" + param);
        RestTemplate restTemplate = new RestTemplate();
        /* 注意：必须 http、https……开头 */
        HttpHeaders headers = new HttpHeaders();
        /* 解决中文乱码的关键，设置请求体格式 */

        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        JSONObject jsonObj = JSON.parseObject(param);
        HttpEntity<String> entity = new HttpEntity<String>(jsonObj.toString(), headers);
        /* body是Http消息体例如json串 */
        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        String resultStr = new String(resp.getBody().getBytes("ISO-8859-1"), "UTF-8");
        logger.info("调用" + url + "返回的信息:" + resultStr);
        return resultStr;
    }

    /**
     * get请求API
     *
     * @param url
     * @return
     */
    public static String get(String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        /*设置请求数据类型*/
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity entity = new HttpEntity("", headers);
        /* body是Http消息体例如json串 */
        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String resultStr = resp.getBody();
        logger.info("调用" + url + "返回的信息:" + resultStr);
        return resultStr;
    }

    public static void main(String[] args) {
//			String str  ="https://api.weixin.qq.com/sns/jscode2session?appid=wx6959c49a70d52bf6"+
//					"&secret=6cffcf8a55680b88f2079ca02c470a93&js_code=0719hRnh0bU4Fv1flZjh0qNGnh09hRn7&grant_type=authorization_code";
//			get(str);

		//String rawData =
    }
}
