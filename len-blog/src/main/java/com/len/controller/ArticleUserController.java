package com.len.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.len.base.BaseController;
import com.len.entity.ArticleComment;
import com.len.exception.MyException;
import com.len.service.ArticleCommentService;
import com.len.util.ReType;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@CrossOrigin
@Controller
@Slf4j
@RequestMapping("/article")
public class ArticleUserController extends BaseController {
    @Autowired
    private ArticleCommentService articleCommentService;

    /**
     * 分页
     */
    @GetMapping(value = "/showArticleCommentList")
    @ResponseBody
    public ReType showArticleCommentList(ArticleComment articleComment, String page, String limit) {
        List<ArticleComment> tList = null;
        Page<ArticleComment> tPage = PageHelper.startPage(Integer.valueOf(page), Integer.valueOf(limit));
        try {
            tList = articleCommentService.selectListByPage(articleComment);
        } catch (MyException e) {
            log.error("class:ArticleCommentController ->method:showArticleCommentList->message:" + e.getMessage());
            e.printStackTrace();
        }
        return new ReType(tPage.getTotal(), tList);
    }

}