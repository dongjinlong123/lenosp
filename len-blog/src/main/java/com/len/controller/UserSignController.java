package com.len.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.len.base.BaseController;

import java.util.List;

import com.len.entity.UserSign;
import com.len.exception.MyException;
import com.len.service.UserSignService;
import com.len.util.JsonUtil;
import com.len.util.ReType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@Controller
@Slf4j
@RequestMapping("/userSign")
public class UserSignController extends BaseController {
    @Autowired
    private UserSignService userSignService;

    /**
     * 分页
     */
    @GetMapping(value = "/showUserSignList")
    @ResponseBody
    public ReType showUserSignList(UserSign userSign, String page, String limit) {
        List<UserSign> tList = null;
        Page<UserSign> tPage = PageHelper.startPage(Integer.valueOf(page), Integer.valueOf(limit));
        try {
            tList = userSignService.selectListByPage(userSign);
        } catch (MyException e) {
            log.error("class:UserSignController ->method:showUserSignList->message:" + e.getMessage());
            e.printStackTrace();
        }
        return new ReType(tPage.getTotal(), tList);
    }
}