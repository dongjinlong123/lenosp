package com.len.base;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;

import java.util.List;

/**
 *@RegisterMapper 注解可以避免 mappers 参数配置，
 * 通用 Mapper 检测到该接口被继承时，会自动注册。
 * @param <T>
 */
@tk.mybatis.mapper.annotation.RegisterMapper
public interface LenInsertListAndinsertUseGeneratedKeysMapper<T> {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @InsertProvider(type = MySpecialProvider.class, method = "dynamicSQL")
    int insertList(List<T> recordList);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @InsertProvider(type = MySpecialProvider.class, method = "dynamicSQL")
    int insertUseGeneratedKeys(T record);
}
