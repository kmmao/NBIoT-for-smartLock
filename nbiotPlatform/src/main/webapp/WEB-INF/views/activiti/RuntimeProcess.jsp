<%@ page import="com.routon.plcloud.common.decorator.PageCheckboxDecorator"%>
<%@ page import="com.routon.plcloud.common.decorator.PageLinkDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<style> 
.aaa1{ float:left}
.header{ display:none }
.badge {
	display: inline-block;
	min-width: 10px;
	padding: 3px 7px;
	font-size: 12px;
	font-weight: bold;
	line-height: 1;
	color: #fff;
	text-align: center;
	white-space: nowrap;
	vertical-align: baseline;
	background-color: #FF0000;
	border-radius: 10px;
}
.badge:empty {
	display: none;
}
</style>
<div class="container" style=" width: 1560px;">
  <input id="currentStatus" name="currentStatus" type="text" style="display:none" value="${tabStatus}"/>
  <input id="currentTabStatus" name="currentTabStatus" type="text" style="display:none" value="${currentTab}"/> <!-- style="display:none" -->
  <input id="taskNum" name="taskNum" type="text" style="display:none" value="${taskQuerySize}"/>
  <div>
	<p>
		<font size="3" color="black">销售助理新建订单→系统管理员审核→客户商务人员申请→销售助理审核→客户商务人员审核→财务审核→客户商务人员分配账号→客户项目负责人员上传授权清单→项目技术人员确定软件产品及版本号→项目管理人员审核→终端授权</font>
	</p>
	<ul id="tab" class="nav nav-pills">
		<li id="tab3" value="3" role="presentation" class="active"><a href="#"><span class="glyphicon glyphicon-list" aria-hidden="true"></span> 全部流程</a></li>
  		<li id="tab4" value="4" role="presentation" ><a href="#"><span class="glyphicon glyphicon-list" aria-hidden="true"></span>待处理任务<span id="taskNumSpan" class="badge"></span></a></li>
  		<!-- <li id="tab1" value="1" role="presentation" ><a href="#"><span class="glyphicon glyphicon-list" aria-hidden="true"></span> 待处理任务</a></li> -->
  		<c:if test="${(!empty userPrivilege['90000305'])}">
  			<li id="tab2" value="2" role="presentation"><a href="#"><span class="glyphicon glyphicon-list" aria-hidden="true"></span> 流程跟踪</a></li>
  		</c:if>
	</ul>
	<div id="content1" style="display:none;">
	 <div class="panel panel-default">
		<display:table name="${claimedTaskList}" id="curPage" class="table table-striped" sort="external" partialList="false" excludedParams="*"
			requestURI="show.do"
			decorator="com.routon.plcloud.common.decorator.PageLinkDecorator"
			export="false">
			<display:column title="公司" property="companyName" sortable="true" style="width:5%;" ></display:column>
			<display:column title="项目" property="projectName" sortable="true" style="width:5%;" ></display:column>
			<display:column title="订单号" property="orderNum" sortable="true" style="width:5%;" ></display:column>
			<display:column title="申请人" property="applyUser" sortable="true" style="width:5%;" ></display:column>
			<display:column title="任务创建时间" property="createTime" sortable="true"  style="width:8%;" ></display:column>
			<display:column title="授权数量" headerClass="header" property="authorNums" sortable="true"  style="display:none;" ></display:column>
			<display:column title="备注信息" property="remarks" sortable="true"  style="width:10%;" ></display:column>
			<display:column title="结束时间" sortable="true"  style="width:5%;" ></display:column>
			<display:column title="任务ID" headerClass="header" sortable="true" property="id" style="display:none;" ></display:column>
			<display:column title="实例ID" headerClass="header" property="processInstanceId"  sortable="true"  style="display:none;" maxLength="50"></display:column>
			<display:column title="流程ID"  property="processDefinitionId" sortable="true"  style="width:5%;"></display:column>
			<display:column title="流程名称"  property="processName" sortable="true"  style="width:5%;"></display:column>
			<display:column title="待处理事项"  property="name"  sortable="true"  style="width:9%;"></display:column>
			<display:column title="操作" property="button" sortable="true"  style="width:12%;" >${curPage_rowNum}, ${curPage.processDefinitionId}, ${curPage.processInstanceId}</display:column>
		</display:table>
	 </div>
	 <%@ include file="/WEB-INF/views/common/claimedTask.jsp" %>
	 <select id="select_page_2" name="select_page_2" onchange="javascript:window.location.href=this.options[this.selectedIndex].value;" style="display:none">
	 	<option value="show.do?claimed_page=1&tabStatus=tab1" selected="selected">1</option>
	 	<c:forEach var="i" begin="2" end="${claimed_maxpage}">
			<option value="show.do?claimed_page=${i}&tabStatus=tab1">${i}</option>
		</c:forEach>
	 </select>
	</div>
	</div>
	<div id="content2" style="display:none;">
		<div class="panel panel-default">
		  <display:table name="${AllList}" id="curPage" class="table table-striped" sort="external" partialList="false" excludedParams="*"
			requestURI="show.do"
			decorator="com.routon.plcloud.common.decorator.PageLinkDecorator"
			export="false">
			<display:column title="公司" property="companyName" sortable="true" style="width:5%;" ></display:column>
			<display:column title="项目" property="projectName" sortable="true" style="width:5%;" ></display:column>
			<display:column title="订单号" property="orderNum" sortable="true" style="width:5%;" ></display:column>
			<display:column title="申请人" property="applyUser" sortable="true" style="width:5%;" ></display:column>
			<display:column title="任务创建时间" property="createTime" sortable="true"  style="width:8%;" ></display:column>
			<display:column title="授权数量" headerClass="header" property="authorNums" sortable="true"  style="display:none;" ></display:column>
			<display:column title="备注信息" property="remarks" sortable="true"  style="width:10%;" ></display:column>
			<display:column title="结束时间" sortable="true"  style="width:5%;" ></display:column>
			<display:column title="任务ID" headerClass="header" sortable="true" property="id" style="display:none;" ></display:column>
			<display:column title="实例ID" headerClass="header" property="processInstanceId"  sortable="true"  style="display:none;" maxLength="50"></display:column>
			<display:column title="流程ID"  property="processDefinitionId" sortable="true"  style="width:5%;"></display:column>
			<display:column title="流程名称"  property="processName" sortable="true"  style="width:5%;"></display:column>
			<display:column title="待处理事项"  property="name"  sortable="true"  style="width:9%;"></display:column>
			<display:column title="操作" property="button2" sortable="true"  style="width:12%;" >${curPage_rowNum}, ${curPage.processDefinitionId}, ${curPage.processInstanceId}</display:column>
		  </display:table>
		</div>
		<%@ include file="/WEB-INF/views/common/allTask.jsp" %>
	    <select id="select_page_3" name="select_page_3" onchange="javascript:window.location.href=this.options[this.selectedIndex].value;" style="display:none">
	 			<option value="show.do?allTask_page=1&tabStatus=tab2" selected="selected">1</option>
	 			<c:forEach var="i" begin="2" end="${allTask_maxpage}">
					<option value="show.do?allTask_page=${i}&tabStatus=tab2">${i}</option>
				</c:forEach>
	 		</select>
		</div>
	  </div>
	<div id="content3" style="display:block;">
	  <div class="panel panel-default">
		<display:table name="${pageList_process}" id="curPage" class="table table-striped" sort="external" partialList="false"
			requestURI="show.do"
			decorator="com.routon.plcloud.common.decorator.ActivitiLinkDecorator"
			export="false">
			<display:column title="流程ID" sortable="true" property="id" style="width:5%;" ></display:column>
			<display:column title="DeploymentId" headerClass="header" sortable="true" property="deploymentId" style="display:none;" ></display:column>
			<display:column title="名称" sortable="true"  property="name" style="width:5%;" ></display:column>
			<display:column title="KEY" headerClass="header" sortable="true"  property="key" style="display:none;" ></display:column>
			<display:column title="版本号" sortable="true"  property="version" style="width:5%;" ></display:column>
			<display:column title="XML" sortable="true" property="activitiXML" style="width:5%;" ></display:column>
			<display:column title="流程展示" sortable="true"  property="activitiProcessImage" style="width:5%;" maxLength="50"></display:column>
			<display:column title="操作" sortable="true"  style="width:5%;"><button id="startup" class="btn btn-default btn-sm" disabled="disabled" onclick="startProcess(this)">启动</button></display:column>
		</display:table>
	  </div>
	</div>
	<div id="content4" style="display:none;">
	 <div class="panel panel-default">
		<display:table name="${List}" id="curPage" class="table table-striped" sort="external" partialList="false" excludedParams="*"
			requestURI="show.do"
			decorator="com.routon.plcloud.common.decorator.PageLinkDecorator"
			export="false">
			<display:column title="公司" property="companyName" sortable="true" style="width:5%;" ></display:column>
			<display:column title="项目" property="projectName" sortable="true" style="width:5%;" ></display:column>
			<display:column title="订单号" property="orderNum" sortable="true" style="width:5%;" ></display:column>
			<display:column title="申请人" property="applyUser" sortable="true" style="width:5%;" ></display:column>
			<display:column title="任务创建时间" property="createTime" sortable="true"  style="width:10%;" ></display:column>
			<display:column title="授权数量" headerClass="header" property="authorNums" sortable="true"  style="display:none;" ></display:column>
			<display:column title="备注信息" property="remarks" sortable="true"  style="width:10%;" ></display:column>
			<display:column title="结束时间" sortable="true"  style="width:5%;" ></display:column>
			<display:column title="任务ID" headerClass="header" sortable="true" property="id" style="display:none;" ></display:column>
			<display:column title="实例ID" headerClass="header" property="processInstanceId"  sortable="true"  style="display:none;" maxLength="50"></display:column>
			<display:column title="流程ID"  property="processDefinitionId" sortable="true"  style="width:5%;"></display:column>
			<display:column title="流程名称"  property="processName" sortable="true"  style="width:5%;"></display:column>
			<display:column title="待处理事项"  property="name"  sortable="true"  style="width:9%;"></display:column>
			<display:column title="操作" property="buttonRecv" sortable="true"  style="width:12%;" >${curPage_rowNum}, ${curPage.processDefinitionId}, ${curPage.processInstanceId}</display:column>
		</display:table>
	 </div>
	 <%@ include file="/WEB-INF/views/common/pagination_notReceviedTask.jsp" %>
	 <select id="select_page_1" name="select_page_1" onchange="javascript:window.location.href=this.options[this.selectedIndex].value;" style="display:none">
	 	<option value="show.do?task_no_page=1&tabStatus=tab4" selected="selected">1</option>
	 	<c:forEach var="i" begin="2" end="${task_no_received_maxpage}">
			<option value="show.do?task_no_page=${i}&tabStatus=tab4">${i}</option>
		</c:forEach>
	 </select>
	</div>
  </div>
 </div>
 
 <div class="modal fade" id="activitiModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" aria-hidden="true">
 	<div class="modal-dialog" style="width: 500px;">
 		<div class="modal-content">
 			<div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">启动信息</h4>
            </div>
            <div class="modal-body">
            		<div class="form-group">
            		<form role="form" id="activitiForm" name="activitiForm" method='post' enctype="multipart/form-data">
            			<input id="processDefinitionId" name="processDefinitionId" type="hidden">
    					<label for="name">项目名称</label>
    					<input type="text" class="form-control" id="projectName" name="projectName">
    					<label for="name">拟授权数量</label>
    					<input type="text" class="form-control" id="authorNums" name="authorNums">
    					<label for="name">备注</label><br>
    					<textarea id="remarks" name="remarks" style="width: 468px;height:60px"></textarea>
    				</form>
    				</div>
            </div>
            <div class="modal-footer">
                <!-- <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button> -->
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <!-- <button type="button" class="btn btn-primary" onclick="startup">启动</button> -->
            	<button type="button" class="btn btn-primary" onclick="save('#activitiForm', '${base}activiti/startPrpces.do', '${base}order/show.do?page=${page}')">启动</button>
            </div>
 		</div>
 	</div>
 </div>
 
  <div class="modal fade" id="checkModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" aria-hidden="true">
 	<div class="modal-dialog" style="width: 500px;">
 		<div class="modal-content">
 			<div class="modal-header">
                <h4 class="modal-title" id="checkLable">订单审核</h4>
            </div>
            <div class="modal-body">
            		<div class="form-group">
            		<form role="form" id="activitiForm" name="activitiForm" method='post' enctype="multipart/form-data">
            			<input id="task_verify" name="task_verify" type="hidden">
            			<input id="task_verify_instanceId" name="task_verify_instanceId" type="hidden">
            			<input id="process_Id" name="process_Id" type="hidden">
    					<label for="name">审核意见</label><br>
    					<textarea id="checkInfo" name="checkInfo" style="width: 468px;height:60px"></textarea>
    				</form>
    				</div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="passTask()">通过</button>
            	<button type="button" class="btn btn-default" onclick="denyTask()">不通过</button>
            	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
 		</div>
 	</div>
 </div>
 
 <div class="modal fade" id="taskInfoView" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" aria-hidden="true">
 	<div class="modal-dialog" style="width: 500px;">
 		<div class="modal-content">
 			<div class="modal-header">
                <h4 class="modal-title" id="checkLable">流程进展查看</h4>
            </div>
            <div class="modal-body">
            	<table border="1" id="taskInfoTable"></table>
            </div>
            <div class="modal-footer">
            	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
 		</div>
 	</div>
 </div>
