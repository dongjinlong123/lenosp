package com.len.service.impl;

import com.len.base.BaseMapper;
import com.len.base.impl.BaseServiceImpl;
import com.len.entity.WxUser;
import com.len.mapper.WxUserMapper;
import com.len.service.WxUserService;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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

        //根据openId 判断用户是否存在
        WxUser user = new WxUser();
        user.setOpenId(openID);
        user.setUserPic(icon);
        user.setNickName(nickName);
        user.setGender(sex);
        return user;
    }
}