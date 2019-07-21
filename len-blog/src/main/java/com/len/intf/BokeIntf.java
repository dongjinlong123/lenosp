package com.len.intf;

import com.len.base.BaseController;
import com.len.common.HttpSendUtil;
import com.len.entity.Article;
import com.len.entity.ArticleComment;
import com.len.entity.BoKeUserMessage;
import com.len.entity.WxUser;
import com.len.service.ArticleService;
import com.len.service.BokeIntfService;
import com.len.service.WeiXinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 博客对外接口
 */
@CrossOrigin
@Controller
@Slf4j
@RequestMapping("/intf")
@Api(value = "接口管理", description = "小程序接口处理")
public class BokeIntf extends BaseController {

    @Autowired
    private BokeIntfService bokeIntfService;
    @Autowired
    private WeiXinService weiXinService;


    @GetMapping("/getUserIdByCode")
    @ResponseBody
    @ApiOperation(value = "getUserIdByCode", notes = "根据code获取用户ID")
    public Map<String, Object> getUserIdByCode(String code, HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        String openId = weiXinService.getOpenIdByCode(code);
        Integer userId = bokeIntfService.getUserIdByOpenId(openId);
        result.put("result", userId);
        return result;
    }
    /**
     * 获取文章列表
     *
     * @return
     */
    @GetMapping("/getArticleList")
    @ResponseBody
    @ApiOperation(value = "getArticleList", notes = "获取文章列表")
    public Map<String, Object> getArticleList(Article article, Integer pageSize, Integer pagination,
                                              HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        //Integer pageSize = Integer.valueOf(req.getParameter("pageSize")); //每页数量
        //  Integer pagination = Integer.valueOf(req.getParameter("pagination"));//页次
        log.info("传递的参数" + pageSize + "--" + pagination);
        List<Article> list = bokeIntfService.getArticleList(article, pageSize, pagination);

        result.put("result", list);
        return result;
    }

    @GetMapping("/getArticleDetail")
    @ResponseBody
    @ApiOperation(value = "getArticleDetail", notes = "获取文章详情")
    public Map<String, Object> getArticleDetail(Integer id, HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        Article article = bokeIntfService.getArticleDetail(id);
        result.put("result", article);
        return result;
    }

    @PostMapping("/saveUserInfo")
    @ResponseBody
    @ApiOperation(value = "saveUserInfo", notes = "获取文章详情")
    public Map<String, Object> saveUserInfo(@RequestBody WxUser wxUser, HttpServletRequest req, HttpServletResponse resp) {
        log.info("得到的信息" + wxUser);

        Map<String, Object> result = new HashMap<String, Object>();
        String code = wxUser.getCode();
        String openId = weiXinService.getOpenIdByCode(code);
        wxUser.setOpenId(openId);

        bokeIntfService.saveUserInfo(wxUser);
        result.put("msg", "保存成功");
        return result;
    }

    @GetMapping("/getCategoryList")
    @ResponseBody
    @ApiOperation(value = "getCategoryList", notes = "获取分类列表")
    public Map<String, Object> getCategoryList(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> list = bokeIntfService.getCategoryList();
        result.put("result", list);
        return result;
    }

    @GetMapping("/getArticleByCategory")
    @ResponseBody
    @ApiOperation(value = "getArticleByCategory", notes = "按照分类请求博文数据")
    public Map<String, Object> getArticleByCategory(String category, HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Article> list = bokeIntfService.getArticleListByCategory(category);
        result.put("result", list);
        return result;
    }

    @GetMapping("/getSignNum")
    @ResponseBody
    @ApiOperation(value = "getSignNum", notes = "获取签到天数")
    public Map<String, Object> getSignNum(String code, HttpServletRequest req, HttpServletResponse resp) {
        String openId = weiXinService.getOpenIdByCode(code);
        Map<String, Object> ret = bokeIntfService.getSignNum(openId);
        return ret;
    }

    @GetMapping("/saveSign")
    @ResponseBody
    @ApiOperation(value = "saveSign", notes = "保存签到数据")
    public Map<String, Object> saveSign(String code, HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        String openId = weiXinService.getOpenIdByCode(code);
        Map<String, Object> ret = bokeIntfService.saveSign(openId);
        result.put("result", true);
        result.put("data", ret);
        return result;
    }

