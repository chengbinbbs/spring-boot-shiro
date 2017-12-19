package com.chengbinbbs.springbootshiro.dto;

import com.chengbinbbs.springbootshiro.domain.Role;
import com.chengbinbbs.springbootshiro.domain.User;

import java.io.Serializable;

/**
 * @author zhangcb
 * @created 2017-12-12 15:00.
 */
public class RoleDTO implements Serializable {

    private Long id;

    private String role;

    private String description;

    private String hasRole;

    public RoleDTO() {
    }

    public String isHasRole() {
        return hasRole;
    }

    public void setHasRole(String hasRole) {
        this.hasRole = hasRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
