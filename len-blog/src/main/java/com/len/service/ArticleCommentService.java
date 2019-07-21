package com.len.service;
import com.len.base.BaseService;
import com.len.entity.ArticleComment;

import java.util.List;

public interface ArticleCommentService extends BaseService<ArticleComment, String> {
    List<ArticleComment> selectCommentList(ArticleComment ac);
}