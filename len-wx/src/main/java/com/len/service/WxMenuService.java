package com.len.service;
import com.len.base.BaseService;
import com.len.entity.Menu;
public interface WxMenuService extends BaseService<Menu, String> {
    /**
     * 初始化菜单字符串
     * @return
     */
    public String initMenu();
}