package com.len.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.len.base.BaseMapper;
import com.len.base.impl.BaseServiceImpl;
import com.len.common.HttpSendUtil;
import com.len.entity.WxUser;
import com.len.mapper.WxUserMapper;
import com.len.service.WxUserService;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.utils.QQConnectConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class WxUserServiceImpl extends BaseServiceImpl<WxUser, String> implements WxUserService {
    @Autowired
    private WxUserMapper wxUserMapper;

    @Override
    public BaseMapper<WxUser, String> getMappser() {
        return wxUserMapper;
    }

    @Override
    public void addUser(WxUser user) {
        WxUser w = new WxUser();
        w.setOpenId(user.getOpenId());
        WxUser retW = wxUserMapper.selectOne(w);
        if (retW != null) {
            user.setId(retW.getId());
            wxUserMapper.updateByPrimaryKeySelective(user);
        } else {
            wxUserMapper.insertSelective(user);
        }
    }


    @Override
    public WxUser getQQUserInfo(String accessToken, String openID) throws Exception {
        String icon = null, nickName = null, sex = null;

        //根据openId获取用户信息
        String url = QQConnectConfig.getValue("getUserInfoURL");
        String getUrl = url +"?access_token="+accessToken+"&oauth_consumer_key=12345&openid="+openID;
        String ret = HttpSendUtil.get(getUrl);
        log.info("getQQUserInfo得到数据为：" + ret);
        JSONObject retJosn = JSONObject.parseObject(ret);
        nickName = retJosn.getString("nickname");
        icon = retJosn.getString("figureurl");
        sex = retJosn.getString("gender");
        if("男".equals(sex)){
            sex = "1";
        }else if("女".equals(sex)){
            sex="2";
        }else{
            sex="1";
        }
        //根据openId 判断用户是否存在
        WxUser user = new WxUser();
        user.setOpenId(openID);
        user.setUserPic(icon);
        user.setNickName(nickName);
        user.setGender(sex);
        this.addUser(user);
        return user;
    }

    /**
     * 根据code获取
     *
     * @param code
     * @return
     */
    @Cacheable(value = "accessToken", key = "#code") //存储在缓存中
    @Override
    public String getAccessToken(String code) {
        String url = QQConnectConfig.getValue("accessTokenURL");
        String grant_type = "authorization_code";
        String client_id = QQConnectConfig.getValue("app_ID");
        String client_secret = QQConnectConfig.getValue("app_KEY");
        String redirect_uri = QQConnectConfig.getValue("redirect_URI");

        String ret = HttpSendUtil.get(url + "?grant_type=" + grant_type + "&client_id=" + client_id + "&client_secret=" + client_secret
                + "&redirect_uri=" + redirect_uri + "&code=" + code);
        log.info("getAccessToken得到数据为：" + ret);
        //返回的信息:access_token=59F0040530564F831E88598B10650121&expires_in=7776000&refresh_token=937C4D881C55B8E4032EC529C5043462
        String[] arr = ret.split("=");
        String access_token = arr[1].split("&")[0];
        return access_token;
    }

    @Override
    public String getOpenId(String accessToken) {
        String url = QQConnectConfig.getValue("getOpenIDURL");
        String ret = HttpSendUtil.get(url + "?access_token=" + accessToken);
        log.info("getOpenId得到数据为：" + ret);
        //callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
        return jxOpenId(ret);
    }

    private String jxOpenId(String ret) {
        ret = ret.split("callback\\(")[1];
        ret = ret.split("\\)")[0];
        JSONObject jsonObject = JSONObject.parseObject(ret.trim());
        return jsonObject.getString("openid");
    }
}