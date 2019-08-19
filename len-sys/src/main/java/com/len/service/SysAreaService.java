package com.len.service;

import com.len.base.BaseService;
import com.len.entity.SysArea;

import java.util.List;

public interface SysAreaService extends BaseService<SysArea, String> {
    /**
     * 根据地区级别以及父ID获取对应的地区信息
     * @param i
     * @return
     */
    List<SysArea> selectByLevel(Integer i,Integer parentId);
}