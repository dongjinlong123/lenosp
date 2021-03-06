package com.len.service.impl;

import com.len.base.BaseMapper;
import com.len.base.impl.BaseServiceImpl;
import com.len.entity.Article;
import com.len.entity.ArticlePraise;
import com.len.entity.ArticleSave;
import com.len.mapper.ArticleMapper;
import com.len.redis.RedisService;
import com.len.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
    @CacheEvict(value = "article" , key = "#p0.id")
    public boolean updateByKey(Article article) {
        //先删除之前的类别
        ArticleMapper.deleteByArticleId(article.getId());
        String[] categorys = article.getCategory().split(",");
        for (String category : categorys) {
            article.setCategory(category);
            ArticleMapper.insertArticleType(article);
        }
        //删除缓存
        //redisService.delObj("article-" + article.getId());
        return ArticleMapper.updateByPrimaryKeySelective(article) > 0;
    }

    @Transactional
    @CacheEvict(value = "article" , key = "#id")
    @Override
    public boolean deleteByKey(Integer id) {
        //删除缓存
        //redisService.delObj("article-" + id);
        ArticleMapper.deleteAllByArticleId(id);
        return true;
    }

    @Override
    public List<ArticlePraise> selectPraiseListByPage(Integer id) {
        return ArticleMapper.selectPraiseListByPage(id);
    }

    @Override
    public Integer praiseCount(Integer id) {
        return ArticleMapper.praiseCount(id);
    }

    @Override
    public List<ArticleSave> selectSaveListByPage(Integer id) {
        return ArticleMapper.selectSaveListByPage(id);
    }

    @Override
    public Integer saveCount(Integer id) {
        return ArticleMapper.saveCount(id);
    }

    @Override
    public List<Article> selectByCategory(String category) {
        return ArticleMapper.selectByCategory(category);
    }

    @Override
    public List<Article> selectListByPage(Article article, Integer pageSize, Integer pagination) {
        return ArticleMapper.selectListByMyPage(article, pageSize, pagination);
    }

    @Override
    public List<Map<String, Object>> getClickRecommendList() {
        return ArticleMapper.getClickRecommendList();
    }

    @Override
    public List<Map<String, Object>> getCommentRecommendList() {
        return ArticleMapper.getCommentRecommendList();
    }

    @Override
    public List<Map<String, Object>> getCommentUserList() {
        return ArticleMapper.getCommentUserList();
    }

    @Override
    public List<Map<String, Object>> getCommentList() {
        return ArticleMapper.getCommentList();
    }

    @Override
    public int getArticleCount(Article article) {
        return ArticleMapper.getArticleCount(article);
    }

    @Override
    public boolean updateById(Article article) {
        if(ArticleMapper.updateByPrimaryKeySelective(article) >0){
            return true;
        }
        return false;
    }

    /**
     * 通过类别名称删除类别
     *
     * @param category
     * @return
     */
    @Override
    @Transactional
    @CacheEvict(value = "category",allEntries=true)
    public boolean deleteCategoryByCategoryName(String category) {
        List<Article> articles = this.ArticleMapper.selectByCategory(category);
        if(articles != null && articles.size() > 0){
            for (Article a: articles
                 ) {
                ArticleMapper.deleteByArticleId(a.getId());
            }

        }
        //再删除类别
        ArticleMapper.deleteCategoryByCategoryName(category);
        return true;
    }


}