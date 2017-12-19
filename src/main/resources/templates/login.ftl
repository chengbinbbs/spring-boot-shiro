<html>

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>XXPAY - 运营平台登录</title>
		<link rel="icon" type="image/x-icon" href="/images/favicon.ico">
		<link rel="stylesheet" href="/plugins/layui/css/layui.css" media="all" />
		<link rel="stylesheet" href="/css/login.css" />
	</head>

	<body class="beg-login-bg">
		<div class="beg-login-box">
			<header>
				<h1>XXX运营平台[演示版]</h1>
			</header>
			<div class="beg-login-main">
				<form id="loginForm" action="" class="layui-form" method="post"><input name="__RequestVerificationToken" type="hidden" value="fkfh8D89BFqTdrE2iiSdG_L781RSRtdWOH411poVUWhxzA5MzI8es07g6KPYQh9Log-xf84pIR2RIAEkOokZL3Ee3UKmX0Jc8bW8jOdhqo81" />
					<div class="layui-form-item">
						<label class="beg-login-icon">
                        <i class="layui-icon">&#xe612;</i>
                    </label>
						<input type="text" lay-verify="username" name="username" lay-verify="username" autocomplete="off" placeholder="这里输入登录名" class="layui-input" value="">
					</div>
					<div class="layui-form-item">
						<label class="beg-login-icon">
                        <i class="layui-icon">&#xe642;</i>
                    </label>
						<input type="password" lay-verify="password" name="password" lay-verify="password" autocomplete="off" placeholder="这里输入密码" class="layui-input" value="">
					</div>
					<div class="layui-form-item">
						<div class="beg-pull-left beg-login-remember">
							<label>记住帐号？</label>
							<input type="checkbox" name="rememberMe" value="true" lay-skin="switch" checked title="记住帐号">
                            <div class="layui-unselect layui-form-switch layui-form-onswitch" lay-skin="_switch"><em></em><i></i></div>
						</div>
						<div class="beg-pull-right">
                            <button  class="layui-btn layui-btn-primary" lay-submit lay-filter="login">
                                <i class="layui-icon">&#xe650;</i> 登录
                        </button>
						</div>
						<div class="beg-clear"></div>
					</div>
				</form>
			</div>
			<footer>
				<p>XXX © www.XXX.org</p>
			</footer>
		</div>
		<script type="text/javascript" src="/plugins/layui/layui.js"></script>
		<script>
			layui.use(['layer', 'form'], function() {
				var layer = layui.layer,
					$ = layui.jquery,
					form = layui.form();

                //表单验证
                form.verify({
                    username: function(value){
                        if(value == '' || value.length < 1){
                            return '用户名不能为空!';
                        }
                    },
					password: [/(.+){3,12}$/, '密码必须3到12位'],
                    phone: [/^1[3|4|5|7|8]\d{9}$/, '手机必须11位，只能是数字！'],
                    email: [/^[a-z0-9._%-]+@([a-z0-9-]+\.)+[a-z]{2,4}$|^1[3|4|5|7|8]\d{9}$/, '邮箱格式不对']
                });

				form.on('submit(login)',function(data){
                    var params = data.field;
                    $.ajax({
                        type: "POST",
                        url: "/login",
                        data: params,
                        success: function(msg){
                            if(msg.code == 200) {
                                location.href='/index';
                            }else {
                                layer.msg(msg.message,{icon:1})
                            }
                        }
                    });
					return false;
				});
			});
		</script>
	</body>

</html>