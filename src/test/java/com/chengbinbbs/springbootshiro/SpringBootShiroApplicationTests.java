package com.chengbinbbs.springbootshiro;

import com.chengbinbbs.springbootshiro.domain.Permission;
import com.chengbinbbs.springbootshiro.domain.Role;
import com.chengbinbbs.springbootshiro.domain.User;
import com.chengbinbbs.springbootshiro.service.PermissionService;
import com.chengbinbbs.springbootshiro.service.RoleService;
import com.chengbinbbs.springbootshiro.service.UserService;
import com.chengbinbbs.springbootshiro.shiro.realm.UserRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootShiroApplicationTests {

	@Autowired
	protected PermissionService permissionService;
	@Autowired
	protected RoleService       roleService;
	@Autowired
	protected UserService       userService;

	@Autowired
	private UserRealm userRealm;



	protected String password = "123";

	protected Permission p1;
	protected Permission p2;
	protected Permission p3;
	protected Role       r1;
	protected Role       r2;
	protected User       u1;
	protected User       u2;
	protected User       u3;
	protected User       u4;

	//@Before
	public void setUp() {

		//1、新增权限
		p1 = new Permission("user:create", "用户模块新增", Boolean.TRUE);
		p2 = new Permission("user:update", "用户模块修改", Boolean.TRUE);
		p3 = new Permission("menu:create", "菜单模块新增", Boolean.TRUE);
		permissionService.createPermission(p1);
		permissionService.createPermission(p2);
		permissionService.createPermission(p3);
		//2、新增角色
		r1 = new Role("admin", "管理员", Boolean.TRUE);
		r2 = new Role("user", "用户管理员", Boolean.TRUE);
		roleService.createRole(r1);
		roleService.createRole(r2);
		//3、关联角色-权限
		List<Long> permissions = new ArrayList<>();
		permissions.add(p1.getId());
		roleService.correlationPermissions(r1.getId(), permissions);
		roleService.correlationPermissions(r2.getId(), permissions);
		permissions = new ArrayList<>();
		permissions.add(p2.getId());
		roleService.correlationPermissions(r1.getId(), permissions);
		roleService.correlationPermissions(r2.getId(), permissions);
		permissions = new ArrayList<>();
		permissions.add(p3.getId());
		roleService.correlationPermissions(r1.getId(), permissions);


		//4、新增用户
		u1 = new User("zhang", password);
		u2 = new User("li", password);
		u3 = new User("wu", password);
		u4 = new User("wang", password);
		u4.setLocked(Boolean.TRUE);
		userService.createUser(u1);
		userService.createUser(u2);
		userService.createUser(u3);
		userService.createUser(u4);
		//5、关联用户-角色
		List<Long> roles = new ArrayList<>();
		roles.add(r1.getId());
		userService.correlationRoles(u1.getId(), roles);

	}


}
