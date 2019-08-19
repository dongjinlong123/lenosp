package com.len.service;

import com.len.base.BaseMapper;
import com.len.base.impl.BaseServiceImpl;
import com.len.entity.SysArea;
import com.len.mapper.SysAreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysAreaServiceImpl extends BaseServiceImpl<SysArea, String> implements SysAreaService {
    @Autowired
    private SysAreaMapper SysAreaMapper;
    @Override
    public BaseMapper<SysArea, String> getMappser() {
        return SysAreaMapper;
    }

    @Override
    public List<SysArea> selectByLevel(Integer i, Integer parentId) {
        SysArea sa = new SysArea();
        sa.setLevel(i);
        sa.setParentId(parentId);
        return SysAreaMapper.selectByLevel(sa);
    }
}