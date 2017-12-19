package com.chengbinbbs.springbootshiro.service.impl;

import com.chengbinbbs.springbootshiro.dao.RoleMapper;
import com.chengbinbbs.springbootshiro.domain.Role;
import com.chengbinbbs.springbootshiro.service.RoleService;
import com.chengbinbbs.springbootshiro.vo.RoleVO;
import com.chengbinbbs.springbootshiro.vo.UserVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangcb
 * @created 2017-12-05 14:43.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public int createRole(Role role) {
        return roleMapper.insert(role);
    }

    public void deleteRole(Long roleId) {
        roleMapper.deleteByPrimaryKey(roleId);
    }

    /**
     * 添加角色-权限之间关系
     * @param roleId
     * @param permissionIds
     */
    public void correlationPermissions(@Param("roleId") Long roleId, @Param("permissionIds")List<Long> permissionIds) {
        roleMapper.correlationPermissions(roleId, permissionIds);
    }

    /**
     * 移除角色-权限之间关系
     * @param roleId
     * @param permissionIds
     */
    public void uncorrelationPermissions(@Param("roleId")Long roleId, @Param("permissionIds")List<Long> permissionIds) {
        roleMapper.uncorrelationPermissions(roleId, permissionIds);
    }

    @Override
    public int queryRoleCount(RoleVO roleVO) {
        return roleMapper.queryRoleCount(roleVO);
    }

    @Override
    public PageInfo<Role> queryRoleList(RoleVO roleVO) {
        PageHelper.startPage(roleVO.getPageIndex(),roleVO.getPageSize());
        List<Role> roleList = roleMapper.queryRoleList(roleVO);
        return new PageInfo<Role>(roleList);
    }

    @Override
    public Role selectByPrimaryKey(long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(Role role) {
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public int deleteByPrimaryKey(long id) {
        return roleMapper.deleteByPrimaryKey(id);
    }

    public List<Role> queryAll(){
        return roleMapper.queryAll();
    }
}
