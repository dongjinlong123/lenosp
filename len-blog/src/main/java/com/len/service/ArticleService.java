package com.len.service;
import com.len.base.BaseService;
import com.len.entity.Article;

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
}