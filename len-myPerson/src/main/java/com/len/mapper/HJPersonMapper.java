package com.len.mapper;

import com.len.base.BaseMapper;
import com.len.entity.HJPerson;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * 得到省份人员分布
     * name:省份名称
     * value:人数
     * @return
     */
    List<Map<String, Object>> getHjPersonMap();
}