    @GetMapping("/getIsCollect")
    @ResponseBody
    @ApiOperation(value = "getIsCollect", notes = "查询是否收藏文章")
    public Map<String, Object> getIsCollect(Integer id, String code, HttpServletRequest req, HttpServletResponse resp) {

        Map<String, Object> result = new HashMap<String, Object>();
        String openId = weiXinService.getOpenIdByCode(code);
        Integer count = bokeIntfService.getIsCollect(id, openId,0);
        result.put("result", count);
        return result;
    }

    @GetMapping("/collectAction")
    @ResponseBody
    @ApiOperation(value = "collectAction", notes = "取消/收藏文章")
    public Map<String, Object> collectAction(Integer id, String code, String action, HttpServletRequest req, HttpServletResponse resp) {

        Map<String, Object> result = new HashMap<String, Object>();
        String openId = weiXinService.getOpenIdByCode(code);
        boolean flag = bokeIntfService.collectAction(id, openId, action);
        result.put("result", flag);
        return result;
    }

    @GetMapping("/getIsLiked")
    @ResponseBody
    @ApiOperation(value = "getIsLiked", notes = "查询是否点赞文章")
    public Map<String, Object> getIsLiked(Integer id, String code, HttpServletRequest req, HttpServletResponse resp) {

        Map<String, Object> result = new HashMap<String, Object>();
        String openId = weiXinService.getOpenIdByCode(code);
        Integer count = bokeIntfService.getIsLiked(id, openId,0);
        result.put("result", count);
        return result;
    }

    @GetMapping("/likeAction")
    @ResponseBody
    @ApiOperation(value = "likeAction", notes = "点赞文章")
    public Map<String, Object> likeAction(Integer id, String code, String action, HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        String openId = weiXinService.getOpenIdByCode(code);
        boolean flag = bokeIntfService.likeAction(id, openId, action);
        result.put("result", flag);
        return result;
    }
    @GetMapping("/getComment")
    @ResponseBody
    @ApiOperation(value = "getComment", notes = "获取文章评论")
    public Map<String, Object> getComment(Integer id, HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> list = bokeIntfService.getComment(id);
        result.put("result", list);
        return result;
    }

    @PostMapping("/saveComment")
    @ResponseBody
    @ApiOperation(value = "saveComment", notes = "保存文章评论")
    public Map<String, Object> saveComment(@RequestBody ArticleComment articleComment, HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        boolean flag = bokeIntfService.saveComment(articleComment);
        result.put("result",flag);
        result.put("replyerId",articleComment.getReplyerId());
        return result;
    }
    @GetMapping("/addReadCount")
    @ResponseBody
    @ApiOperation(value = "addReadCount", notes = "添加阅读记录")
    public Map<String, Object> addReadCount(Integer id, HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        boolean flag = bokeIntfService.addReadCount(id);
        result.put("result",flag);
        return result;
    }

    @PostMapping("/sendNew")
    @ResponseBody
    @ApiOperation(value = "sendNew", notes = "保存文章评论")
    public Map<String, Object> sendNew(@RequestBody BoKeUserMessage boKeUserMessage, HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        boolean flag = bokeIntfService.sendNew(boKeUserMessage);
        result.put("result",flag);
        return result;
    }

    @GetMapping("/getNewsCount")
    @ResponseBody
    @ApiOperation(value = "getNewsCount", notes = "未读信息数量")
    public Map<String, Object> getNewsCount(String code, HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        String openId = weiXinService.getOpenIdByCode(code);
        Integer count = bokeIntfService.getNewsCount(openId);
        result.put("result",count);
        return result;
    }
    @GetMapping("/getNewsList")
    @ResponseBody
    @ApiOperation(value = "getNewsList", notes = "得到未读信息列表")
    public Map<String, Object> getNewsList(String code, HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        String openId = weiXinService.getOpenIdByCode(code);
        List<BoKeUserMessage> list = bokeIntfService.getNewsList(openId);
        result.put("result",list);
        return result;
    }
    @GetMapping("/changeStatus")
    @ResponseBody
    @ApiOperation(value = "changeStatus", notes = "更新信息状态")
    public Map<String, Object> changeStatus(String code,Integer id, HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        String openId = weiXinService.getOpenIdByCode(code);
        boolean flag = bokeIntfService.changeStatus(openId,id);
        result.put("result",flag);
        return result;
    }
    @GetMapping("/getCollectList")
    @ResponseBody
    @ApiOperation(value = "getCollectList", notes = "获取收藏列表")
    public Map<String, Object> getCollectList(Integer userId,HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> collectList = bokeIntfService.getCollectList(userId);
        result.put("result",collectList);
        return result;
    }
}
