package com.len.mapper;

import com.len.entity.ArticlePraise;
import com.len.entity.ArticleSave;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BokeIntfMapper {
    List<Map<String, Object>> getCategoryList();

    Integer getSignNum(String openId);

    List<Map<String, Object>> getAllSignTime(String openId);

    void insertSignDate(Map<String, Object> param);

    Integer getIsCollect(@Param("id") Integer id, @Param("openId") String openId, @Param("flag") Integer flag);


    Integer getArticleSaveId(@Param("id") Integer id, @Param("openId") String openId);

    void insertArticleSave(ArticleSave as);

    void updateArticleSave(ArticleSave as);

    Integer getIsLiked(@Param("id") Integer id, @Param("openId") String openId, @Param("flag") Integer flag);

    Integer getArticlePraiseId(@Param("id") Integer id, @Param("openId") String openId);

    void insertArticlePraise(ArticlePraise ap);

    void updateArticlePraise(ArticlePraise ap);

    boolean addReadCount(@Param("id") Integer id);

    List<ArticleSave> getCollectList(@Param("userId") Integer userId);
}
