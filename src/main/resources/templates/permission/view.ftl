<div style="margin: 15px;">
	<form class="layui-form">
		<div class="layui-form-item">
			<label class="layui-form-label">权限ID</label>
			<div class="layui-input-block">
				<input type="text" class="layui-input" disabled="disabled" value="${item.id?if_exists }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">权限名称</label>
			<div class="layui-input-block">
				<input type="text" disabled="disabled" class="layui-input" value="${item.permission?if_exists }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">权限描叙</label>
            <div class="layui-input-block">
                <input type="text" disabled="disabled" class="layui-input" value="${item.description?if_exists }">
            </div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">状态</label>
			<div class="layui-input-block">
			<#if item.available == true>
                <input type="text" style="color: green" disabled="disabled" class="layui-input" value="启用" }">
			<#else>
                <input type="text" style="color: red" disabled="disabled" class="layui-input" value="停止" }">
			</#if>
			</div>
		</div>
		<button lay-filter="edit" lay-submit style="display: none;"></button>
	</form>
</div>