<script>

function passTask(){
	var taskid = $("#task_verify").val();
	var checkInfo = $("#checkInfo").val();
	var instanceId = $("#task_verify_instanceId").val();
	var processId = $("#process_Id").val();
	var checkLable = $('#checkLable').html();
	$.ajax({
		url:'${base}activiti/finisheTaskCondition.do',
		type:'post',
		data:{  taskId : taskid, checkInfo : checkInfo , checkLable : checkLable, pass : true, instanceId : instanceId, processId : processId},
		async:false,
		success:function(data){
			if(data == "success"){
				alert("当前任务["+ taskid +"]已处理！");
				//window.location.reload();
			}else{
				alert("内部错误！！！");
				//window.location.reload();
			}
			var currentTab = $("#currentTabStatus").val();
			location.href = "${base}order/show.do?currentTab=" + currentTab;
		}
	});
}

function denyTask(){
	var taskid = $("#task_verify").val();
	var checkInfo = $("#checkInfo").val();
	var instanceId = $("#task_verify_instanceId").val();
	var processId = $("#process_Id").val();
	var checkLable = $('#checkLable').html();
	$.ajax({
		url:'${base}activiti/finisheTaskCondition.do',
		type:'post',
		data:{  taskId : taskid, checkInfo : checkInfo , checkLable : checkLable, pass : false, instanceId : instanceId, processId : processId},
		async:false,
		success:function(data){
			if(data == "success"){
				alert("当前任务["+ taskid +"]已处理！");
				//window.location.reload();
			}else{
				alert("内部错误！！！");
				//window.location.reload();
			}
			var currentTab = $("#currentTabStatus").val();
			location.href = "${base}order/show.do?currentTab=" + currentTab;
		}
	});
}

