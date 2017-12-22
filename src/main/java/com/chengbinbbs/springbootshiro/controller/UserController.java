package com.chengbinbbs.springbootshiro.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chengbinbbs.springbootshiro.domain.PageModel;
import com.chengbinbbs.springbootshiro.domain.Role;
import com.chengbinbbs.springbootshiro.domain.User;
import com.chengbinbbs.springbootshiro.dto.RoleDTO;
import com.chengbinbbs.springbootshiro.service.RoleService;
import com.chengbinbbs.springbootshiro.service.UserService;
import com.chengbinbbs.springbootshiro.vo.RoleVO;
import com.chengbinbbs.springbootshiro.vo.UserVO;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author: zhangcb
 * @Description:
 * @Date: Created on 2017/12/7.
 */
@RequiresRoles("admin")
@RequestMapping("/user")
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String listView(){
        return "user/list";
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public String list(@ModelAttribute UserVO userVO) {
        PageModel pageModel = new PageModel();
        int count = userService.queryUserCount(userVO);
        if(count <= 0) return JSON.toJSONString(pageModel);
        PageInfo<User> userList = userService.queryUserList(userVO);
        pageModel.setList(userList.getList());
        pageModel.setCount(count);
        pageModel.setMsg("ok");
        pageModel.setRel(true);
        return JSON.toJSONString(pageModel);
    }

    @RequestMapping(value = "/edit")
    public String edit(String userId, ModelMap model) {
        User user = null;
        if(!StringUtils.isEmpty(userId)) {
            user = userService.selectByPrimaryKey(Long.parseLong(userId));
        }
        List<RoleDTO> roles = Lists.newArrayList();
        if(user == null) {
            user = new User();
        }else{
            List<Long> roleIds = Lists.newArrayList();
            if(!StringUtils.isEmpty(user.getRoles())){
                for (Role role : user.getRoles()) {
                    roleIds.add(role.getId());
                }
            }
            List<Role> roleList = roleService.queryAll();
            if(!StringUtils.isEmpty(roleList)){
                for (Role role : roleList) {
                    RoleDTO dto = new RoleDTO();
                    BeanUtils.copyProperties(role,dto);
                    if(roleIds.contains(role.getId())){
                        dto.setHasRole(true);
                    }else{
                        dto.setHasRole(false);
                    }
                    roles.add(dto);
                }
            }
        }
        model.put("item", user);
        model.put("roles", roles);
        return "user/edit";
    }

    @RequestMapping("/view")
    public String view(String userId, ModelMap model) {
        User user = null;
        if(!StringUtils.isEmpty(userId)) {
            user = userService.selectByPrimaryKey(Long.parseLong(userId));
        }
        if(user == null) user = new User();
        model.put("item", user);
        return "user/view";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(@RequestParam String params) {
        JSONObject po = JSONObject.parseObject(params);
        User user = new User();
        String id = po.getString("id");
        user.setUsername(po.getString("username"));
        user.setPassword(po.getString("password"));
        int result;
        if(StringUtils.isEmpty(id)) {
            // 添加
            result = userService.createUser(user);
        }else {
            // 修改
            user.setId(Long.parseLong(id));
            result = userService.updateByPrimaryKey(user);
        }
        logger.info("保存用户信息,返回:{}", result);
        return result+"";
    }

    @RequestMapping(value = "/del")
    @ResponseBody
    public String del(String userId, ModelMap model) {
        int result = 1;
        if(!StringUtils.isEmpty(userId)) {
            result = userService.deleteByPrimaryKey(Long.parseLong(userId));
        }
        logger.info("删除用户信息,返回:{}", result);
        return result+"";
    }

}
