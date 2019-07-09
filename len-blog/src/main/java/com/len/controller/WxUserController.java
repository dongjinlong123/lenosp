package com.len.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.len.base.BaseController;
import com.len.entity.WxUser;
import com.len.exception.MyException;
import com.len.service.WxUserService;
import com.len.util.ReType;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CrossOrigin
@Controller
@Slf4j
@RequestMapping("/wxUser")
public class WxUserController extends BaseController {
    @Autowired
    private WxUserService wxUserService;

    /**
     * 展示首页
     */
    @GetMapping("/showWxUser")
    @RequiresPermissions("wxUser:show")
    public String wxUserListPage(HttpServletRequest req, HttpServletResponse resp) {
        return "/wxUser";
    }

    /**
     * 分页
     */
    @GetMapping(value = "/showWxUserList")
    @ResponseBody
    @RequiresPermissions("wxUser:show")
    public ReType showWxUserList(WxUser wxUser, String page, String limit) {
        List<WxUser> tList = null;
        Page<WxUser> tPage = PageHelper.startPage(Integer.valueOf(page), Integer.valueOf(limit));
        try {
            tList = wxUserService.selectListByPage(wxUser);
        } catch (MyException e) {
            log.error("class:WxUserController ->method:showWxUserList->message:" + e.getMessage());
            e.printStackTrace();
        }
        return new ReType(tPage.getTotal(), tList);
    }
}