function startProcess(obj){
	//var $ele = $(this);
	var id = $(obj).parents('tr').children('td').eq(0).text();
	$('#processDefinitionId').val(id);
	$('#activitiModal').modal('show');
	
/*  	$.ajax({
		url:'${base}activiti/startPrpces.do',
		type:'post',
		data:{ processDefinitionId : id },
		async:false,
		success:function(data){
			if(data == "success"){
				alert("流程已启动");
				window.location.reload();
			}
		}
	}); */ 
}

function recvTask(obj){
	var id = $(obj).parents('tr').children('td').eq(8).text();
	//alert("current processid:" + id);
	$.ajax({
		url:'${base}order/claimTask.do',
		type:'post',
		data:{  processInstantId : id },
		async:false,
		success:function(data){
			if(data == "success"){
				alert("任务["+ id +"]已被您签收！");
				//window.location.reload();
				var currentTab = $("#currentTabStatus").val();
				location.href = "${base}order/show.do?currentTab=" + currentTab;
			}
		}
	});
};

	function finisheTask(obj){
		var id = $(obj).parents('tr').children('td').eq(8).text();
		$.ajax({
			url:'${base}activiti/finisheTask.do',
			type:'post',
			data:{  taskId : id },
			async:false,
			success:function(data){
				if(data == "success"){
					alert("已结束");
					//window.location.reload();
					var currentTab = $("#currentTabStatus").val();
					location.href = "${base}order/show.do?currentTab=" + currentTab;
				}
			}
		});
	};
	
	function viewHistory(obj){
		var id = $(obj).parents('tr').children('td').eq(9).text();
		$.ajax({
			url:'${base}activiti/viewHistory.do',
			type:'post',
			data:{  processInstanceId : id },
			async:false,
			success:function(data){
				$('#taskInfoView').modal('show');
				$("#taskInfoTable").html("<tr style='font-weight: bolder;'><td>节点名称</td><td>处理人</td><td>处理时间</td><td>处理结果</td><td>处理意见</td></tr>");
  	            for( i in data){
  	               if(data[i].activityName == 'Exclusive Gateway'){
  	            	   continue;
  	               }
  	               var assignee = data[i].assignee;
  	               var endTime = data[i].endTime;
  	               var result = null;
  	               var tips = null;
  	               if(typeof(data[i].result) == "undefined"){
  	            	 result = "通过" 
  	               }else{
  	            	 result = data[i].result;
  	               }
  	               if(typeof(data[i].tips) == "undefined"){
  	            	 tips="无"
  	               }else{
  	            	 tips = data[i].tips;
  	               }
  	               if(i == data.length-1){
  	            	   if(assignee == ''){
  	            		 assignee = "待处理";
  	            		 result = "";
  	            		 tips = "";
  	            	   }else if(endTime == ''){
  	            		 result = "处理中";
  	            		 tips = "";
  	            	   }
  	               }
	               $("#taskInfoTable").append("<tr><td>"+data[i].activityName+
	            		   	   "</td><td>"+assignee+
	                           "</td><td>"+endTime+
	                           "</td><td><font color='red'>"+result+
	                           "</td><td>"+tips+
	                           "</td><tr>")
	                 } 
				/* if(data == "success"){
					alert("已结束");
					window.location.reload();
				} */
			}
		});
	};
	
	function checkTask(obj){
		var id = $(obj).parents('tr').children('td').eq(8).text();
		var processInstanceID = $(obj).parents('tr').children('td').eq(9).text();
		var processID = $(obj).parents('tr').children('td').eq(10).text();
		var taskname = $(obj).parents('tr').children('td').eq(12).text();
		$('#checkLable').html(taskname);
		$('#task_verify').val(id);
		$('#task_verify_instanceId').val(processInstanceID);
		$('#process_Id').val(processID);
		$('#checkModal').modal('show');
	}
	
	function showStartupProcessDialog(obj) {
		var id = $(obj).parents('tr').find('.process-id').text();
		var taskname = $(obj).parents('tr').find('.process-name').text();
		var html = "<input id = \"input1\" type=\"text\" name=\"processid\" value=\""+ id +"\">&nbsp<input id = \"input2\" type=\"text\" name=\"taskname\" value=\""+ taskname +"\"></input></input></br></br><button id=\"startup1\" onclick=\"finisheTaskapprove(this)\" >同意</button>&nbsp&nbsp&nbsp&nbsp&nbsp<button id=\"startup2\" onclick=\"finisheTaskdisaprove(this)\" >不同意</button>";
	    BootstrapDialog.show({
	        title: '审核任务',
	        message: html
	    })
	};
	function finisheTaskapprove(obj){
		var id = $("#input1").val();
		var processId = $(obj).parents('tr').children('td').eq(10).text();
		$.ajax({
			url:'${base}activiti/finisheTaskCondition.do',
			type:'post',
			data:{  taskId : id , pass : true , processId : processId},
			async:false,
			success:function(data){
				if(data == "success"){
					alert("任务已通过");
				}
			}
		});
	};
	function finisheTaskdisaprove(obj){
		var id = $("#input2").val();
		var processId = $(obj).parents('tr').children('td').eq(10).text();
		$.ajax({
			url:'${base}activiti/finisheTaskCondition.do',
			type:'post',
			data:{  taskId : id , pass : false, processId : processId},
			async:false,
			success:function(data){
				if(data == "success"){
					alert("任务否决");
				}
			}
		});
	};
	
 	function openOrderInfo(obj){
 		var orderId = $(obj).parents('tr').children('td').eq(2).text();
 		detailOrder(orderId);
	}
	
	$("#tab1").click(function(){

		$("#content1").attr('style','display:block');
		$("#content2").attr('style','display:none');
		$("#content3").attr('style','display:none');
		$("#content4").attr('style','display:none');
		$("#tab1").attr('class','active');
		$("#tab2").removeAttr("class");
		$("#tab3").removeAttr("class");
		$("#tab4").removeAttr("class");
		$("#currentTabStatus").val('tab1');
	}); 
	$("#tab2").click(function(){

		$("#content2").attr('style','display:block');
		$("#content1").attr('style','display:none');
		$("#content3").attr('style','display:none');
		$("#content4").attr('style','display:none');
		$("#tab2").attr('class','active');
		$("#tab1").removeAttr("class");
		$("#tab3").removeAttr("class");
		$("#tab4").removeAttr("class");
		$("#currentTabStatus").val('tab2');
	}); 
	$("#tab3").click(function(){

		$("#content3").attr('style','display:block');
		$("#content1").attr('style','display:none');
		$("#content2").attr('style','display:none');
		$("#content4").attr('style','display:none');
		$("#tab3").attr('class','active');
		$("#tab1").removeAttr("class");
		$("#tab2").removeAttr("class");
		$("#tab4").removeAttr("class");
		$("#currentTabStatus").val('tab3');
	}); 
	$("#tab4").click(function(){

		$("#content4").attr('style','display:block');
		$("#content1").attr('style','display:none');
		$("#content2").attr('style','display:none');
		$("#content3").attr('style','display:none');
		$("#tab4").attr('class','active');
		$("#tab1").removeAttr("class");
		$("#tab2").removeAttr("class");
		$("#tab3").removeAttr("class");
		$("#currentTabStatus").val('tab4');
	}); 
</script>
<script>
		jQuery(document).ready(function() {
 			$("#content3").attr('style','display:block');
			$("#content1").attr('style','display:none');
			$("#content2").attr('style','display:none');
			$("#content4").attr('style','display:none');
			$("#tab3").attr('class','active');
			$("#tab1").removeAttr("class");
			$("#tab2").removeAttr("class");
			$("#tab4").removeAttr("class"); 
			var currentStatus = $("#currentStatus").val();
			var currentTabStatus = $("#currentTabStatus").val();
			if(currentStatus != null && currentStatus != ""){
				$("#" + currentStatus).click();
			}
			if(currentTabStatus != null && currentTabStatus != ""){
				$("#" + currentTabStatus).click();
			}
			var taskNum = $("#taskNum").val();
			$("#taskNumSpan").html(taskNum);
		});
</script>
<script src="${ctx}/js/bootstrap-dialog.min.js"></script>
<script src="${ctx}/js/jquery-easyui/jquery.easyui.min.js"></script>
<script src="${ctx}/js/common.js"></script>