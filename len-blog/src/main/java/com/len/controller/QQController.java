package com.len.controller;

import com.len.entity.SysUser;
import com.len.entity.WxUser;
import com.len.service.BokeIntfService;
import com.len.service.WxUserService;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping(value = "/checkSession")
    @ResponseBody
    public Map<String, Object> checkSession(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        HttpSession session =  req.getSession();
        //判断会话中是否存在用户的登录信息
        SysUser user = (SysUser) session.getAttribute("qqUser");
        if(user != null){
            result.put("userInfo",user);
            result.put("login",true);
        }else{
            result.put("login",false);
        }
        return result;
    }


    @GetMapping(value = "/qqLogin")
    public void qqLogin(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session =  req.getSession();
        String url = req.getParameter("callBackUrl");
        //得到参数中跳转的url
        session.setAttribute("callBackUrl",url);

        //判断会话中是否存在用户的登录信息
        SysUser user = (SysUser) session.getAttribute("qqUser");
        try {
            resp.setContentType("text/html;charset=utf-8");
            if(user != null){
                resp.sendRedirect(url);
                return;
            }
            resp.sendRedirect(new Oauth().getAuthorizeURL(req));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (QQConnectException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/callback")
    public String callback(HttpServletRequest req, HttpServletResponse resp) {
        String url = "";
        try {
            HttpSession session =  req.getSession();
            //得到参数中跳转的url
            url =  (String) session.getAttribute("callBackUrl");

            log.info("--------开始回调-----------" + url);
            AccessToken accessTokenObj = new Oauth().getAccessTokenByRequest(req);
            String accessToken = null;
            String openID = null;
            long tokenExpireIn = 0L;
            if ("".equals(accessTokenObj.getAccessToken())) {
                log.info("用户未登录--------");
                return url;
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
            WxUser user = new WxUser();
            user.setOpenId(openID);
            user.setUserPic(icon);
            user.setNickName(nickName);
            user.setGender(sex);
            wxUserService.insertSelective(user);
            session.setAttribute("qqUser",user);

            log.info("获得的信息"+ userInfoBean);
        } catch (QQConnectException e) {
            e.printStackTrace();
        }
        return url;
    }
}
