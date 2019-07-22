package com.len.mapper;
import com.len.base.BaseMapper;
import com.len.entity.Article;
import com.len.entity.ArticlePraise;
import com.len.entity.ArticleSave;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article, String> {
    List<String> getAllCategory();

    void insertArticleType(Article article);

    void insertArticle(Article article);

    Article selectByKey(Integer id);

    void deleteByArticleId(Integer articleId);

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
}