package com.len.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.len.entity.HJPerson;
import com.len.exception.MyException;
import com.len.service.HJPersonService;
import com.len.util.JsonUtil;
import com.len.util.ReType;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 文章管理（后台）
 *
 * @author JamesZBL
 * @email 154040976@qq.com
 * @date 2018-05-05
 */

@CrossOrigin
@Controller
@Slf4j
@RequestMapping("/hjPerson")
public class HJPersonController {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    @Autowired
    private HJPersonService hJPersonService;
    @GetMapping("/showHjPerson")
    @RequiresPermissions("hjPerson:show")
    public String articleListPage(HttpServletRequest req, HttpServletResponse resp) {
        log.info("进入花椒人员管理页面");
        return "/hjPersonInfo";
    }
    /**
     * 获取 人员信息列表 分页
     */
    @GetMapping(value = "/showHjPersonList")
    @ResponseBody
    @RequiresPermissions("hjPerson:show")
    public ReType showHjPersonList(String userName,String studyYear, String page, String limit) {
        List<HJPerson> tList = null;
        Page<HJPerson> tPage = PageHelper.startPage(Integer.valueOf(page), Integer.valueOf(limit));
        try {
            log.info("得到的参数"+userName +"--"+ studyYear);
            HJPerson hjPerson = new HJPerson();
            hjPerson.setUseName(userName);
            hjPerson.setStudyYear(studyYear);
            tList = hJPersonService.selectListByPage(hjPerson);
        } catch (MyException e) {
            log.error("class:HJPersonController ->method:showHjPersonList->message:" + e.getMessage());
            e.printStackTrace();
        }
        return new ReType(tPage.getTotal(), tList);
    }
    @GetMapping(value = "/getAllStudyYear")
    @ResponseBody
    @RequiresPermissions("hjPerson:show")
    public JsonUtil getAllStudyYear() {
        JsonUtil j = new JsonUtil();
        List<String> studyYearList = hJPersonService.getAllStudyYear();
        j.setData(studyYearList);
        return j;
    }
    @GetMapping(value = "/showAddHjPerson")
    @RequiresPermissions("hjPerson:add")
    public String showAddHjPerson(Model model) {
        return "/add-hjPersonInfo";
    }
}
