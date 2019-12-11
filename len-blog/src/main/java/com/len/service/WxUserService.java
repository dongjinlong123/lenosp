package com.len.service;
import com.len.base.BaseService;
import com.len.entity.WxUser;
public interface WxUserService extends BaseService<WxUser, String> {
    void addUser(WxUser user);

    WxUser getQQUserInfo(String accessToken, String openID) throws Exception;
}