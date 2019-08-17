package com.len.service.impl;

import com.len.base.BaseMapper;
import com.len.base.impl.BaseServiceImpl;
import com.len.mapper.HJPersonMapper;
import com.len.entity.HJPerson;
import com.len.service.HJPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HJPersonServiceImpl extends BaseServiceImpl<HJPerson, String>
        implements HJPersonService {

    @Autowired
    private HJPersonMapper hPersonMapper;

    @Override
    public BaseMapper<HJPerson, String> getMappser() {
        return hPersonMapper;
    }

    @Override
    public List<String> getAllStudyYear() {
        return hPersonMapper.getAllStudyYear();
    }

    @Override
    public List<String> getAllProvince() {
        return hPersonMapper.getAllProvince();
    }

    @Override
    public List<String> getAllCityByProvince(String province) {
        return hPersonMapper.getAllCityByProvince(province);
    }
}
