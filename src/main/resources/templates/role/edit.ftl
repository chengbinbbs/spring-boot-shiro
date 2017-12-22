<div style="margin: 15px;">
	<form class="layui-form">
		<#if item.id??>
		<div class="layui-form-item">
			<label class="layui-form-label">角色ID</label>
			<div class="layui-input-block">
				<input type="text" name="id" placeholder="请输入角色ID" autocomplete="off" class="layui-input"  readonly="readonly" value="${item.id?if_exists }">
			</div>
		</div>
		</#if>
		<div class="layui-form-item">
			<label class="layui-form-label">角色名称</label>
			<div class="layui-input-block">
				<input type="text" name="role" lay-verify="required" placeholder="请输入角色名称" autocomplete="off" class="layui-input" value="${item.role?if_exists }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">角色描叙</label>
			<div class="layui-input-block">
                <input type="text" name="description" lay-verify="required" placeholder="请输入角色描叙" autocomplete="off" class="layui-input" value="${item.description?if_exists }">
            </div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">分配权限</label>
			<div class="layui-input-block">
				<#list permissions as permission>
					<input type="checkbox" name="role" value="${permission.id}" title="${permission.description}">
                    			<div class="layui-unselect
                     			<#list (item.permissions) as p>
						<#if (p.id)?? && (permission.id)?? && permission.id == p.id>
                                        	layui-form-checked
                                    		</#if>
                        		</#list>
                     			layui-form-checkbox" lay-skin><span>${permission.description}</span><i class="layui-icon"></i></div>
				</#list>
			</div>
		</div>
		<button lay-filter="edit" lay-submit style="display: none;"></button>
	</form>
</div>
