package com.len.mapper;
import com.len.base.BaseMapper;
import com.len.entity.SysArea;

import java.awt.geom.Area;
import java.util.List;

public interface SysAreaMapper extends BaseMapper<SysArea, String> {
    /**
     * 根据地区级别以及父ID获取对应的地区信息
     */
    List<Area> selectByLevel(SysArea sysArea);
}