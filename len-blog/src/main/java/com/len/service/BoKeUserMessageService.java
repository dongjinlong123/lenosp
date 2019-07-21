package com.len.service;
import com.len.base.BaseService;
import com.len.entity.ArticleSave;
import com.len.entity.BoKeUserMessage;

import java.util.List;

public interface BoKeUserMessageService extends BaseService<BoKeUserMessage, String> {
    Integer getNewsCount(BoKeUserMessage b);

}