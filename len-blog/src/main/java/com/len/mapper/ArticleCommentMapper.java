package com.len.mapper;
import com.len.base.BaseMapper;
import com.len.entity.ArticleComment;

import java.util.List;

public interface ArticleCommentMapper extends BaseMapper<ArticleComment, String> {
    List<ArticleComment> selectCommentList(ArticleComment ac);

    void add(ArticleComment articleComment);
}