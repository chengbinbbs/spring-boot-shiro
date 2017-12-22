<div style="margin: 15px;">
	<form class="layui-form">
		<#if item.id??>
		<div class="layui-form-item">
			<label class="layui-form-label">用户ID</label>
			<div class="layui-input-block">
				<input type="text" name="id" placeholder="请输入用户ID" autocomplete="off" class="layui-input"  readonly="readonly" value="${item.id?if_exists }">
			</div>
		</div>
		</#if>
		<div class="layui-form-item">
			<label class="layui-form-label">用户名称</label>
			<div class="layui-input-block">
				<input type="text" name="username" lay-verify="required" placeholder="请输入用户名称" autocomplete="off" class="layui-input" value="${item.username?if_exists }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">用户密码</label>
			<div class="layui-input-block">
                <input type="password" name="password" lay-verify="required" placeholder="请输入用户密码" autocomplete="off" class="layui-input" value="${item.password?if_exists }">
            </div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">分配角色</label>
			<div class="layui-input-block">
				<#list roles as role>
                    <input type="checkbox" name="role" value="${role.id}" title="${role.description}">
                    <div class="layui-unselect
                    	<#list (item.roles) as r>
				<#if (role.id)?? && (r.id)?? && role.id == r.id>
                                        layui-form-checked
                                </#if>
                        </#list>
			layui-form-checkbox" lay-skin><span>${role.description}</span><i class="layui-icon"></i></div>
				</#list>
			</div>
		</div>
		<button lay-filter="edit" lay-submit style="display: none;"></button>
	</form>
</div>
