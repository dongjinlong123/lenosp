package com.len.service;

import com.len.entity.Article;
import com.len.entity.ArticleComment;
import com.len.entity.BoKeUserMessage;
import com.len.entity.WxUser;

import java.util.List;
import java.util.Map;

/**
 * 博客接口服务
 */
public interface BokeIntfService {
    Integer getUserIdByOpenId(String openId);

    List<Article> getArticleList(Article article, Integer pageSize, Integer pagination);

    Article getArticleDetail(Integer id);

    void saveUserInfo(WxUser wxUser);

    List<Map<String, Object>> getCategoryList();

    List<Article> getArticleListByCategory(String category);

    Map<String, Object> getSignNum(String openId);

    Map<String, Object> saveSign(String openId);

    Integer getIsCollect(Integer id, String openId, Integer flag);

    boolean collectAction(Integer id, String openId, String action);

    Integer getIsLiked(Integer id, String openId, Integer flag);

    boolean likeAction(Integer id, String openId, String action);

    List<Map<String, Object>> getComment(Integer id);

    boolean saveComment(ArticleComment articleComment);

    boolean addReadCount(Integer id);

    boolean sendNew(BoKeUserMessage boKeUserMessage);

    Integer getNewsCount(String openId);

    List<BoKeUserMessage> getNewsList(String openId);

    boolean changeStatus(String openId, Integer id);

    List<Map<String, Object>> getCollectList(Integer userId);
}
