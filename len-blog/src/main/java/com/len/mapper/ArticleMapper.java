package com.len.mapper;
import com.len.base.BaseMapper;
import com.len.entity.Article;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article, String> {
    List<String> getAllCategory();

    void insertArticleType(Article article);

    void insertArticle(Article article);

    Article selectByKey(Integer id);

    void deleteByArticleId(Integer articleId);
}