package com.len.mapper;

import com.len.base.BaseMapper;
import com.len.entity.HJPerson;

import java.util.List;

public interface HJPersonMapper extends BaseMapper<HJPerson, String> {
    /**
     * 得到年份列表
     * @return
     */
    List<String> getAllStudyYear();
}
