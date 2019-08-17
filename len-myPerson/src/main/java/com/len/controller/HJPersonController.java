package com.len.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.len.base.BaseController;
import com.len.core.shiro.Principal;
import com.len.entity.HJPerson;
import com.len.exception.MyException;
import com.len.service.HJPersonService;
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
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class HJPersonController extends BaseController {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    @Autowired
    private HJPersonService hJPersonService;

    @GetMapping("/showHjPerson")
    @RequiresPermissions("hjPerson:show")
    public String hjPersonListPage(HttpServletRequest req, HttpServletResponse resp) {
        log.info("进入花椒人员管理页面");
        log.info("当前的用户信息" + Principal.getCurrentUse());
        return "/hjPersonInfo";
    }

    /**
     * 获取 人员信息列表 分页
     */
    @GetMapping(value = "/showHjPersonList")
    @ResponseBody
    @RequiresPermissions("hjPerson:show")
    public ReType showHjPersonList(@ModelAttribute  HJPerson hjPerson, String page, String limit) {
        List<HJPerson> tList = null;
        Page<HJPerson> tPage = PageHelper.startPage(Integer.valueOf(page), Integer.valueOf(limit));
        try {
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

    @PostMapping(value = "/addHjPerson")
    @RequiresPermissions("hjPerson:add")
    @ResponseBody
    public JsonUtil addHjPerson(HJPerson hjPerson) {
        log.info("入参" + hjPerson);
        JsonUtil j = new JsonUtil();
        String msg = "保存成功";
        try {
            hjPerson.setCreatedBy(Principal.getCurrentUse().getUsername());
            hjPerson.setLastUpdateBy(Principal.getCurrentUse().getUsername());
            hjPerson.setLastUpdateDate(new Date());
            hjPerson.setCreatedDate(new Date());
            hJPersonService.insertSelective(hjPerson);
        } catch (MyException e) {
            msg = "保存失败";
            j.setFlag(false);
            e.printStackTrace();
        }
        j.setMsg(msg);
        return j;
    }

    @GetMapping(value = "/showHjPersonDetail")
    @RequiresPermissions("hjPerson:select")
    public String showHjPersonDetail(String id, Model model, boolean detail) {
        if (StringUtils.isNotEmpty(id)) {
            HJPerson hJPerson = hJPersonService.selectByPrimaryKey(id);
            model.addAttribute("hJPerson", hJPerson);
        }
        model.addAttribute("detail", detail);
        return "/update-hjPersonInfo";
    }

    @PostMapping(value = "/updateHjPerson")
    @ResponseBody
    @RequiresPermissions("hjPerson:update")
    public JsonUtil updateHjPerson(HJPerson hjPerson) {
        JsonUtil j = new JsonUtil();
        if (hjPerson == null) {
            j.setFlag(false);
            j.setMsg("获取数据失败");
            return j;
        }
        hjPerson.setLastUpdateBy(Principal.getCurrentUse().getUsername());
        hjPerson.setLastUpdateDate(new Date());
        if (hJPersonService.updateByPrimaryKeySelective(hjPerson) > 0) {
            j.setFlag(true);
            j.setMsg("更新成功");
        } else {
            j.setFlag(false);
            j.setMsg("更新失败");
        }
        return j;
    }

    @PostMapping("/del")
    @ResponseBody
    @RequiresPermissions("hjPerson:del")
    public JsonUtil updateHjPerson(Integer userNum) {
        JsonUtil j = new JsonUtil();
        if (userNum == null) {
            j.setFlag(false);
            j.setMsg("刪除数据失败");
            return j;
        }
        if (hJPersonService.deleteByPrimaryKey(userNum) > 0) {
            j.setFlag(true);
            j.setMsg("刪除成功");
        } else {
            j.setFlag(false);
            j.setMsg("刪除失败");
        }
        return j;
    }

    @GetMapping(value = "/getAllProvince")
    @ResponseBody
    @RequiresPermissions("hjPerson:show")
    public JsonUtil getAllProvince() {
        JsonUtil j = new JsonUtil();
        List<String> provinceList = hJPersonService.getAllProvince();
        j.setData(provinceList);
        return j;
    }

    @GetMapping(value = "/getAllCityByProvince")
    @ResponseBody
    @RequiresPermissions("hjPerson:show")
    public JsonUtil getAllCityByProvince(@RequestParam(required = false,name = "province") String province) {
        JsonUtil j = new JsonUtil();
        List<String> cityList = hJPersonService.getAllCityByProvince(province);
        j.setData(cityList);
        return j;
    }
}
