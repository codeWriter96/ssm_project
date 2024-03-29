<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String basepath = request.getScheme() + "://"
			+ request.getServerName() +
			":" + request.getServerPort() + request.getContextPath() + "/";
%>

<html>
<title>
	登录页面
</title>
<head>
	<base href="<%=basepath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function () {
			
			if(window.top !=window){
				window.top.location = window.location
			}
			$("#loginAct").focus()
			$("#loginAct").val("")
			$("#loginPwd").val("")
			$("#loginButton").click(function () {
				login()
			})
			$(window).keydown(function (event) {
				if(event.keyCode==13){
					login()
				}
			})
			$("#loginAct").change(function () {
				$("#loginPwd").val("")
			})
		})

		function trim(obj){
			$(obj).val($.trim($(obj).val()))
		}
		function login() {
			if($("#loginAct").val()==""||$("#loginPwd").val()==""){
				$("#msg").html("用户名或密码不能为空")
				return false
			}
			var info ={"loginAct":$("#loginAct").val(),"loginPwd":$("#loginPwd").val()}
			$.ajax({
				url:"setting/user/logIn.do",
				data:info,
				type:"post",
				dataType:"json",
				success:function (data) {
					if(data.success){
						document.location.href = "workbench/index.jsp";
					}else{
						$("#msg").html(data.msg)
					}
				}
			})
		}




	</script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2021&nbsp;xx企业</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="workbench/index.jsp" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input id="loginAct" class="form-control" type="text" placeholder="用户名" onblur=trim("#loginAct")>
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input id="loginPwd" class="form-control" type="password" placeholder="密码" onblur=trim("#loginPwd")>
					</div>
					<div class="checkbox" id="checkLogin" style="position: relative;top: 30px; left: 10px;">
						
							<span id="msg" style="color: red"></span>
					</div>
					<button type="button" id="loginButton" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>