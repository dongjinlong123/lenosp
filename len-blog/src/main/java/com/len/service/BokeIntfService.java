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

    Map<String, Object> getSignNum(Integer userId);

    Map<String, Object> saveSign(Integer userId);

    Integer getIsCollect(Integer id, Integer userId, Integer flag);

    boolean collectAction(Integer id, Integer userId, String action);

    Integer getIsLiked(Integer id, Integer userId, Integer flag);

    boolean likeAction(Integer id, Integer userId, String action);

    List<Map<String, Object>> getComment(Integer id);

    boolean saveComment(ArticleComment articleComment);

    boolean addReadCount(Integer id);

    boolean sendNew(BoKeUserMessage boKeUserMessage);

    Integer getNewsCount(Integer userId);

    List<BoKeUserMessage> getNewsList(Integer userId);

    boolean changeStatus(Integer userId, Integer id);

    List<Map<String, Object>> getCollectList(Integer userId);

    WxUser getUserIdByCode(Integer userId);
}
