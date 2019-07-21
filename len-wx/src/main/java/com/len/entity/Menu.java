package com.len.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "wx_menu_table")
@Data
public class Menu {
    //菜单id
    @Id
    @Column(name = "row_id")
    private Integer rowId;
    //菜单类型
    @Column(name = "menu_type")
    private String menuType;
    //菜单等级
    @Column(name = "menu_level")
    private String menuLevel;
    //菜单名称
    @Column(name = "menu_name")
    private String menuName;
    //视图跳转的url
    @Column(name = "url")
    private String url;
    //菜单父id
    @Column(name = "menu_parent_id")
    private String menuParentId;
    //菜单排序号
    @Column(name = "menu_order")
    private String menuOrder;
    @Column(name = "data_create")
    private Date dataCreate;
    @Column(name = "create_by")
    private String createBy;
    @Column(name = "data_last_upd")
    private Date dataLastUpd;
    @Column(name = "last_upd_by")
    private String lastUpdBy;

}
