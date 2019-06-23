package com.len.controller;

import com.len.entity.HJPerson;
import com.len.service.HJPersonService;
import com.len.util.ReType;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

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
   // @Autowired
    private HJPersonService hJPersonService;
    @GetMapping("/showHjPerson")
    @RequiresPermissions("hjPerson:show")
    public String articleListPage(HttpServletRequest req, HttpServletResponse resp) {
        log.info("进入花椒人员管理页面");
        return "/hjPersonInfo";
    }
    /**
     * 获取 人员信息列表
     */
    @GetMapping(value = "/showHjPersonList")
    @ResponseBody
    @RequiresPermissions("hjPerson:show")
    public ReType showHjPersonList(Model model, HJPerson hjPerson, String page, String limit) {
        return hJPersonService.show(hjPerson, Integer.valueOf(page), Integer.valueOf(limit));
    }
}
