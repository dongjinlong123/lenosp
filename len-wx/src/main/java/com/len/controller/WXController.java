package com.len.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.len.base.BaseController;
import com.len.common.WxMessageUtil;
import com.len.entity.WxMessage;
import com.len.service.WeiXinService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@Controller
@Slf4j
@RequestMapping("/weixin")
public class WXController extends BaseController  {

    @Value("${wx.token}")
    private String TOKEN;

    private final Logger logger = LoggerFactory.getLogger(WXController.class);
    @Autowired
    private WeiXinService weiXinService;


    @GetMapping(value = "/test")
    @ResponseBody
    public  String test(){
        return TOKEN;
    }
    /**
     * 微信公众平台验证通过向配置的url传递get方法
     *
     * @throws IOException
     */
    @GetMapping(value = "/wx")
    public void getWxConnection(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //设置字符集
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter p = resp.getWriter();
        //2.接入:后台程序和微信公众平台进行联系
        connect(req, p);
    }

    /**
     * 接入验证,响应echostr 随机字符串
     *
     * @param req
     */
    private void connect(HttpServletRequest req, PrintWriter p) {
        //1.微信端传递的参数
        /*
         * signature:	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
         * timestamp: 	时间戳
         * nonce:		随机数
         * echostr:		随机字符串
         * */
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");

        //2.校验
        boolean flag = weiXinService.checkConnection(TOKEN, timestamp, nonce, signature);
        if (flag) {
            logger.info("恭喜接入成功");
            //返回给微信端，随机字符串
            p.print(echostr);
            p.flush();
        } else {
            logger.info("接入失败");
        }
    }

    /**
     * 获取微信端传递的信息并返回
     *
     * @param req
     * @param resp
     */
    @PostMapping(value = "/wx")
    public void WxMsg(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		/*
		  1.获取XML数据
		  2.解析XML数据-> 存放map中->map中存放XML数据 key-value
		  3.XML转换为对象进行存取
		  4.将对象中的属性 FromUserName 和  ToUserName 进行调换，更改时间，发送不同的内容
		  5.将对象再转为XML
		  6.response 发送出去
		 */
        //设置字符集
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        Map<String, String> xmlMap = new HashMap<String, String>();
        //得到req的输入流转化为Map，MessageUtil为dom4j.jar的方法
        xmlMap = WxMessageUtil.toMap(req.getInputStream());
        //将Map对象转化为Msessage对象
        WxMessage message = WxMessageUtil.toMessage(xmlMap);
        //获取返回XML
        String reMsgXML = weiXinService.getReturnMsg(message, xmlMap);
        if (!StringUtils.isEmpty(reMsgXML)) {
            PrintWriter p = resp.getWriter();
            p.print(reMsgXML);
            p.flush();
        }
    }
}
