package com.len.service.impl;

import com.len.base.BaseMapper;
import com.len.base.impl.BaseServiceImpl;
import com.len.entity.Article;
import com.len.mapper.ArticleMapper;
import com.len.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ArticleServiceImpl extends BaseServiceImpl<Article, String> implements ArticleService {
    @Autowired
    private ArticleMapper ArticleMapper;

    @Override
    public BaseMapper<Article, String> getMappser() {
        return ArticleMapper;
    }

    @Override
    public List<String> getAllCategory() {
        return ArticleMapper.getAllCategory();
    }

    @Override
    @Transactional
    public void insertArticle(Article article) {
        //自动生成ID
        ArticleMapper.insertArticle(article);
        Integer id = article.getId();
        log.info("添加后返回的ID" + id);
        String[] categorys = article.getCategory().split(",");
        for (String category : categorys) {
            article.setId(id);
            article.setCategory(category);
            ArticleMapper.insertArticleType(article);
        }
    }

    @Override
    public Article selectByKey(Integer id) {
        return ArticleMapper.selectByKey(id);
    }

    @Transactional
    @Override
    public boolean updateByKey(Article article) {
        //先删除之前的类别
        ArticleMapper.deleteByArticleId(article.getId());
        String[] categorys = article.getCategory().split(",");
        for (String category : categorys) {
            article.setCategory(category);
            ArticleMapper.insertArticleType(article);
        }
        return ArticleMapper.updateByPrimaryKeySelective(article) > 0;
    }

    @Transactional
    @Override
    public boolean deleteByKey(Integer id) {
        ArticleMapper.deleteByArticleId(id);
        return ArticleMapper.deleteByPrimaryKey(id) >0;
    }
}