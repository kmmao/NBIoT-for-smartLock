<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("ctx", path);
%>

<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">

    <title>智能锁管理平台</title>

    <!-- Bootstrap core CSS
    	 http://cdn.bootcss.com/twitter-bootstrap/3.3.4/css/bootstrap.min.css
    -->
    <link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
    
    <!-- Placed at the end of the document so the pages load faster 
    	http://cdn.bootcss.com/jquery/1.11.0/jquery.min.js
    	http://cdn.bootcss.com/bootstrap/3.0.0/js/bootstrap.min.js
    -->
    <script src="${ctx}/js/jquery.min.js"></script>
    <script src="${ctx}/js/bootstrap.min.js"></script>
    <script src="${ctx}/js/jquery.form.js"></script>
    <script src="${ctx}/js/jquery.enplaceholder.js"></script>
    <script type="text/javascript">
    	var g_ctx = '${ctx}';
    	
    	$(document).ready(function(){
    		$('input').placeholder();
			
		});
    </script>
    <style type="text/css">
    @media (min-width: 1200px)
		.container {
		    width: 970px;
		
	}
	.btn-info{color:#fff;background-color:#4d6ea6;border-color:#4d6ea6}
	.btn-info:hover {
		background:url('${ctx}/images/login_submit_hover.png') repeat-x;
		}
	.btn-info.active,.btn-info.focus,.btn-info:active,.btn-info:focus,.btn-info:hover,.open>.dropdown-toggle.btn-info{color:#fff;background-color:#46669f;border-color:#46669f}
    </style>
    
<!--     <style>
	body{
  		width: 100%;
  		height:auto;
  		background:url("${ctx}/images/bg.png") no-repeat;
  		background-size: 100%;
	}
	</style> -->
  </head>
  
<body>
<img id="bg" <%-- src="${ctx}/images/bg.png" --%>  style="right: 0; bottom: 0;position: absolute; top: 0; left: 0; z-index: -1" /> 

<!-- 	<div class="container" style="width: 970px;height: 239px; margin-top: 20px;"> -->
	 <div id= "version" style="
	position:absolute;
    width: 100px;
">version:1.1.7 build-20180709
</div>
<!-- 	</div>	 -->
	<div id="login" style="overflow: visible;position: absolute;right: 153.6px;top: 350px;left: 110px;">
	<div class="container" style="width: 420px;height: 430px;background-repeat: no-repeat;padding-right:0px;">
  		<div class="container" style=" width: 420px;height: 51px;background-repeat: no-repeat;" >
  		<div class="container" style="width: 400px;">
		<form:form commandName="user" cssClass="form-horizontal " role="form" method="post" action="${ctx}/login/authen.do" onsubmit="return validator(this)" >
		  
		  <div class="container" style="width: 353px;height: 51px; padding-right:0px;">
		  	<span class="input-group-addon"  style="line-height:51px;height: 24px; background-image: url('${ctx}/images/login_username.png');background-repeat: no-repeat;vertical-align: middle;display: inline; border: none;background-color: transparent;"></span>
  			<span style="font-size: 23px;margin-left: 10px;color: #0da2f6" >|</span>
  			<form:input path="phone" placeholder="帐号(电话号码)"  tabindex="1" cssStyle="background-color: transparent;border: none;height: 51px;padding:6px 12px;top: 0px;position: relative;width: 82%;"/>	
		  </div>
		  <div class="container" style="width: 353px;height: 51px; margin-top: 20px; padding-right:0px;">
		  	<span class="input-group-addon"  style="line-height:51px;height: 24px; background-image: url('${ctx}/images/login_pwd.png');background-repeat: no-repeat;vertical-align: middle;display: inline; border: none;background-color: transparent;"></span>
  			<span style="font-size: 23px;margin-left: 10px;color: #0da2f6" >|</span>
  			<form:password path="pwd" placeholder="输入密码"  tabindex="2" cssStyle="background-color: transparent;border: none;height: 51px;padding:6px 12px;top: 0px;position: relative;position: relative;width: 82%;"/>	
		  </div>
		  <div class="container"style="width: 353px;margin-top: 20px;padding-left: 0px;padding-right:0px;">
		      <button id="savebtn" name="savebtn" type="submit" class="btn btn-info btn-lg " style="width: 100%;color: white;font-family: 微软雅黑;font-size: 20px;background-repeat: no-repeat;">登录</button>
		  </div>
		</form:form>
	  </div>
	</div>

<%-- <div class="container" style=" width: 970px;height: 768px;" >

	 
<div>
	<div class="container" style="width: 970px;height: 239px; margin-top: 100px;">
	</div>	
	<div class="container" style="width: 970px;padding-left: 0px;padding-right: 0px;">
		<form:form commandName="user" cssClass="form-horizontal " role="form" method="post" action="${ctx}/login/authen.do" onsubmit="return validator(this)" >
		  
		  <div class="container" style="width: 353px;height: 51px; background-image: url('${ctx}/images/login_input.png');background-repeat: no-repeat;    padding-right:0px;">
  			<span class="input-group-addon"  style="line-height:51px;height: 24px; background-image: url('${ctx}/images/login_username.png');background-repeat: no-repeat;vertical-align: middle;display: inline; border: none;background-color: transparent;"></span>
  			<span style="font-size: 23px;margin-left: 10px;color: #0da2f6" >|</span>
  			<form:input path="phone" placeholder="帐号(电话号码)"  tabindex="1" cssStyle="background-color: transparent;border: none;height: 51px;padding:6px 12px;top: 0px;position: relative;width: 292px;"/>	
		  </div>
		  <div class="container" style="width: 353px;height: 51px; background-image: url('${ctx}/images/login_input.png');background-repeat: no-repeat;margin-top: 20px; padding-right:0px;">
  			<span class="input-group-addon"  style="line-height:51px;height: 24px; background-image: url('${ctx}/images/login_pwd.png');background-repeat: no-repeat;vertical-align: middle;display: inline; border: none;background-color: transparent;"></span>
  			<span style="font-size: 23px;margin-left: 10px;color: #0da2f6" >|</span>
  			<form:password path="pwd" placeholder="输入密码"  tabindex="2" cssStyle="background-color: transparent;border: none;height: 51px;padding:6px 12px;top: 0px;position: relative;position: relative;width: 292px;"/>	
		  </div>
		  <div class="container"style="width: 353px;margin-top: 20px;padding-left: 0px;padding-right:0px;">
		      <button id="savebtn" name="savebtn" type="submit" class="btn btn-info btn-lg" style="width: 100%;color: white;font-family: 微软雅黑;font-size: 20px;">登录</button>
		  </div>
		</form:form>
	</div>
</div>
</div> --%>
<%-- 
 <div style="
    margin-left: 1300px;
    margin-top: 380px;
    border-top-width: 10px;
    width: 100px;
">version:1.0.1 build-20171205
</div>	 
  --%>		 
<%-- <div style="height:156px; background-image: url('${ctx}/images/4.png');background-repeat: no-repeat;"> --%>
<!-- </div>	 -->
 	
	</div> <!-- /container -->
	</div>
  </body>
  <script type="text/javascript">
var login = document.getElementById("login");
var bgImg = document.getElementById("bg");
var version = document.getElementById("version");
login.style.position = "absolute";

window.onload = view; 	// 页面初始化时固定其位置
window.onscroll = view;// 当文档位置发生变化时重新固定其位置
window.onresize = view;//页面重置时固定其位置
function view(){      // 元素位置固定函数
//登录
    login.style.right = parseInt(document.documentElement.clientWidth)*0.08 + "px";
    login.style.top = parseInt(document.documentElement.clientHeight)*0.3 + "px";
//背景
    bgImg.style.width = parseInt(document.documentElement.clientWidth) + "px";
    bgImg.style.height = parseInt(document.documentElement.clientHeight) + "px";
//版本号
    version.style.right= "0px";

    version.style.top = parseInt(document.documentElement.clientHeight)*0.015 + "px";
}
</script>
  
  <script type="text/javascript">
  
		function validator() {
			if ($("#phone").val().length == 0 || $("#phone").val().length != 11) {
				alert("请输入合法的用户名");
				$("#phone").focus();

				return false;
			}

			if ($("#pwd").val().length == 0) {
				alert("请输入密码");
				$("#pwd").focus();

				return false;
			}
// 			else if($("#pwd").val() == 111111){
// 				alert("您的密码是原始密码，登录后请修改密码");
// 			}

			/*
		 	if ($("#securitycode").val().length == 0) {
				alert("请输入验证码！");
				$("#securitycode").focus();

				return false;
			}
			*/

			return true;
		}

		$(document).ready(function() {
			var loginResults = [
					true,
					"您输入的验证码不对，请重新输入。",
					"用户名或密码错误",
					"验证码已失效，请重新输入。",
					"此用户已经被停用，不能够登录系统",
					"您的密码是原始密码，登录后请修改密码"];
			var loginResult = "${empty loginResult ? param.loginResult : loginResult}";
			var countDownIntervalId;

			$("#phone").focus();

			function getSecurityCode() {
				$("#securitycode + img").get(0).src = '${ctx}'
						+ '/graphics.gif?' + Math.random();

				if ($("#phone").val().length == 0)
					$("#phone").focus();
				else if ($("#pwd").val().length == 0)
					$("#pwd").focus();
				else
					$("#securitycode").focus();
			}

			$("#changesecuritycode").click(function() {
				getSecurityCode();
			});

			$("#securitycode ~ img").click(function() {
				getSecurityCode();
			});

			function countDown() {
				if (parseInt($("#countDown").html()) > 0)
					$("#countDown")
							.html(
									parseInt($("#countDown")
											.html()) - 1);
				else {
					window.clearInterval(countDownIntervalId);
					location.href = "${ctx}/";
				}
			}

			if (loginResult != ""
					&& parseInt(loginResult) != NaN) {
				if (loginResults[parseInt(loginResult)] == true) {
					location.href = "${ctx}/";
					
					/* $("#divLoginInput").css("visibility", "hidden");
					$("#divWelcome").css("visibility", "visible");
					$("#divLoginFace").css("backgroundImage", "url('${ctx}/images/common/welcome_bg.jpg')");
					$("#divWelcome > a").attr("href", "${ctx}/");
					$("#divWelcome > a").focus();
					countDownIntervalId = window.setInterval(countDown,1000); */
				} else if (typeof loginResults[parseInt(loginResult)] == "string") {
					alert(loginResults[parseInt(loginResult)]);
				}
			}
		});
	
	</script>

</html>

