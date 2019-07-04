package com.len.service.impl;
import com.len.base.BaseMapper;
import com.len.base.impl.BaseServiceImpl;
import com.len.entity.Article;
import com.len.mapper.ArticleMapper;
import com.len.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ArticleServiceImpl extends BaseServiceImpl<Article, String> implements ArticleService {
	@Autowired
	private ArticleMapper ArticleMapper;
    @Override
    public BaseMapper<Article, String> getMappser() {
        return ArticleMapper;
    }
}