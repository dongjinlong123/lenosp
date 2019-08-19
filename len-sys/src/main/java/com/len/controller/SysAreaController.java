package com.len.controller;

import com.len.base.BaseController;
import com.len.entity.SysArea;
import com.len.service.SysAreaService;
import com.len.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 系统地址
 */
@CrossOrigin
@Controller
@Slf4j
@RequestMapping("/SysArea")
public class SysAreaController extends BaseController {
    @Autowired
    private SysAreaService SysAreaService;

    @RequestMapping("/getProvince")
    @ResponseBody
    public JsonUtil getProvince() {
        JsonUtil j = new JsonUtil();
        j.setFlag(true);
        List<SysArea> list = SysAreaService.selectByLevel(1, -1);
        j.setData(list);
        return j;
    }

    @RequestMapping("/getCity")
    @ResponseBody
    public JsonUtil getCity(Integer provinceId) {
        JsonUtil j = new JsonUtil();
        j.setFlag(true);
        List<SysArea> list = SysAreaService.selectByLevel(2, provinceId);
        j.setData(list);
        return j;
    }

    @RequestMapping("/getArea")
    @ResponseBody
    public JsonUtil getArea(Integer cityId) {
        JsonUtil j = new JsonUtil();
        j.setFlag(true);
        List<SysArea> list = SysAreaService.selectByLevel(3, cityId);
        j.setData(list);
        return j;
    }
}