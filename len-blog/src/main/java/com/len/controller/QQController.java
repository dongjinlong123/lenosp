package com.len.controller;

import com.len.entity.WxUser;
import com.len.service.WxUserService;
import com.len.util.JsonUtil;
import com.qq.connect.QQConnectException;
import com.qq.connect.oauth.Oauth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * qq互联接入
 */
@Controller
@Slf4j
@RequestMapping("/qq")
public class QQController {
    @Autowired
    private WxUserService wxUserService;
    @GetMapping(value = "/qqLogin")
    public void qqLogin(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.setContentType("text/html;charset=utf-8");

            String authorizeURL = new Oauth().getAuthorizeURL(req);

            resp.sendRedirect(authorizeURL);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (QQConnectException e) {
            e.printStackTrace();
        }
    }
    @GetMapping(value = "/getQQUserInfo")
    @ResponseBody
    public JsonUtil qQUserInfo(HttpServletRequest req, HttpServletResponse resp) {
        JsonUtil ret = new JsonUtil();
        try{
            ret.setFlag(true);
            String code = req.getParameter("code");
            String accessToken = wxUserService.getAccessToken(code);
            String openId = wxUserService.getOpenId(accessToken);
            WxUser qqUserInfo = wxUserService.getQQUserInfo(accessToken, openId);
            wxUserService.addUser(qqUserInfo);
            ret.setData(qqUserInfo);
            return ret;
        }catch (Exception e){
            log.info("获取用户信息异常："+e.getMessage());
            ret.setFlag(false);
            return ret;
        }
    }
}
