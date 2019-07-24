package com.len.service;
import com.len.base.BaseService;
import com.len.entity.Article;
import com.len.entity.ArticlePraise;
import com.len.entity.ArticleSave;

import java.util.List;

public interface ArticleService extends BaseService<Article, String> {
    public List<String> getAllCategory();

    /**
     * 新增文章
     * @param article
     */
    void insertArticle(Article article);

    Article selectByKey(Integer id);

    boolean updateByKey(Article article);

    boolean deleteByKey(Integer id);

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

    List<Article> selectListByPage(Article article, Integer pageSize, Integer pagination);
}