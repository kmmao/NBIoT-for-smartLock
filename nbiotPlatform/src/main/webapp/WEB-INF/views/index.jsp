<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@ include file="/WEB-INF/views/head_n.jsp" %>


  <style type="text/css">

	.home-btn-info{color:#fff;background-color:transparent; border-color:transparent;background-image: url("${ctx}/images/home_menu_btn.png");width: 100%;height:57px;color: white;
	font-family: 微软雅黑;font-size: 20px;line-height: 57px;cursor: pointer;text-align: center;}
	.home-btn-info.active,.home-btn-info.focus,.home-btn-info:active,.home-btn-info:focus,.home-btn-info:hover,.open>.dropdown-toggle.home-btn-info{color:#fff;background-color:transparent; border-color:transparent;background-image: url("${ctx}/images/home_menu_btn_active.png");}
  </style>
     

<div>

		<div class="container">
			
			<div class="container"  style="margin-top: 50px;">
			
			<div id = role1 style="display:none;">
				<ol class="breadcrumb">
					<li >超级管理员工作流程：</li>
					<li>
						<a href = "${ctx}/user/show.do">新增销售助理账号</a>	
					</li>
				</ol>
			</div>
			<div id = role2 style="display:none;">
				<ol class="breadcrumb">
					<li >销售助理工作流程：</li>
					<li>
						<a href = "${ctx}/company/list.do">新增公司</a>	
					</li>
					<li>
						<a href = "${ctx}/project/list.do">新增项目</a>	
					</li>
					<li>
						<a href = "${ctx}/user/show.do">新增商务账号</a>	
					</li>
					<li>
						<a href = "${ctx}/order/show.do">新增订单</a>	
					</li>
				</ol>
			</div>
			<div id = role3 style="display:none;">
				<ol class="breadcrumb">
					<li >项目管理人员工作流程：</li>
<!-- 					<li> -->
<%-- 						<a href = "${ctx}/company/list.do">新增公司</a>	 --%>
<!-- 					</li> -->
					<li>
						<a href = "${ctx}/project/list.do">项目审核</a>	
					</li>
<!-- 					<li> -->
<%-- 						<a href = "${ctx}/order/show.do">新增订单</a>	 --%>
<!-- 					</li> -->
				</ol>
			</div>
			<div id = role4 style="display:none;">
				<ol class="breadcrumb">
					<li >项目技术负责人员工作流程：</li>
					<li>
						<a href = "${ctx}/operatingsystem/list.do">新增操作系统</a>	
					</li>
					<li>
						<a href = "${ctx}/hardwarestation/list.do">新增硬件平台</a>	
					</li>
<!-- 					<li> -->
<%-- 						<a href = "${ctx}/order/show.do">新增订单</a>	 --%>
<!-- 					</li> -->
				</ol>
			</div>
			<div id = role5 style="display:none;">
				<ol class="breadcrumb">
					<li >财务人员工作流程：</li>
<!-- 					<li> -->
<%-- 						<a href = "${ctx}/company/list.do">新增公司</a>	 --%>
<!-- 					</li> -->
<!-- 					<li> -->
<%-- 						<a href = "${ctx}/project/list.do">新增项目</a>	 --%>
<!-- 					</li> -->
					<li>
						<a href = "${ctx}/order/show.do">订单审核</a>	
					</li>
				</ol>
			</div>
			<div id = role6 style="display:none;">
				<ol class="breadcrumb">
					<li >商务人员工作流程：</li>
<!-- 					<li> -->
<%-- 						<a href = "${ctx}/company/list.do">新增公司</a>	 --%>
<!-- 					</li> -->
					<li>
						<a href = "${ctx}/project/list.do">项目审核</a>	
					</li>
					<li>
						<a href = "${ctx}/order/show.do">订单审核</a>	
					</li>
					<li>
						<a href = "${ctx}/user/show.do">创建项目负责人员</a>	
					</li>
				</ol>
			</div>
			<div id = role7 style="display:none;">
				<ol class="breadcrumb">
					<li >项目负责人员工作流程：</li>
<!-- 					<li> -->
<%-- 						<a href = "${ctx}/company/list.do">新增公司</a>	 --%>
<!-- 					</li> -->
					
					<li>
						<a href = "${ctx}/clientinfo/list.do">导入授权清单</a>	
					</li>
					<li>
						<a href = "${ctx}/terminal/list.do">终端管理</a>	
					</li>
				</ol>
			</div>
			<div id = input1 style="display:none;">
			<p style="font-weight:bold;coler:black;">超级管理员操作流程说明:</p>
				<textarea id="mark" name="mark"   style = "width:1140px; height:50px; outline:none;border:0;resize:none ;" readonly >
	(1): 1.1 选择“系统”-->1.2 点击“用户管理”查看当前用户权限下的所有用户信息-->1.3 点击“新增用户”根据提示在指定位置填写或选择正确的信息-->1.4 点击“保存”完成信息填写</textarea>
			</div>
			<div id = input2 style="display:none;">
			<p style="font-weight:bold;coler:black;">销售助理操作流程说明:</p>
				<textarea id="mark" name="mark"   style = "width:1140px; height:400px;outline:none; border:0;resize:none ;" readonly >
	(1): 1.1 选择”订单”-->1.2 点击”公司管理”-->1.3点击”新增”根据提示在指定位置填写或选择正确的信息-->1.4 点击”保存”完成信息填写
	(2): 1.1 选择”订单”-->1.2 点击”项目管理”-->1.3 点击”新增”根据提示在指定位置填写或选择正确的信息-->1.4 点击”确定”完成信息填写-->1.5 选择”系统”-->1.6 点击”用户管理”-->1.7 选择需要分配项目权限的我方项目管理人员和客户商务人员”ID”根据提示授权（若没有该用户，1.7.1 选择”新增用户”-->1.7.2 根据提示在制定位置填写或选择正确的信息）-->1.8 点击”保存”完成信息填写-->1.9 点击”待处理任务”-->1.10 点击”我已处理完毕”-->1.11 等待”我方项目管理人员”和”客户商务人员”审核
	(3): 1.1 选择”系统”-->1.2 点击”用户管理”-->1.3点击”新增用户”根据提示在指定位置填写或选择正确的信息-->1.4 点击”保存”完成信息填写
	(4): 1.新建订单	
		1.1 选择”订单”-->1.2 点击”订单管理”-->1.3 选择需创建订单的项目-->1.4 点击”新建订单”根据提示在指定位置填写或选择正确的信息-->1.5 点击”确定”完成信息填写-->1.6 点击”待处理任务”-->1.7 点击”我已处理完毕”-->1.8 等待 “客户商务人员"处理完毕后-->1.9 点击”待处理任务”-->1.10点击”审核”-->1.11 等待”客户商务人员”和”我方财务人员”审核
	      2.续订订单（启用/结束状态下已审核的订单）
		1.1 选择需要续订的订单-->1.2 点击”订单续订”-->1.3 填写”续订数量”-->1.4 点击”保存”-->1.5 点击”待处理任务”-->1.6 点击”我已处理完毕”-->1.7 等待 “客户商务人员”处理完毕后-->1.8 点击”待处理任务”-->1.9 点击”审核”-->1.10 等待”客户商务人员”和”我方财务人员”审核
	      3.退订订单（启用状态下已审核的订单）
		1.1 选择需要续订的订单-->1.2 点击”订单退订”-->1.3 填写”退订数量”-->1.4 点击”保存”-->1.5 点击”待处理任务”-->1.6 点击”我已处理完毕”-->1.7 等待 “客户商务人员”处理完毕-->1.8 点击”待处理任务”-->1.9 点击”审核”-->1.10 等待”客户商务人员”和”我方财务人员”审核
	      4.强制停止（启用状态下已审核的订单）/开启（强制停止状态下的订单）
		1.1 选择需要强制停止的订单-->1.2 点击”强制停止”-->1.3 点击”确认”-->1.4 点击”待处理任务”-->1.5 点击”我已处理完毕”-->1.6 等待 “我方财务人员”审核完毕后强制停止完成-->1.7 选择需要开启的订单-->1.8 点击”订单开启”-->1.9 点击”确认”-->1.10 点击”待处理任务”-->1.11 点击”我已处理完毕”-->1.12 等待 “我方财务人员”审核</textarea>

			</div>
			<div id = input3 style="display:none;">
			<p style="font-weight:bold;coler:black;">项目管理人员操作流程说明:</p>
				<textarea id="mark" name="mark"   style = "width:1140px; height:100px; outline:none;border:0;resize:none ;" readonly >
	(1): 1.1 选择”订单”-->1.2 点击”项目管理”-->1.3 点击”待处理任务”-->1.4点击”审核”-->1.5 等待 “客户商务人员”审核完成</textarea>
			</div>
			<div id = input4 style="display:none;">
				<textarea id="mark" name="mark"   style = "width:1140px; height:100px;outline:none; border:0;resize:none ;" readonly >项目技术负责人员操作流程说明:
	1、
	2、</textarea>
			</div>
		
			<div id = input5 style="display:none;">
			<p style="font-weight:bold;coler:black;">财务人员操作流程说明:</p>
				<textarea id="mark" name="mark"   style = "width:1140px; height:100px;outline:none; border:0;resize:none ;" readonly >
	(1): 1.1 选择”订单”-->1.2 点击”项目管理”-->1.3 点击”待处理任务”-->1.4点击”审核”</textarea>
			</div>
			<div id = input6 style="display:none;">
			<p style="font-weight:bold;coler:black;">商务人员操作流程说明:</p>
				<textarea id="mark" name="mark"   style = "width:1140px; height:110px;outline:none; border:0;resize:none ;" readonly >
	(1): 1.1 选择”订单”-->1.2 点击”项目管理”-->1.3 点击”待处理任务”-->1.4点击”审核”
	(2): 1.1 选择”订单”-->1.2 点击”项目管理”-->1.3 点击”待处理任务”-->1.4点击”我已处理完毕”-->1.5 等待 “销售助理”审核-->1.6 点击”待处理任务”-->1.7点击”审核”-->等待”我方财务人员”审核
	(3): 1.1 选择”系统”-->1.2 点击”用户管理”-->1.3点击”新增用户”根据提示在指定位置填写或选择正确的信息-->1.4 点击”保存”完成信息填写
	</textarea>
			</div>
			<div id = input7 style="display:none;">
			<p style="font-weight:bold;coler:black;">项目负责人员操作流程说明:</p>
				<textarea id="mark" name="mark"   style = "width:1140px; height:120px;outline:none; border:0;resize:none ;" readonly >
	(1): 1.1 选择”订单”-->1.2 点击”用户信息管理”-->1.3选择需要导入/录入让客户代码的公司项目订单-->批量导入业务代码（2.1 按照要求创建批量导入文件-->2.2 点击”Excel导入”-->2.3 点击”浏览”选择需要导入的文件-->2.4 点击”上传”-->导入完成后，点击”确定”）或单个录入业务代码（3.1 选择需要录入客户代码的订单-->3.2 点击”编辑”根据提示在指定的位置填写或选择正确的信息-->3.3 点击”确定”，完成本次录入）
	(2): 1.1 选择”订单”-->1.2 点击”终端管理”-->1.3选择需要申请离线授权的项目-->1.4 点击”离线授权申请”根据提示填写正确的业务代码和机器码-->1.5 点击”确定”-->1.6 选择需要下载授权文件的记录-->1.7 点击”授权文件下载”完成</textarea>
			</div>
<!-- 			<h5 style="color:red;">123</h5> -->
<!-- 			授权管理平台平台首页  --> 
			</div>
			
		</div>


		<script type="text/javascript">  
		
// 		$(document).ready(function() {
// 			$("#oldPwd").val("");
// 			$("#newPwd").val("");
// 			$("#newPwdConfirm").val("");
// 			$("#changepwdmodal").modal('show');
// 		})

	$(document).ready(function(){
	  var userloginid =	"${userProfile.currentUserId}";
		$.ajax({ 
			type        : "POST"
			,url         : "${ctx}/home/procedureshow.do"
			,data        : {userloginid : userloginid}
			,contentType : "application/x-www-form-urlencoded;charset=utf-8;"
			,dataType    : "json"
			,cache		  : false	
			,success: function(info) {
				
				if (info.code == 1) {
					var rolename = {};
					rolename = info.msg.split(",")
					for(i = 0 ; i < rolename.length ; i++){
						if(rolename[i] == "1"){
							document.getElementById('role1').style.display='block';
							document.getElementById('input1').style.display='block';
						}
						if(rolename[i] == "2"){
							document.getElementById('role2').style.display='block';
							document.getElementById('input2').style.display='block';
						}
						if(rolename[i] == "3"){
							document.getElementById('role3').style.display='block';
							document.getElementById('input3').style.display='block';
						}
						if(rolename[i] == "4"){
							document.getElementById('role4').style.display='block';
							document.getElementById('input4').style.display='block';
						}
						if(rolename[i] == "5"){
							document.getElementById('role5').style.display='block';
							document.getElementById('input5').style.display='block';
						}
						if(rolename[i] == "6"){
							document.getElementById('role6').style.display='block';
							document.getElementById('input6').style.display='block';
						}
						if(rolename[i] == "7"){
							document.getElementById('role7').style.display='block';
							document.getElementById('input7').style.display='block';
						}
					}
				}
				else if (info.code == 0) {
					alert("流程读取失败，请刷新页面");
				}
// 				else if (info.code == -1) {
// 					alert("密码重置异常");
// 				}					
			}
			,error : function(XMLHttpRequest, textStatus, errorThrown) {    
				alert(XMLHttpRequest.status + textStatus);    
			} 	
		}
		);
	});
		
		function goUrl(url){
			document.location.href = '${ctx}'+url;
		}
		</script>
		
	</body> 
</html>