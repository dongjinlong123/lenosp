package com.len.service.impl;
import com.len.base.BaseMapper;
import com.len.base.impl.BaseServiceImpl;
import com.len.entity.BoKeUserMessage;
import com.len.mapper.BoKeUserMessageMapper;
import com.len.service.BoKeUserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class BoKeUserMessageServiceImpl extends BaseServiceImpl<BoKeUserMessage, String> implements BoKeUserMessageService {
	@Autowired
	private BoKeUserMessageMapper BoKeUserMessageMapper;
    @Override
    public BaseMapper<BoKeUserMessage, String> getMappser() {
        return BoKeUserMessageMapper;
    }

    @Override
    public Integer getNewsCount(BoKeUserMessage b) {
        return BoKeUserMessageMapper.getNewsCount(b);
    }
}