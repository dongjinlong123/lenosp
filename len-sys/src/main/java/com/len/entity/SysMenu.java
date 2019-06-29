package com.len.entity;

import com.len.validator.group.AddGroup;
import com.len.validator.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "sys_menu")
@Data
@ToString
@EqualsAndHashCode
public class SysMenu {
    @Id
    @Column(name = "id")
    private String id;

    @NotEmpty(message = "菜单名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    @Column(name = "p_id")
    private String pId;

    private String url;

    /**
     * 排序字段
     */
    @Length(min = 1,max = 4, message = "序号长度不对")
    @Column(name = "order_num")
    private Integer orderNum;

    /**
     * layui升级 ， 显示为title
     */
    @Transient
    private String title;
    /**
     * 图标
     */
    private String icon;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 权限
     */
    private String permission;

    /**
     * 1栏目2菜单
     */
    @Column(name = "menu_type")
    private Byte menuType;

    private int num;

    private List<SysRole> roleList;

    private static final long serialVersionUID = 1L;

    private List<SysMenu> children=new ArrayList<SysMenu>();

    public void addChild(SysMenu sysMenu){
        children.add(sysMenu);
    }
}