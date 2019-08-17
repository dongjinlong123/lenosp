package com.len.service;

import com.len.base.BaseService;
import com.len.entity.HJPerson;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HJPersonService  extends BaseService<HJPerson, String> {
    /**
     * 得到所有年份列表
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
