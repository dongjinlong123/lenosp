package com.len.mapper;
import com.len.base.BaseMapper;
import com.len.entity.BoKeUserMessage;
public interface BoKeUserMessageMapper extends BaseMapper<BoKeUserMessage, String> {
    Integer getNewsCount(BoKeUserMessage b);
}