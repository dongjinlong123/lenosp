package com.len.entity.wxmenu;

/**
 * 微信菜单基类
 * @author Damon
 */
public class MenuButton
{


    /**
     * 菜单标题，不超过16个字节，子菜单不超过60个字节
     */
    private String name = "";

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
