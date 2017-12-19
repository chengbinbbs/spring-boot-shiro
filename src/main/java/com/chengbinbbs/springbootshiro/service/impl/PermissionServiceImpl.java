package com.chengbinbbs.springbootshiro.service.impl;

import com.chengbinbbs.springbootshiro.dao.PermissionMapper;
import com.chengbinbbs.springbootshiro.domain.Permission;
import com.chengbinbbs.springbootshiro.service.PermissionService;
import com.chengbinbbs.springbootshiro.vo.PermissionVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangcb
 * @created 2017-12-05 14:44.
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    public int createPermission(Permission permission) {
        return permissionMapper.insert(permission);
    }

    public void deletePermission(Long permissionId) {
        permissionMapper.deleteByPrimaryKey(permissionId);
    }

    @Override
    public int deleteByPrimaryKey(long id) {
        return permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(Permission permission) {
        return permissionMapper.updateByPrimaryKeySelective(permission);
    }

    @Override
    public Permission selectByPrimaryKey(long id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<Permission> queryPermissionList(PermissionVO permissionVO) {
        PageHelper.startPage(permissionVO.getPageIndex(),permissionVO.getPageSize());
        List<Permission> permissionList = permissionMapper.queryPermissionList(permissionVO);
        return new PageInfo<Permission>(permissionList);
    }

    @Override
    public int queryPermissionCount(PermissionVO permissionVO) {
        return permissionMapper.queryPermissionCount(permissionVO);
    }

    public List<Permission> queryAll(){
        return permissionMapper.queryAll();
    }
}
