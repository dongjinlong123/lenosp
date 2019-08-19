package com.len.mapper;

import com.len.base.BaseMapper;
import com.len.entity.SysArea;

import java.util.List;

public interface SysAreaMapper extends BaseMapper<SysArea, String> {
    /**
     * 根据地区级别以及父ID获取对应的地区信息
     */
    List<SysArea> selectByLevel(SysArea sysArea);
}