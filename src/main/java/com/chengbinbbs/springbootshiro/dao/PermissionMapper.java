package com.chengbinbbs.springbootshiro.dao;

import com.chengbinbbs.springbootshiro.domain.Permission;
import com.chengbinbbs.springbootshiro.vo.PermissionVO;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    int queryPermissionCount(PermissionVO permissionVO);

    List<Permission> queryPermissionList(PermissionVO permissionVO);

    List<Permission> queryAll();
}