package com.len.service.impl;
import com.len.base.BaseMapper;
import com.len.base.impl.BaseServiceImpl;
import com.len.entity.WxUser;
import com.len.mapper.WxUserMapper;
import com.len.service.WxUserService;
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
}