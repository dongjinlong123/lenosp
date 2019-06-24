package com.len.service;

import com.len.base.BaseService;
import com.len.entity.HJPerson;

import java.util.List;

public interface HJPersonService  extends BaseService<HJPerson, String> {
    /**
     * 得到所有年份列表
     * @return
     */
    List<String> getAllStudyYear();
}
