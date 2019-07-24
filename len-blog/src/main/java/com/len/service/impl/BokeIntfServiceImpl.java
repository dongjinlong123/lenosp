package com.len.service.impl;

import com.len.entity.*;
import com.len.exception.MyException;
import com.len.mapper.BokeIntfMapper;
import com.len.redis.RedisService;
import com.len.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BokeIntfServiceImpl implements BokeIntfService {
    @Autowired
    private BokeIntfMapper bokeIntfMapper;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private WxUserService wxUserService;
    @Autowired
    private ArticleCommentService articleCommentService;
    @Autowired
    private BoKeUserMessageService boKeUserMessageService;
    @Autowired
    private RedisService redisService;

    @Value("${lenosp.rootUrl}")
    private String rootUrl;

    private static Long redisSaveTime = 3600L;

    @Override
    public List<Article> getArticleList(Article article, Integer pageSize, Integer pagination) {
        List<Article> tList = null;
        try {
            tList = articleService.selectListByPage(article, pageSize, pagination * pageSize);
            for (Article a : tList) {
                a.setListPic(rootUrl + a.getListPic());
            }
        } catch (MyException e) {
            e.printStackTrace();
        }
        return tList;
    }

    @Override
    public Article getArticleDetail(Integer id) {
        if (redisService.getObj("article-" + id) != null) {
            return (Article) redisService.getObj("article-" + id);
        }
        Article a = articleService.selectByKey(id);
        a.setListPic(rootUrl + a.getListPic());
        redisService.setObj("article-" + id, a, redisSaveTime);
        return a;
    }

    @Override
    public void saveUserInfo(WxUser wxUser) {
        //根据openId判断用户是否存在
        String openId = wxUser.getOpenId();
        WxUser wx = new WxUser();
        wx.setOpenId(openId);
        WxUser user = wxUserService.selectOne(wx);
        if (user == null) {
            wxUserService.insertSelective(wxUser);
        } else {
            wxUser.setId(user.getId());
            wxUserService.updateByPrimaryKeySelective(wxUser);
        }

    }

    @Override
    public List<Map<String, Object>> getCategoryList() {
        if (redisService.getObj("getCategoryList") != null) {
            return (List<Map<String, Object>>) redisService.getObj("getCategoryList");
        }
        List<Map<String, Object>> ret = bokeIntfMapper.getCategoryList();
        redisService.setObj("getCategoryList", ret, redisSaveTime);
        return ret;
    }

    @Override
    public List<Article> getArticleListByCategory(String category) {

        if (redisService.getObj("getArticleListByCategory-" + category) != null) {
            return (List<Article>) redisService.getObj("getArticleListByCategory-" + category);
        }

        List<Article> list = articleService.selectByCategory(category);
        for (Article article : list) {
            article.setListPic(rootUrl + article.getListPic());
        }
        redisService.setObj("getArticleListByCategory-" + category, list, redisSaveTime);
        return list;
    }

    @Override
    public Map<String, Object> getSignNum(Integer userId) {
        //获取签到的次数
        Map<String, Object> result = new HashMap<String, Object>();
        Integer signNum = bokeIntfMapper.getSignNum(userId);
        result.put("signNum", signNum);
        List<Map<String, Object>> createdAtMap = bokeIntfMapper.getAllSignTime(userId);
        result.put("result", createdAtMap);
        return result;
    }

    @Override
    public Map<String, Object> saveSign(Integer userId) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createdAt = sdf.format(new Date());
        param.put("wxUserId", userId);
        param.put("createdAt", createdAt);
        bokeIntfMapper.insertSignDate(param);
        result.put("createdAt", createdAt);
        return result;
    }

    @Override
    public Integer getIsCollect(Integer id, Integer userId, Integer flag) {
        return bokeIntfMapper.getIsCollect(id, userId, flag);
    }

    @Override
    public Integer getUserIdByOpenId(String openId) {
        WxUser wx = new WxUser();
        wx.setOpenId(openId);
        WxUser user = wxUserService.selectOne(wx);
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    @Override
    public boolean collectAction(Integer id, Integer userId, String action) {
        ArticleSave as = new ArticleSave();
        as.setUserId(userId);
        as.setArticleId(id);
        boolean isExist = bokeIntfMapper.getIsCollect(id, userId, null) > 0;

        if ("collect".equals(action)) {
            //收藏操作
            as.setFlag(0);
        } else {
            as.setFlag(1);
        }
        as.setCreatedAt(new Date());
        //判断是否存在数据
        try {
            if (isExist) {
                as.setId(bokeIntfMapper.getArticleSaveId(id, userId));
                bokeIntfMapper.updateArticleSave(as);
            } else {
                //不存在则添加
                bokeIntfMapper.insertArticleSave(as);

            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public Integer getIsLiked(Integer id, Integer userId, Integer flag) {
        return bokeIntfMapper.getIsLiked(id, userId, null);
    }

    @Override
    public boolean likeAction(Integer id, Integer userId, String action) {
        ArticlePraise ap = new ArticlePraise();
        ap.setUserId(userId);
        ap.setArticleId(id);
        boolean isExist = bokeIntfMapper.getIsLiked(id, userId, null) > 0;

        if ("like".equals(action)) {
            //点赞操作
            ap.setFlag(0);
        } else {
            ap.setFlag(1);
        }
        ap.setCreatedAt(new Date());
        //判断是否存在数据
        try {
            if (isExist) {
                ap.setId(bokeIntfMapper.getArticlePraiseId(id, userId));
                bokeIntfMapper.updateArticlePraise(ap);
            } else {
                //不存在则添加
                bokeIntfMapper.insertArticlePraise(ap);
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public List<Map<String, Object>> getComment(Integer id) {

        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
        ArticleComment ac = new ArticleComment();
        ac.setArticleId(id);
        List<ArticleComment> list = articleCommentService.selectCommentList(ac);
        if (list == null || list.size() == 0) {
            return retList;
        }
        for (ArticleComment articleComment : list) {
            Map<String, Object> retMap = new HashMap<String, Object>();

            retMap.put("createdAt", articleComment.getCreatedAt());
            retMap.put("content", articleComment.getContent());
            retMap.put("formId", articleComment.getFormId());
            //回复信息
            Map<String, Object> replyerMap = new HashMap<String, Object>();
            Integer replyer = articleComment.getReplyerId();
            WxUser wxUser = wxUserService.selectByPrimaryKey(replyer);
            if (wxUser != null) {
                //获取用户信息根据用户id获取
                replyerMap.put("userPic", wxUser.getUserPic());
                replyerMap.put("nickName", wxUser.getNickName());
                replyerMap.put("replyerId", replyer);
                retMap.put("replyer", replyerMap);
            }
            //得到回复的用户信息
            Map<String, Object> userMap = new HashMap<String, Object>();
            Integer userId = articleComment.getUserId();

            WxUser user = wxUserService.selectByPrimaryKey(userId);
            if (user != null) {
                userMap.put("userPic", user.getUserPic());
                userMap.put("nickName", user.getNickName());
                userMap.put("userId", userId);
                retMap.put("user", userMap);
            }
            retList.add(retMap);
        }
        return retList;
    }

    @Override
    public boolean saveComment(ArticleComment articleComment) {
        try {
            articleComment.setCreatedAt(new Date());
            articleCommentService.insertSelective(articleComment);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean addReadCount(Integer id) {
        return bokeIntfMapper.addReadCount(id);
    }

    @Override
    public boolean sendNew(BoKeUserMessage boKeUserMessage) {
        try {
            boKeUserMessage.setCreatedAt(new Date());
            if ("回复".equals(boKeUserMessage.getType()) && boKeUserMessage.getUserId() == boKeUserMessage.getReplyerId()) {
                boKeUserMessage.setType("评论"); //自己评论自己不算是回复信息
            }
            if (boKeUserMessage.getUserId() == null) {
                boKeUserMessage.setUserId(boKeUserMessage.getReplyerId());
            }
            boKeUserMessageService.insertSelective(boKeUserMessage);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Integer getNewsCount(Integer userId) {
        BoKeUserMessage b = new BoKeUserMessage();
        b.setType("回复");
        b.setStatus(0);
        b.setUserId(userId);
        return boKeUserMessageService.getNewsCount(b);
    }

    @Override
    public List<BoKeUserMessage> getNewsList(Integer userId) {
        BoKeUserMessage b = new BoKeUserMessage();
        b.setType("回复");
        b.setStatus(0);
        b.setUserId(userId);
        List<BoKeUserMessage> messages = boKeUserMessageService.select(b);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (messages != null) {
            for (BoKeUserMessage mes : messages) {
                mes.setCreatedAtStr(sdf.format(mes.getCreatedAt()));
            }
        }
        return messages;
    }

    @Override
    public boolean changeStatus(Integer userId, Integer id) {
        try {
            BoKeUserMessage b = new BoKeUserMessage();
            b.setUserId(userId);
            b.setId(id);
            b.setStatus(1);//更新为已读
            b.setCreatedAt(new Date());
            boKeUserMessageService.updateByPrimaryKeySelective(b);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<Map<String, Object>> getCollectList(Integer userId) {
        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
        //根据用户id获取对于的收藏信息
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<ArticleSave> list = bokeIntfMapper.getCollectList(userId);
        for (ArticleSave as : list) {
            Map<String, Object> saveMap = new HashMap<String, Object>();
            saveMap.put("createdAt", sdf.format(as.getCreatedAt()));
            //查询对于的文章信息
            Article article = articleService.selectByKey(as.getArticleId());
            article.setListPic(rootUrl + article.getListPic());
            saveMap.put("article", article);
            retList.add(saveMap);
        }
        return retList;
    }

    @Override
    public WxUser getUserIdByCode(Integer userId) {
        return wxUserService.selectByPrimaryKey(userId);
    }
}
