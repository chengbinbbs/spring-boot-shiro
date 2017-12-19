package com.chengbinbbs.springbootshiro.service;


import com.chengbinbbs.springbootshiro.domain.User;
import com.chengbinbbs.springbootshiro.vo.UserVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Set;

/**
 * @author zhangcb
 * @created on 2017/12/5.
 */
public interface UserService {

    User selectByPrimaryKey(Long id);

    int insert(User record);

    int updateByPrimaryKey(User record);

    int deleteByPrimaryKey(Long id);
    /**
     * 创建用户
     * @param user
     */
    public int createUser(User user);

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public void changePassword(Long userId, String newPassword);

    /**
     * 添加用户-角色关系
     * @param userId
     * @param roleIds
     */
    public void correlationRoles(Long userId, List<Long> roleIds);


    /**
     * 移除用户-角色关系
     * @param userId
     * @param roleIds
     */
    public void uncorrelationRoles(Long userId, List<Long> roleIds);

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User findByUsername(String username);

    /**
     * 根据用户名查找其角色
     * @param username
     * @return
     */
    public Set<String> findRoles(String username);

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username);

    int queryUserCount(UserVO userVO);

    PageInfo<User> queryUserList(UserVO userVO);
}
