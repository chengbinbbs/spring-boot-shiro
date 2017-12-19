package com.chengbinbbs.springbootshiro.service;


import com.chengbinbbs.springbootshiro.domain.Role;
import com.chengbinbbs.springbootshiro.vo.RoleVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author zhangcb
 * @created on 2017/12/5.
 */
public interface RoleService {

    public int createRole(Role role);
    public void deleteRole(Long roleId);

    /**
     * 添加角色-权限之间关系
     * @param roleId
     * @param permissionIds
     */
    public void correlationPermissions(Long roleId, List<Long> permissionIds);

    /**
     * 移除角色-权限之间关系
     * @param roleId
     * @param permissionIds
     */
    public void uncorrelationPermissions(Long roleId, List<Long> permissionIds);

    int queryRoleCount(RoleVO roleVO);

    PageInfo<Role> queryRoleList(RoleVO roleVO);

    Role selectByPrimaryKey(long l);

    int updateByPrimaryKey(Role role);

    int deleteByPrimaryKey(long l);

    List<Role> queryAll();
}
