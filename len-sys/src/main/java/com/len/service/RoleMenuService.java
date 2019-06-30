package com.len.service;

import com.len.base.BaseService;
import com.len.entity.SysRoleMenu;
import java.util.List;


public interface RoleMenuService extends BaseService<SysRoleMenu,String>{

    List<SysRoleMenu> selectByCondition(SysRoleMenu sysRoleMenu);

    int  selectCountByCondition(SysRoleMenu sysRoleMenu);

    int deleteByPrimaryKey(SysRoleMenu sysRoleMenu);
}
