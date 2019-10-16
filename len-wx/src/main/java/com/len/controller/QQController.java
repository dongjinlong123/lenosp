package com.len.controller;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * qq互联接入
 */
@CrossOrigin
@Controller
@Slf4j
@RequestMapping("/qq")
public class QQController {

    @GetMapping(value = "/qqLogin")
    public void qqLogin(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html;charset=utf-8");
        try {
            resp.sendRedirect(new Oauth().getAuthorizeURL(req));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (QQConnectException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/callback")
    public String callback(HttpServletRequest req, HttpServletResponse resp) {
        try {
            log.info("--------开始回调-----------");
            AccessToken accessTokenObj = new Oauth().getAccessTokenByRequest(req);
            String accessToken = null;
            String openID = null;
            long tokenExpireIn = 0L;
            if (accessTokenObj.getAccessToken().equals("")) {
                log.info("用户为登录--------");
                return "404";
            }
            accessToken = accessTokenObj.getAccessToken();

            tokenExpireIn = accessTokenObj.getExpireIn();
            // 利用获取到的accessToken 去获取当前用的openid
            OpenID openIDObj = new OpenID(accessToken);
            openID = openIDObj.getUserOpenID();

            String icon = null, nickName = null, sex = null;
            // 去获取用户在Qzone的昵称等信息
            com.qq.connect.api.qzone.UserInfo qzoneUserInfo =
                    new com.qq.connect.api.qzone.UserInfo(accessToken, openID);
            UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
            if (userInfoBean.getRet() == 0) {
                nickName = userInfoBean.getNickname();
                sex = userInfoBean.getGender();
                if (userInfoBean.getAvatar().getAvatarURL100() == null) {
                    icon = userInfoBean.getAvatar().getAvatarURL50();
                }
                icon = userInfoBean.getAvatar().getAvatarURL100();

            }
            log.info("获得的信息"+ userInfoBean);
        } catch (QQConnectException e) {
            e.printStackTrace();
        }
        return "666";
    }

}
