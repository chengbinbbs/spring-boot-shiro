package com.chengbinbbs.springbootshiro.service.impl;

import com.chengbinbbs.springbootshiro.dao.UserMapper;
import com.chengbinbbs.springbootshiro.domain.User;
import com.chengbinbbs.springbootshiro.service.UserService;
import com.chengbinbbs.springbootshiro.util.PasswordHelper;
import com.chengbinbbs.springbootshiro.vo.UserVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhangcb
 * @created 2017-12-05 14:44.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    private PasswordHelper passwordHelper = new PasswordHelper();

    public User selectByPrimaryKey(Long id){
        return userMapper.selectByPrimaryKey(id);
    }

    public int insert(User record){
        return userMapper.insert(record);
    }

    public int updateByPrimaryKey(User record){
        return userMapper.updateByPrimaryKeySelective(record);
    }

    public int deleteByPrimaryKey(Long id){
        return userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 创建用户
     * @param user
     */
    public int createUser(User user) {
        //加密密码
        passwordHelper.encryptPassword(user);
        return userMapper.insert(user);
    }

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public void changePassword(Long userId, String newPassword) {
        User user =userMapper.selectByPrimaryKey(userId);
        user.setPassword(newPassword);
        passwordHelper.encryptPassword(user);
        userMapper.updateByPrimaryKey(user);
    }

    /**
     * 添加用户-角色关系
     * @param userId
     * @param roleIds
     */
    public void correlationRoles(Long userId, List<Long> roleIds) {
        userMapper.correlationRoles(userId, roleIds);
    }


    /**
     * 移除用户-角色关系
     * @param userId
     * @param roleIds
     */
    public void uncorrelationRoles(Long userId, List<Long> roleIds) {
        userMapper.uncorrelationRoles(userId, roleIds);
    }

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    /**
     * 根据用户名查找其角色
     * @param username
     * @return
     */
    public Set<String> findRoles(String username) {
        List<String> roleList = userMapper.findRoles(username);
        return new HashSet(roleList);
    }

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username) {
        List<String> permissionList =  userMapper.findPermissions(username);
        return new HashSet(permissionList);
    }

    @Override
    public int queryUserCount(UserVO userVO) {
        return userMapper.queryUserCount(userVO);
    }

    @Override
    public PageInfo<User> queryUserList(UserVO userVO) {
        PageHelper.startPage(userVO.getPageIndex(),userVO.getPageSize());
        List<User> userList = userMapper.queryUserList(userVO);
        return new PageInfo<User>(userList);
    }
}
