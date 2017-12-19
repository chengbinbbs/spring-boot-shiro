package com.chengbinbbs.springbootshiro.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chengbinbbs.springbootshiro.domain.PageModel;
import com.chengbinbbs.springbootshiro.domain.Permission;
import com.chengbinbbs.springbootshiro.service.PermissionService;
import com.chengbinbbs.springbootshiro.vo.PermissionVO;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: zhangcb
 * @Description:
 * @Date: Created on 2017/12/7.
 */
@RequestMapping("/permission")
@Controller
public class PermissionController {

    private static final Logger logger = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String listView(){
        return "permission/list";
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public String list(@ModelAttribute PermissionVO permissionVO) {
        PageModel pageModel = new PageModel();
        int count = permissionService.queryPermissionCount(permissionVO);
        if(count <= 0) return JSON.toJSONString(pageModel);
        PageInfo<Permission> permissionList = permissionService.queryPermissionList(permissionVO);
        pageModel.setList(permissionList.getList());
        pageModel.setCount(count);
        pageModel.setMsg("ok");
        pageModel.setRel(true);
        return JSON.toJSONString(pageModel);
    }

    @RequestMapping(value = "/edit")
    public String edit(String permissionId, ModelMap model) {
        Permission permission = null;
        if(!StringUtils.isEmpty(permissionId)) {
            permission = permissionService.selectByPrimaryKey(Long.parseLong(permissionId));
        }
        if(permission == null) permission = new Permission();
        model.put("item", permission);
        return "permission/edit";
    }

    @RequestMapping("/view")
    public String view(String permissionId, ModelMap model) {
        Permission permission = null;
        if(!StringUtils.isEmpty(permissionId)) {
            permission = permissionService.selectByPrimaryKey(Long.parseLong(permissionId));
        }
        if(permission == null) permission = new Permission();
        model.put("item", permission);
        return "permission/view";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(@RequestParam String params) {
        JSONObject po = JSONObject.parseObject(params);
        Permission permission = new Permission();
        String id = po.getString("id");
        permission.setPermission(po.getString("permission"));
        permission.setDescription(po.getString("description"));
        permission.setAvailable(true);
        int result;
        if(StringUtils.isEmpty(id)) {
            // 添加
            result = permissionService.createPermission(permission);
        }else {
            // 修改
            permission.setId(Long.parseLong(id));
            result = permissionService.updateByPrimaryKey(permission);
        }
        logger.info("保存角色信息,返回:{}", result);
        return result+"";
    }

    @RequestMapping(value = "/del")
    @ResponseBody
    public String del(String permissionId, ModelMap model) {
        int result = 1;
        if(!StringUtils.isEmpty(permissionId)) {
            result = permissionService.deleteByPrimaryKey(Long.parseLong(permissionId));
        }
        logger.info("删除角色信息,返回:{}", result);
        return result+"";
    }

}
