package com.len.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.len.base.BaseMapper;
import com.len.base.impl.BaseServiceImpl;
import com.len.entity.Menu;
import com.len.entity.wxmenu.LevelMenu;
import com.len.entity.wxmenu.MenuButton;
import com.len.entity.wxmenu.MenuToWxVo;
import com.len.entity.wxmenu.SubMenuButton;
import com.len.mapper.MenuMapper;
import com.len.service.WxMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service("wxMenuService")
public class WxMenuServiceImpl extends BaseServiceImpl<Menu, String> implements WxMenuService {

    @Autowired
	private MenuMapper menuMapper;
    @Override
    public BaseMapper<Menu, String> getMappser() {
        return menuMapper;
    }

    /**
     * 一级菜单
     */
    private static final String FIRST_MENU = "1";
    /**
     * 二级菜单
     */
    private static final String SECOND_MENU = "2";
    /**
     * 存在二级菜单的一级菜单类型
     */
    private static final String SUB_BUTTON = "sub_button";
    @Override
    public String initMenu() {
        /**
         * 获取所有的一级菜单
         */
        List<Menu> list = getMenuByMenuLevel(FIRST_MENU,"0");
        /**
         * 初始化菜单
         */
        MenuToWxVo vo = setMenuVO(list);
        String str = JSONObject.toJSONString(vo);
        return str;
    }
    /**
     * 根据菜单级别及父菜单的id获取菜单信息
     * 一级菜单默认父菜单为0
     * @param menuLevel
     * @param menuParentId 父菜单id 一级菜单默认父菜单为0
     * @return
     */
    private List<Menu> getMenuByMenuLevel(String menuLevel, String menuParentId){
        Menu m = new Menu();
        m.setMenuLevel(menuLevel);
        m.setMenuParentId(menuParentId);
        List<Menu> list = this.selectListByPage(m);
        return list;
    }
    /**
     * 根据一级菜单封装菜单信息
     * @param list
     * @return
     */
    private MenuToWxVo setMenuVO(List<Menu> list) {
        MenuToWxVo vo = new MenuToWxVo();

        if(list == null || list.isEmpty()) {
            log.info("菜单配置为空");
            return null;
        }
        //初始化一级菜单数量
        MenuButton[] button = new MenuButton[list.size()];
        vo.setButton(button);
        for (int i=0;i<list.size();i++) {
            //判断一级菜单是否包含二级菜单
            if(SUB_BUTTON.equals(list.get(i).getMenuType())) {
                button[i] = getLevelMenu(list.get(i));
            }else {
                button[i] = getSubMenuButton(list.get(i));
            }
        }

        return vo;
    }
    /**
     * 获取导航菜单
     * @param menu
     * @return
     */
    private MenuButton getLevelMenu(Menu menu) {
        LevelMenu fisrtMenu = new LevelMenu();
        fisrtMenu.setName(menu.getMenuName());
        //获取到一级菜单的id
        Integer id = menu.getRowId();
        //查询关联的二级菜单
        List<Menu> list = getMenuByMenuLevel(SECOND_MENU,id +"");
        if(list == null || list.isEmpty()) {
            log.info("菜单配置为空");
            return null;
        }
        SubMenuButton[] sub_button = new SubMenuButton[list.size()];
        fisrtMenu.setSub_button(sub_button);
        for (int i=0;i<list.size();i++) {
            sub_button[i] = (SubMenuButton) getSubMenuButton(list.get(i));
        }
        return fisrtMenu;
    }
    /**
     * 获取功能菜单
     * @param menu
     * @return
     */
    private MenuButton getSubMenuButton(Menu menu) {
        //根据二级菜单的功能来判断,目前仅view 功能
        SubMenuButton button = new SubMenuButton();
        button.setName(menu.getMenuName());
        button.setType(menu.getMenuType());
        //对应的跳转页面功能
        button.setUrl(menu.getUrl());
        return button;
    }


}