package com.len.mapper;

import com.len.base.BaseMapper;
import com.len.entity.HJPerson;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HJPersonMapper extends BaseMapper<HJPerson, String> {
    /**
     * 得到年份列表
     * @return
     */
    List<String> getAllStudyYear();

    /**
     * 获取所有的省份
     * @return
     */
    List<String> getAllProvince();

    /**
     * 获取所有的城市
     * @return
     */
    List<String> getAllCityByProvince(@Param("province") String province);
}
