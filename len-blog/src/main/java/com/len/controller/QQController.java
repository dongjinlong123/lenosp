package com.len.controller;

import com.len.entity.SysUser;
import com.len.entity.WxUser;
import com.len.redis.RedisService;
import com.len.service.BokeIntfService;
import com.len.service.WxUserService;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import com.qq.connect.utils.QQConnectConfig;
import com.qq.connect.utils.http.PostParameter;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * qq互联接入
 */
@CrossOrigin
@Controller
@Slf4j
@RequestMapping("/qq")
public class QQController {
    @Autowired
    private WxUserService wxUserService;
    @Autowired
    private RedisService redisService;

    @GetMapping(value = "/checkSession")
    @ResponseBody
    public Map<String, Object> checkSession(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        HttpSession session = req.getSession();
        //判断会话中是否存在用户的登录信息
        WxUser user = (WxUser) session.getAttribute("qqUser");
        if (user != null) {
            result.put("userInfo", user);
            result.put("login", true);
        } else {
            result.put("login", false);
        }
        return result;
    }


    @GetMapping(value = "/qqLogin")
    public void qqLogin(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        String url = req.getParameter("callBackUrl");
        //得到参数中跳转的url
        log.info("得到的参数url:" + url + " == sessionId" + session.getId());
        session.setAttribute("callBackUrl", url);

        //判断会话中是否存在用户的登录信息
        WxUser user = (WxUser) session.getAttribute("qqUser");
        try {
            resp.setContentType("text/html;charset=utf-8");
            if (user != null) {
                resp.sendRedirect(url);
                return;
            }
            String authorizeURL = new Oauth().getAuthorizeURL(req);
            String state = (String) session.getAttribute("qq_connect_state");
            redisService.set(state,url,3600l);

            log.info("authorizeURL:{}", authorizeURL);
            resp.sendRedirect(authorizeURL);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (QQConnectException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/callback")
    public void callback(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String url = "";
        try {
            log.info(req.getSession().getAttribute("qq_connect_state")+"==query:" + req.getQueryString());
            String state = extractionAuthCodeFromUrl(req.getQueryString())[1];
            req.getSession().setAttribute("qq_connect_state", state);

            HttpSession session = req.getSession();
            //得到参数中跳转的url
            url = (String) session.getAttribute("callBackUrl");
            if(StringUtils.isEmpty(url)){
                url = redisService.get(state);
            }
            log.info( "sessionId" + session.getId() + "--------开始回调-----------" + url);
            Oauth oauth = new Oauth();
            AccessToken accessTokenObj = oauth.getAccessTokenByRequest(req);
            String accessToken = null;
            String openID = null;
            long tokenExpireIn = 0L;
            if ("".equals(accessTokenObj.getAccessToken())) {
                log.info("用户未登录--------");
                resp.sendRedirect(url);
                return;
            }

            accessToken = accessTokenObj.getAccessToken();

            tokenExpireIn = accessTokenObj.getExpireIn();

            // 利用获取到的accessToken 去获取当前用的openid
            OpenID openIDObj = new OpenID(accessToken);
            openID = openIDObj.getUserOpenID();

            //根据OpenId 和 accsessToken 封装用户信息

            WxUser user = wxUserService.getQQUserInfo(accessToken, openID);

            wxUserService.addUser(user);
            session.setAttribute("qqUser", user);

        } catch (QQConnectException e) {
            e.printStackTrace();
        }
        resp.sendRedirect(url);
    }
    private  String[] extractionAuthCodeFromUrl(String url) throws QQConnectException {
        if (url == null) {
            throw new QQConnectException("you pass a null String object");
        } else {
            Matcher m = Pattern.compile("code=(\\w+)&state=(\\w+)&?").matcher(url);
            String authCode = "";
            String state = "";
            if (m.find()) {
                authCode = m.group(1);
                state = m.group(2);
            }

            return new String[]{authCode, state};
        }
    }

}
