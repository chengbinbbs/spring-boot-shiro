package com.chengbinbbs.springbootshiro.service;


import com.chengbinbbs.springbootshiro.domain.Permission;
import com.chengbinbbs.springbootshiro.vo.PermissionVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author zhangcb
 * @created on 2017/12/5.
 */
public interface PermissionService {
    public int createPermission(Permission permission);
    public void deletePermission(Long permissionId);

    int deleteByPrimaryKey(long id);

    int updateByPrimaryKey(Permission permission);

    Permission selectByPrimaryKey(long id);

    PageInfo<Permission> queryPermissionList(PermissionVO permissionVO);

    int queryPermissionCount(PermissionVO permissionVO);

    List<Permission> queryAll();
}
