package com.len.service;

import com.len.base.BaseService;
import com.len.entity.SysRoleUser;
import java.util.List;


public interface RoleUserService  extends BaseService<SysRoleUser,String>{

  int deleteByPrimaryKey(SysRoleUser sysRoleUser);

  @Override
  int insert(SysRoleUser sysRoleUser);

  int selectCountByCondition(SysRoleUser sysRoleUser);

  List<SysRoleUser> selectByCondition(SysRoleUser sysRoleUser);
}
