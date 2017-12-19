<div style="margin: 15px;">
	<form class="layui-form">
		<#if item.id??>
		<div class="layui-form-item">
			<label class="layui-form-label">权限ID</label>
			<div class="layui-input-block">
				<input type="text" name="id" placeholder="请输入权限ID" autocomplete="off" class="layui-input"  readonly="readonly" value="${item.id?if_exists }">
			</div>
		</div>
		</#if>
		<div class="layui-form-item">
			<label class="layui-form-label">权限名称</label>
			<div class="layui-input-block">
				<input type="text" name="permission" lay-verify="required" placeholder="请输入权限名称" autocomplete="off" class="layui-input" value="${item.permission?if_exists }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">权限描叙</label>
			<div class="layui-input-block">
                <input type="text" name="description" lay-verify="required" placeholder="请输入权限描叙" autocomplete="off" class="layui-input" value="${item.description?if_exists }">
            </div>
		</div>
		<button lay-filter="edit" lay-submit style="display: none;"></button>
	</form>
</div>