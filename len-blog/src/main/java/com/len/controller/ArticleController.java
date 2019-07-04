package com.len.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.len.base.BaseController;
import com.len.entity.Article;
import com.len.exception.MyException;
import com.len.service.ArticleService;
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
import java.util.List;

@CrossOrigin
@Controller
@Slf4j
@RequestMapping("/article")
public class ArticleController extends BaseController {
    @Autowired
    private ArticleService articleService;

    /**
     *展示首页
     */
    @GetMapping("/showArticle")
    @RequiresPermissions("article:show")
    public String articleListPage(HttpServletRequest req, HttpServletResponse resp) {
        return "/article";
    }
    /**
     * 分页
     */
    @GetMapping(value = "/showArticleList")
    @ResponseBody
    @RequiresPermissions("article:show")
    public ReType showArticleList(Article article, String page, String limit) {
        List<Article> tList = null;
        Page<Article> tPage = PageHelper.startPage(Integer.valueOf(page), Integer.valueOf(limit));
        try {
            tList = articleService.selectListByPage(article);
        } catch (MyException e) {
            log.error("class:ArticleController ->method:showArticleList->message:" + e.getMessage());
            e.printStackTrace();
        }
        return new ReType(tPage.getTotal(), tList);
    }
/**
     * 展示新增页面
     */
    @GetMapping(value = "/showArticleAdd")
    @RequiresPermissions("article:add")
    public String showArticleAdd(Model model) {
        return "/add-article";
    }
 /**
     * 展示修改|查看页面
     */
    @GetMapping(value = "/shoArticleDetail")
    @RequiresPermissions("article:select")
    public String showArticleDetail(String id, Model model, boolean detail) {
        if (StringUtils.isNotEmpty(id)) {
            Article article = articleService.selectByPrimaryKey(id);
            model.addAttribute("article", article);
        }
        model.addAttribute("detail", detail);
        return "/update-article";
    }
/**
     * 添加
     */
    @PostMapping(value = "/addArticle")
    @RequiresPermissions("article:add")
    @ResponseBody
    public JsonUtil addArticle(Article article) {
        log.info("入参" + article);
        JsonUtil j = new JsonUtil();
        String msg = "添加成功";
        try {
            articleService.insertSelective(article);
        } catch (MyException e) {
            msg = "添加失败";
            j.setFlag(false);
            e.printStackTrace();
        }
        j.setMsg(msg);
        return j;
    }
/**
     * 更新
     */
    @PostMapping(value = "/updateArticle")
    @ResponseBody
    @RequiresPermissions("article:update")
    public JsonUtil updateArticle(Article article) {
        JsonUtil j = new JsonUtil();
        if (article == null) {
            j.setFlag(false);
            j.setMsg("获取数据失败");
            return j;
        }
        if (articleService.updateByPrimaryKeySelective(article) > 0) {
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
    @RequiresPermissions("article:del")
    public JsonUtil updateArticle(Integer userNum) {
        JsonUtil j = new JsonUtil();
        if (userNum == null) {
            j.setFlag(false);
            j.setMsg("刪除数据失败");
            return j;
        }
        if (articleService.deleteByPrimaryKey(userNum) > 0) {
            j.setFlag(true);
            j.setMsg("刪除成功");
        } else {
            j.setFlag(false);
            j.setMsg("刪除失败");
        }
        return j;
    }}