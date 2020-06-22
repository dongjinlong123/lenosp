package com.len.service.impl;
import com.len.base.BaseMapper;
import com.len.base.impl.BaseServiceImpl;
import com.len.entity.ArticleComment;
import com.len.mapper.ArticleCommentMapper;
import com.len.service.ArticleCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleCommentServiceImpl extends BaseServiceImpl<ArticleComment, String> implements ArticleCommentService {
	@Autowired
	private ArticleCommentMapper articleCommentMapper;
    @Override
    public BaseMapper<ArticleComment, String> getMappser() {
        return articleCommentMapper;
    }

    @Override
    public List<ArticleComment> selectCommentList(ArticleComment ac) {
        return articleCommentMapper.selectCommentList(ac);
    }

    @Override
    public void add(ArticleComment articleComment) {
        articleCommentMapper.add(articleComment);
    }
}