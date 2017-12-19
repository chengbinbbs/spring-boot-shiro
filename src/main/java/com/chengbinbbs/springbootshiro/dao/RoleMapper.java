package com.chengbinbbs.springbootshiro.dao;

import com.chengbinbbs.springbootshiro.domain.Role;
import com.chengbinbbs.springbootshiro.vo.RoleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    void correlationPermissions(@Param("roleId")Long roleId, @Param("permissionIds")List<Long> permissionIds);

    void uncorrelationPermissions(@Param("roleId")Long roleId, @Param("permissionIds")List<Long> permissionIds);

    int queryRoleCount(RoleVO roleVO);

    List<Role> queryRoleList(RoleVO roleVO);

    List<Role> queryAll();
}