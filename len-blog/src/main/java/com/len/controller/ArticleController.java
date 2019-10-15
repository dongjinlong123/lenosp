package com.len.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.len.base.BaseController;
import com.len.common.QRCodeUtil;
import com.len.entity.Article;
import com.len.entity.ArticlePraise;
import com.len.entity.ArticleSave;
import com.len.exception.MyException;
import com.len.service.ArticleService;
import com.len.util.JsonUtil;
import com.len.util.ReType;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@Api(value = "文章控制类", description = "文章控制类")
public class ArticleController extends BaseController {
    @Autowired
    private ArticleService articleService;

    @Value("${lenosp.imagePath}")
    private String imagePath;

    @Value("${lenosp.rootUrl}")
    private String rootUrl;
    /**
     *展示首页
     */
    @GetMapping("/showArticle")
    @RequiresPermissions("article:show")
    public String articleListPage(Model model,HttpServletRequest req, HttpServletResponse resp) {

        model.addAttribute("categoryList",articleService.getAllCategory());
        return "/article";
    }
    /**
     * 分页
     */
    @GetMapping(value = "/showArticleList")
    @ResponseBody
    @RequiresPermissions("article:show")
    public ReType showArticleList(Article article, String page, String limit) {
        log.info("传递的参数为："+article.getStatus());
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
        model.addAttribute("categoryList",articleService.getAllCategory());
        return "/add-article";
    }

    /**
     * 展示修改|查看页面
     */
    @GetMapping(value = "/showArticleDetail")
    @RequiresPermissions("article:select")
    public String showArticleDetail(String id, Model model, boolean detail) {
        Article article = null;
        if (StringUtils.isNotEmpty(id)) {
            article = articleService.selectByKey(Integer.valueOf(id));
            model.addAttribute("article", article);
        }
        String mainPng = rootUrl + article.getListPic();
        model.addAttribute("mainPng",mainPng);
        model.addAttribute("categoryList",articleService.getAllCategory());
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
            articleService.insertArticle(article);
        } catch (MyException e) {
            msg = "添加失败";
            j.setFlag(false);
            e.printStackTrace();
        }
        j.setMsg(msg);
        return j;
    }
//    @GetMapping(value = "/getAllCategory")
//    @ResponseBody
//    public JsonUtil getAllCategory() {
//        JsonUtil j = new JsonUtil();
//        j.setData(articleService.getAllCategory());
//        return j;
//    }


    @PostMapping(value = "/getErWeiMa")
    @ResponseBody
    public JsonUtil getErWeiMa(String  text,String listPic) {

        JsonUtil j = new JsonUtil();
        String imgPath = imagePath + listPic;
        String msg = "生成成功";
        try {
            String erWeiMaUrl = QRCodeUtil.encode(text, imgPath, imagePath, true);
            j.setData(rootUrl + erWeiMaUrl);
        } catch (Exception e) {
            msg="生成失败";
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
        if (articleService.updateByKey(article)) {
            j.setFlag(true);
            j.setMsg("更新成功");
        } else {
            j.setFlag(false);
            j.setMsg("更新失败");
        }
        return j;
    }
    /**
     * 更新状态
     */
    @PostMapping(value = "/updateArticleStatus")
    @ResponseBody
    @RequiresPermissions("article:update")
    public JsonUtil updateArticleStatus(Article article) {
        JsonUtil j = new JsonUtil();

        if (articleService.updateById(article)) {
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
    public JsonUtil updateArticle(Integer id) {
        JsonUtil j = new JsonUtil();
        if (id == null) {
            j.setFlag(false);
            j.setMsg("刪除数据失败");
            return j;
        }
        if (articleService.deleteByKey(id)) {
            j.setFlag(true);
            j.setMsg("刪除成功");
        } else {
            j.setFlag(false);
            j.setMsg("刪除失败");
        }
        return j;
    }
    /**
     * 分页
     */
    @GetMapping(value = "/showArticlePraiseList")
    @ResponseBody
    public ReType showArticlePraiseList(Integer id, String page, String limit) {
        List<ArticlePraise> tList = null;
        try {
            tList = articleService.selectPraiseListByPage(id);
        } catch (MyException e) {
            log.error("class:ArticleController ->method:showArticlePraiseList->message:" + e.getMessage());
            e.printStackTrace();
        }
        return new ReType(articleService.praiseCount(id), tList);
    }

    /**
     * 分页
     */
    @GetMapping(value = "/showArticleSaveList")
    @ResponseBody
    public ReType showArticleSaveList(Integer id, String page, String limit) {
        List<ArticleSave> tList = null;
        try {
            tList = articleService.selectSaveListByPage(id);
        } catch (MyException e) {
            log.error("class:ArticleController ->method:showArticleSaveList->message:" + e.getMessage());
            e.printStackTrace();
        }
        return new ReType(articleService.saveCount(id), tList);
    }
}