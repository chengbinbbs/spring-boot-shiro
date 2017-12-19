package com.chengbinbbs.springbootshiro.vo;


import java.io.Serializable;

/**
 * @author zhangcb
 * @created 2017-12-08 17:57.
 */
public class PermissionVO extends PageVO implements Serializable{

    private Integer id;

    private String permission;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
