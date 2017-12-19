package com.chengbinbbs.springbootshiro.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chengbinbbs.springbootshiro.domain.PageModel;
import com.chengbinbbs.springbootshiro.domain.Permission;
import com.chengbinbbs.springbootshiro.domain.Role;
import com.chengbinbbs.springbootshiro.dto.PermissionDTO;
import com.chengbinbbs.springbootshiro.service.PermissionService;
import com.chengbinbbs.springbootshiro.service.RoleService;
import com.chengbinbbs.springbootshiro.vo.RoleVO;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/role")
@Controller
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String listView(){
        return "role/list";
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public String list(@ModelAttribute RoleVO roleVO) {
        PageModel pageModel = new PageModel();
        int count = roleService.queryRoleCount(roleVO);
        if(count <= 0) return JSON.toJSONString(pageModel);
        PageInfo<Role> roleList = roleService.queryRoleList(roleVO);
        pageModel.setList(roleList.getList());
        pageModel.setCount(count);
        pageModel.setMsg("ok");
        pageModel.setRel(true);
        return JSON.toJSONString(pageModel);
    }

    @RequestMapping(value = "/edit")
    public String edit(String roleId, ModelMap model) {
        Role role = null;
        if(!StringUtils.isEmpty(roleId)) {
            role = roleService.selectByPrimaryKey(Long.parseLong(roleId));
        }
        List<PermissionDTO> permissions = Lists.newArrayList();
        if(role == null) {
            role = new Role();
        }else{
            List<Long> perIds = Lists.newArrayList();
            if(!StringUtils.isEmpty(role.getPermissions())){
                for (Permission permission : role.getPermissions()) {
                    perIds.add(permission.getId());
                }
            }
            List<Permission> permissionList = permissionService.queryAll();
            if(!StringUtils.isEmpty(permissionList)){
                for (Permission permission : permissionList) {
                    PermissionDTO dto = new PermissionDTO();
                    BeanUtils.copyProperties(permission,dto);
                    if(perIds.contains(permission.getId())){
                        dto.setHasPermission("layui-form-checkbox");
                    }
                    permissions.add(dto);
                }
            }
        }
        model.put("item", role);
        model.put("permissions", permissions);
        return "role/edit";
    }

    @RequestMapping("/view")
    public String view(String roleId, ModelMap model) {
        Role role = null;
        if(!StringUtils.isEmpty(roleId)) {
            role = roleService.selectByPrimaryKey(Long.parseLong(roleId));
        }
        if(role == null) role = new Role();
        model.put("item", role);
        return "role/view";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(@RequestParam String params) {
        JSONObject po = JSONObject.parseObject(params);
        Role role = new Role();
        String id = po.getString("id");
        role.setRole(po.getString("role"));
        role.setDescription(po.getString("description"));
        role.setAvailable(true);
        int result;
        if(StringUtils.isEmpty(id)) {
            // 添加
            result = roleService.createRole(role);
        }else {
            // 修改
            role.setId(Long.parseLong(id));
            result = roleService.updateByPrimaryKey(role);
        }
        logger.info("保存角色信息,返回:{}", result);
        return result+"";
    }

    @RequestMapping(value = "/del")
    @ResponseBody
    public String del(String userId, ModelMap model) {
        int result = 1;
        if(!StringUtils.isEmpty(userId)) {
            result = roleService.deleteByPrimaryKey(Long.parseLong(userId));
        }
        logger.info("删除角色信息,返回:{}", result);
        return result+"";
    }

}
