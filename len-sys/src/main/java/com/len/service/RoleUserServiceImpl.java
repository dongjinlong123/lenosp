package com.len.service;

import com.len.base.BaseMapper;
import com.len.base.impl.BaseServiceImpl;
import com.len.entity.SysRoleUser;
import com.len.mapper.SysRoleUserMapper;
import com.len.service.RoleUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoleUserServiceImpl extends BaseServiceImpl<SysRoleUser,String> implements
    RoleUserService {

  @Autowired
  private SysRoleUserMapper sysRoleUserMapper;

  @Override
  public BaseMapper<SysRoleUser, String> getMappser() {
    return sysRoleUserMapper;
  }

  @Override
  public int deleteByPrimaryKey(SysRoleUser sysRoleUser) {
    return sysRoleUserMapper.deleteByPrimaryKey(sysRoleUser);
  }

  @Override
  public int selectCountByCondition(SysRoleUser sysRoleUser) {
    return sysRoleUserMapper.selectCountByCondition(sysRoleUser);
  }

  @Override
  public List<SysRoleUser> selectByCondition(SysRoleUser sysRoleUser) {
    return sysRoleUserMapper.selectByCondition(sysRoleUser);
  }
}
