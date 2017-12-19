package com.chengbinbbs.springbootshiro.dao;

import com.chengbinbbs.springbootshiro.domain.User;
import com.chengbinbbs.springbootshiro.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    void correlationRoles(@Param("userId")Long userId, @Param("roleIds")List<Long> roleIds);

    void uncorrelationRoles(@Param("userId")Long userId, @Param("roleIds")List<Long> roleIds);

    User findByUsername(@Param("username") String username);

    List<String> findRoles(@Param("username")String username);

    List<String> findPermissions(@Param("username")String username);

    int queryUserCount(UserVO userVO);

    List<User> queryUserList(UserVO userVO);
}