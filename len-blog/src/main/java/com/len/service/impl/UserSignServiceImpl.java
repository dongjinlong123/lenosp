package com.len.service.impl;
import com.len.base.BaseMapper;
import com.len.base.impl.BaseServiceImpl;
import com.len.entity.UserSign;
import com.len.mapper.UserSignMapper;
import com.len.service.UserSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserSignServiceImpl extends BaseServiceImpl<UserSign, String> implements UserSignService {
	@Autowired
	private UserSignMapper userSignMapper;
    @Override
    public BaseMapper<UserSign, String> getMappser() {
        return userSignMapper;
    }
}