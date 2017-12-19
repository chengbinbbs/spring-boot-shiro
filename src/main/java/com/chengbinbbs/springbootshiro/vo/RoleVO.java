package com.chengbinbbs.springbootshiro.vo;


import java.io.Serializable;

/**
 * @author zhangcb
 * @created 2017-12-08 17:57.
 */
public class RoleVO extends PageVO implements Serializable{

    private Integer id;

    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
