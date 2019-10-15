package com.len.mapper;
import com.len.base.BaseMapper;
import com.len.entity.Article;
import com.len.entity.ArticlePraise;
import com.len.entity.ArticleSave;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ArticleMapper extends BaseMapper<Article, String> {
    List<String> getAllCategory();

    void insertArticleType(Article article);

    void insertArticle(Article article);

    Article selectByKey(Integer id);

    void deleteByArticleId(Integer articleId);
    void deleteAllByArticleId(Integer articleId);
    /**
     * 获取文章点赞信息
     * @param id
     * @return
     */
    List<ArticlePraise> selectPraiseListByPage(Integer id);
    Integer praiseCount(Integer id);

    /**
     * 获取文章收藏信息
     * @param id
     * @return
     */
    List<ArticleSave> selectSaveListByPage(Integer id);
    Integer saveCount(Integer id);

    List<Article> selectByCategory(String category);

    /**
     * 自定義分頁
     * @param article
     * @param pageSize
     * @param pagination
     * @return
     */
    List<Article> selectListByMyPage(@Param("article") Article article, @Param("pageSize") Integer pageSize, @Param("pagination")Integer pagination);

    List<Map<String, Object>> getClickRecommendList();

    List<Map<String, Object>> getCommentRecommendList();

    List<Map<String, Object>> getCommentUserList();

    List<Map<String, Object>> getCommentList();

    int getArticleCount(@Param("article") Article article);
}