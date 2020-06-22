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

    /**
     * 点击推荐列表 10 个
     * @return
     */
    List<Map<String, Object>> getClickRecommendList();

    /**
     * 评论推荐列表 10 个
     * @return
     */
    List<Map<String, Object>> getCommentRecommendList();

    /**
     * 评论用户列表 5 个
     * @return
     */
    List<Map<String, Object>> getCommentUserList();

    /**
     * 最新评论 10 个
     * @return
     */
    List<Map<String, Object>> getCommentList();

    /**
     * 得到文章总数
     * @param article
     * @return
     */
    int getArticleCount(Article article);

    /**
     * 用户ID获取用户信息
     * @param userId
     * @return
     */
    WxUser getUserById(Integer userId);
}
