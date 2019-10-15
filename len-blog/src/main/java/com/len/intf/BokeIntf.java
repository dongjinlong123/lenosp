package com.len.intf;

import com.len.base.BaseController;
import com.len.entity.Article;
import com.len.entity.ArticleComment;
import com.len.entity.BoKeUserMessage;
import com.len.entity.WxUser;
import com.len.service.BokeIntfService;
import com.len.service.WeiXinService;
import com.len.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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
        if(openId ==null){
            result.put("success", false);
            return result;
        }
        Integer userId = bokeIntfService.getUserIdByOpenId(openId);
        if(userId == null){
            result.put("success", false);
            return result;
        }
        result.put("success", true);
        result.put("result", userId);
        return result;
    }
    @GetMapping("/getUserInfoByUserId")
    @ResponseBody
    @ApiOperation(value = "getUserInfoByUserId", notes = "根据code获取用户ID")
    public Map<String, Object> getUserIdByCode(Integer userId, HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        WxUser wxUser = bokeIntfService.getUserIdByCode(userId);
        Date createTime = wxUser.getCreateTime();
        //超过7天重新登录，避免用户更新了信息
       if(DateUtil.differentDays(new Date(),createTime)>=7){
           result.put("flag", true);
       }else{
           result.put("flag", false);
       }
       result.put("result", wxUser);
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
        int count = bokeIntfService.getArticleCount(article);
        result.put("result", list);
        result.put("count", count);
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
    @ApiOperation(value = "saveUserInfo", notes = "保存用户信息")
    public Map<String, Object> saveUserInfo(@RequestBody WxUser wxUser, HttpServletRequest req, HttpServletResponse resp) {
        log.info("得到的信息" + wxUser);

        Map<String, Object> result = new HashMap<String, Object>();
        String code = wxUser.getCode();
        String openId = weiXinService.getOpenIdByCode(code);
        wxUser.setOpenId(openId);
        bokeIntfService.saveUserInfo(wxUser);

        result.put("result", bokeIntfService.getUserIdByOpenId(openId));
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
    public Map<String, Object> getSignNum(Integer userId, HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> ret = bokeIntfService.getSignNum(userId);
        return ret;
    }

    @GetMapping("/saveSign")
    @ResponseBody
    @ApiOperation(value = "saveSign", notes = "保存签到数据")
    public Map<String, Object> saveSign(Integer userId, HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> ret = bokeIntfService.saveSign(userId);
        result.put("result", true);
        result.put("data", ret);
        return result;
    }

    @GetMapping("/getIsCollect")
    @ResponseBody
    @ApiOperation(value = "getIsCollect", notes = "查询是否收藏文章")
    public Map<String, Object> getIsCollect(Integer id, Integer userId, HttpServletRequest req, HttpServletResponse resp) {

        Map<String, Object> result = new HashMap<String, Object>();
        Integer count = bokeIntfService.getIsCollect(id, userId,0);
        result.put("result", count);
        return result;
    }

    @GetMapping("/collectAction")
    @ResponseBody
    @ApiOperation(value = "collectAction", notes = "取消/收藏文章")
    public Map<String, Object> collectAction(Integer id, Integer userId, String action, HttpServletRequest req, HttpServletResponse resp) {

        Map<String, Object> result = new HashMap<String, Object>();
        boolean flag = bokeIntfService.collectAction(id, userId, action);
        result.put("result", flag);
        return result;
    }

    @GetMapping("/getIsLiked")
    @ResponseBody
    @ApiOperation(value = "getIsLiked", notes = "查询是否点赞文章")
    public Map<String, Object> getIsLiked(Integer id, Integer userId, HttpServletRequest req, HttpServletResponse resp) {

        Map<String, Object> result = new HashMap<String, Object>();
        Integer count = bokeIntfService.getIsLiked(id, userId,0);
        result.put("result", count);
        return result;
    }

    @GetMapping("/likeAction")
    @ResponseBody
    @ApiOperation(value = "likeAction", notes = "点赞文章")
    public Map<String, Object> likeAction(Integer id, Integer userId, String action, HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        boolean flag = bokeIntfService.likeAction(id, userId, action);
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
    public Map<String, Object> getNewsCount(Integer userId, HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        Integer count = bokeIntfService.getNewsCount(userId);
        result.put("result",count);
        return result;
    }
    @GetMapping("/getNewsList")
    @ResponseBody
    @ApiOperation(value = "getNewsList", notes = "得到未读信息列表")
    public Map<String, Object> getNewsList(Integer userId, HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<BoKeUserMessage> list = bokeIntfService.getNewsList(userId);
        result.put("result",list);
        return result;
    }
    @GetMapping("/changeStatus")
    @ResponseBody
    @ApiOperation(value = "changeStatus", notes = "更新信息状态")
    public Map<String, Object> changeStatus(Integer userId, Integer id, HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        boolean flag = bokeIntfService.changeStatus(userId,id);
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

    @GetMapping("/getArticleRecommend")
    @ResponseBody
    @ApiOperation(value = "getArticleRecommend", notes = "获取推荐列表")
    public Map<String, Object> getArticleRecommend(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> result = new HashMap<String, Object>();
        //点击推荐列表 10 个
        List<Map<String, Object>> clickRecommendList = bokeIntfService.getClickRecommendList();
        result.put("clickRecommendList",clickRecommendList);
        //评论推荐列表 10 个
        List<Map<String, Object>> commentRecommendList = bokeIntfService.getCommentRecommendList();
        result.put("commentRecommendList",commentRecommendList);

        //评论用户列表 5 个
        List<Map<String, Object>> commentUserList = bokeIntfService.getCommentUserList();
        result.put("commentUserList",commentUserList);

        //最新评论 10 个
        List<Map<String, Object>> commentList = bokeIntfService.getCommentList();
        result.put("commentList",commentList);

        return result;
    }
}
