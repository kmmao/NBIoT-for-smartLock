<%@page import="com.routon.plcloud.common.decorator.PageCheckboxDecorator"%>
<%@page import="com.routon.plcloud.common.decorator.PageDateTimeDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@ include file="/WEB-INF/views/head_n.jsp" %>
<%@ include file="/WEB-INF/views/activiti/RuntimeProcess_Project.jsp" %>
	<link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.css">
	<script src="${ctx}/js/bootstrap-datetimepicker.js"></script>
	<script src="${ctx}/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<link rel="stylesheet" href="${ctx}/css/zTreeStyle.css">
<style>
<!--
.ztree li a.curSelectedNode { height: 18px; }
.ztree li a:hover {text-decoration:none}
.count:hover {text-decoration:underline;}
-->
</style>
<!-- 	<div class="panel panel-default container" style="width: 1560px; margin-bottom: 20px;"> -->
		<div class="container" style=" width: 1560px;">
		<input id="activitiTag" name="activitiTag" type="hidden">
		<div class="panel panel-default" style=" width: 1530px; margin-left: -15px;">
  		<div class="panel-heading">
<!--   			<div class="btn-group"> -->
<%--   			<form class="form-inline" role="form" id="queryform" name="queryform" action="${ctx}/project/list.do"  method="post">  		 --%>
<!--   			<div class="btn-group"> -->
<%--   				<input id="name" name="name" type="text" class="form-control" placeholder="请输入分组名称" value="${name}"> --%>
<!--   			</div> -->
<!--   			<div class="btn-group"> -->
<!--   				<button type="submit" class="btn btn-primary" >查询</button> -->
<!--   			</div> -->
<!--   			</form> -->
<!--   			</div> -->
  			
  		<div class="pull-right">
  				
  			<c:if test="${(!empty userPrivilege['90000201'])}">
				<div class="btn-group" >
  					<button type="button" class="btn btn-primary" onclick="addProject()">新建项目</button>
  				</div>
  			</c:if> 	
<%--   		<c:choose>			 --%>
<%--   			<c:when test="${(!empty userPrivilege['90000202'])}"> --%>
<!--   				<div class="btn-group" > -->
<!--   					<button type="button" class="btn btn-primary" value="edit" onclick="editProject(this)">项目详情</button> -->
<!--   				</div> -->
<%--   			</c:when>	 --%>
<%--   			<c:otherwise>	 --%>
<%-- 	  			<c:if test="${(!empty userPrivilege['90000200'])}"> --%>
<!-- 	  				<div class="btn-group" > -->
<!-- 	  					<button type="button" class="btn btn-primary" value="check" onclick="editProject(this)">项目信息详情</button> -->
<!-- 	  				</div> -->
<%-- 	  			</c:if>	 --%>
<%--   			</c:otherwise> --%>
<%--   		 </c:choose> --%>
  			<c:if test="${(!empty userPrivilege['90000203'])}">
  				<div class="btn-group" >
  					<button type="button" class="btn btn-danger" onclick="disableProject()">关闭项目</button>
  				</div>
  			</c:if>	
  		</div> 
  			 <h5>  订单    > 项目管理 </h5>
  		</div>
  		<div class="panel-body">
  			<div class="panel panel-default col-sm-2" style=" overflow-y:auto; overflow-x:auto;width:240px; height:534px; " >
<!--  ***** 	公司查询                  ****-->
  			<form class="form-inline" role="form" id="queryform" name="queryform" action="${ctx}/project/list.do"  method="post">  
				<div class="btn-group"  >				
  				   <input style= "width:128px;float:left" id="companyname" name="companyname" type="text" class="form-control" placeholder="请输入公司名称" value="${companyname}" onkeyup="this.value=this.value.replace(/[^\uFF00-\uFFFF\u4e00-\u9fa5\w\@\&\-]/g,'')">			
  			    </div>
  			   
  			    <div class="btn-group" >
  				   <button type="submit" class="btn btn-primary" >查询</button>
  			    </div>
  			    </form>	
  			    
				<ul id="tree" class="ztree "></ul>
			</div>
			<div class="panel panel-default col-sm-10">			
			    <form class="form-inline" role="form" id="queryform" name="queryform" action="${ctx}/project/list.do"  method="post">  
				<div class="btn-group">
  				   <input id="projectname" name="projectname" type="text" class="form-control" placeholder="请输入项目名称" value="${projectname}" onkeyup="this.value=this.value.replace(/[^\uFF00-\uFFFF\u4e00-\u9fa5\w\@\&\-]/g,'')">			
  			    </div>
  			    <div class="btn-group">
  				   <button type="submit" class="btn btn-primary" >查询</button>
  			    </div>
  			    </form>	 
  			    
  			    <!-- table -->
		<display:table name="requestScope.pageList" id="curPage" class="table table-striped" sort="external"
			requestURI="list.do"
			decorator="com.routon.plcloud.common.decorator.PageProjectDecorator"
			export="false">
			<display:column property="id" title="<%=PageCheckboxDecorator.getTitle(pageContext)%>" decorator="com.routon.plcloud.common.decorator.PageCheckboxDecorator" media="html" style="width:2%;"/>
			<display:column title="ID"  property="id"  sortable="true"  style="width:5%;" />
			<display:column title="公司地址"  property="address" style="width:10%;">
			</display:column>
			<display:column title="项目名称" sortable="true"  property="projectname" style="width:10%;">
			</display:column>
			<display:column title="项目地址" sortable="true"  property="projectadd" style="width:10%;">
			</display:column>
			<display:column title="客户商务人员姓名" sortable="true"  property="cusprojectname" style="width:10%;">
			</display:column>
			<display:column title="客户商务人员电话" sortable="true"  property="cusprojectphone" style="width:15%;">
			</display:column>
			<display:column title="拟需求数量" sortable="true"  property="demandquantity" style="width:8%;">
			</display:column>
			<display:column title="订单已发数量" sortable=""  property="ordersale" style="width:8%;">
			</display:column>
			<display:column title="拟剩余数量" sortable=""  property="restnum" style="width:8%;">
			</display:column>
			<display:column title="需求类型" sortable="true"  property="requirementtype" style="width:9%;">
			</display:column>
			<display:column title="状态" sortable="true"  sortProperty="status" style="width:5%;">
				<c:choose>
					<c:when test="${curPage.status == 2}">
					已审核
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${curPage.status == 1}">
					未审核
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${curPage.status == 0}">
					无效
					</c:when>
				</c:choose>
			</display:column>
		</display:table>
    
  			   
  			       
			</div>
		 <%@ include file="/WEB-INF/views/common/paginationSpecial.jsp" %>
		 	<select id="select_page_4" name="select_page_4" onchange="javascript:window.location.href=this.options[this.selectedIndex].value;" style="display:none">
	 				<option value="list.do?pageOrder=1&id=${treeNodeid}&treeNodeTid=${treeNodeTid}" selected="selected">1</option>
	 				<c:forEach var="i" begin="2" end="${maxpage}">
						<option value="list.do?pageOrder=${i}&id=${treeNodeid}&treeNodeTid=${treeNodeTid}">${i}</option>
					</c:forEach>
	 		</select>   
 		</div>
 </div>
  </div>
 		
 		
	</div>	
<!-- 	模态框 -->
	<div class="modal fade" id="myform" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content" style="width: 500px;">
	      <div class="modal-header">
<!-- 	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button> -->
	        <h4 class="modal-title" id="myModalLabel">项目详情</h4>
	      </div>
	      <div class="modal-body">
	       	<form:form class="form-horizontal"  id="projectForm" name = "projectForm" role="form" method="post" enctype="multipart/form-data" >
	       	
	       	<input id="hiddenInput" type="hidden" value="${treeNodeTid}">
			<input id="treeNodeid" type="hidden" value="${treeNodeid}">
			      <input type="hidden"  id="projectid" name="projectid" >

	       	
			  <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label"  style="padding-left: 5px;padding-right: 5px;">公司名称</label>
			    <div class="col-sm-8" style="padding-left: 0px">
			      <input type="text" class="form-control" id="companyname1" name="companyname1" disabled>
			    </div> 			   
			  </div>
			  <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;">公司地址</label>
			    <div class="col-sm-8" style="padding-left: 0px">
			      <input type="text" class="form-control" id="address" name="address" disabled >
			    </div> 			   
			  </div>
			  <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;">客户商务人员姓名</label>
			    <div class="col-sm-8" style="padding-left: 0px">
			      <input type="text" class="form-control" id="contactname" name="contactname" disabled >
			    </div> 			   
			  </div>
			   <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;">客户商务人员电话</label>
			    <div class="col-sm-8" style="padding-left: 0px">
			      <input type="text" class="form-control" id="contactphone" name="contactphone" disabled >
			    </div> 			   
			  </div>
				  <div class="form-group" style="margin-right: 0px;">
				    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;color:red;">项目注册名称</label>
				    <div class="col-sm-8" style="padding-left: 0px">
				      <input type="text" class="form-control" id="projectregname" name="projectregname" placeholder="长度不能超过120个字符" onkeyup="this.value=this.value.replace(/[^\uFF00-\uFFFF\u4e00-\u9fa5\w\@\&\-]/g,'')">
				    </div> 			   
				  </div>
				  <div id="projecthide" class="form-group" style="margin-right: 0px;">
				    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;color:red;">项目名称</label>
				    <div class="col-sm-8" style="padding-left: 0px">
				      <input type="text" class="form-control" id="projectnameadd" name="projectnameadd" placeholder="长度不能超过120个字符" onkeyup="this.value=this.value.replace(/[^\uFF00-\uFFFF\u4e00-\u9fa5\w\@\&\-]/g,'')">
				    </div> 			   
				  </div>
			  <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;color:red;">项目地址</label>
			    <div class="col-sm-8" style="padding-left: 0px">
			      <input type="text" class="form-control" id="projectadd" name="projectadd" placeholder="长度不能超过250个字符" onkeyup="this.value=this.value.replace(/[^\uFF00-\uFFFF\u4e00-\u9fa5\w\@\&\-]/g,'')">
			    </div> 			   
			  </div>
			  <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;color:red;">客户项目负责人员姓名</label>
			    <div class="col-sm-8" style="padding-left: 0px">
			      <input type="text" class="form-control" id="cusprojectname" name="cusprojectname" placeholder="长度不能超过25个字符" onkeyup="this.value=this.value.replace(/[^\uFF00-\uFFFF\u4e00-\u9fa5\w\@\&\-]/g,'')">
			    </div> 			   
			  </div>
			  <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;color:red;">客户项目负责人员手机号码</label>
			    <div class="col-sm-8" style="padding-left: 0px">
			      <input type="text" class="form-control" id="cusprojectphone" name="cusprojectphone"  placeholder="11位数字">
			    </div> 			   
			  </div>
			  
<!-- 			  软件ERP编码 -->
			   <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;color:red;">软件ERP编码</label>
			     <div class="col-sm-8" style="padding-left: 0px">
			   		<select  class="form-control" id="softwareerpnumber" name="softwareerpnumber" onchange="specieSelChange(this)">
				      			<option value="">―请选择―</option>
				      			<c:forEach var="item" items="${softwareERPcodeList}">
									<option value="${item}">${item}</option>
							    </c:forEach> 
					</select>	
					</div>		   
			  </div>
			  
<!-- 			  产品名称及版本号 -->
  			  <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;color:red;">产品名称及版本号</label>
			    <div class="col-sm-8" style="padding-left: 0px">
			      <input type="text" class="form-control" id="softwaretypeversion" name="softwaretypeversion"   disabled>
			    </div> 			   
			  </div>
			  
			  <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;color:red">拟需求总数量</label>
			    <div class="col-sm-8" style="padding-left: 0px">
			      <input type="text" class="form-control" id="demandquantity" name="demandquantity"  placeholder="数量不超过1000000">
			    </div> 			   
			  </div>
			   <div class="form-group" style="margin-right: 0px;">
			  		<label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;color:red;">需求类型</label>
			  		<div class="col-sm-8" style="padding-left: 0px">
			  		 <select id="requirementtype" name="requirementtype" class="form-control "  >			    
			      		<option value="购买" selected="selected" >购买</option>
						<option value="试用" selected="selected" >试用</option>
				  </select>
				   </div>
			    </div>
			    <div class="form-group" style="margin-right: 0px;">
			  		<label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;color:red;">所属行业</label>
			  		<div class="col-sm-8" style="padding-left: 0px">
			  		 <select id="industry" name="industry" class="form-control "  >			    
			      		<option value="酒店" selected="selected" >酒店</option>
						<option value="其他" selected="selected" >其他</option>
				  </select>
				   </div>
			    </div>
			    
			    <div class="form-group" style="margin-right: 0px;">
			  		<label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;color:red;">授权认证方式</label>
			  		<div class="col-sm-8" style="padding-left: 0px">
			  		 <select id="authentication" name="authentication" class="form-control " >			    
			      		<option value="客户代码" selected="selected" >客户代码</option>
						<option value="终端代码"  >终端代码</option>
						<option value="客户代码+终端代码"  >客户代码+终端代码</option>
				  </select>
				   </div>
			    </div>
			    
			    <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;color:red;">SDK_name</label>
			    <div class="col-sm-8" style="padding-left: 0px">
			      <input type="text" class="form-control" id="sdkname" name="sdkname"  placeholder="长度不能超过16个字符" onkeyup="this.value=this.value.replace(/[^\uFF00-\uFFFF\u4e00-\u9fa5\w\@\&\-]/g,'')">
			    </div> 			   
			  </div>
			    
			  <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;color:red;">SDK_key</label>
			    <div class="col-sm-8" style="padding-left: 0px">
			      <input type="text" class="form-control" id="licensekey" name="licensekey" placeholder="长度必须是32个字符" >
			    </div> 			   
			  </div>
<!-- 			  <div class="form-group" style="margin-right: 0px;"> -->
<!-- 			    <label for="title" class=" control-label" style="padding-left: 5px;padding-right: 5px;color:red;">有效期</label> -->
<!-- 			    <br> -->
<!-- 			    <div  style="padding-left: 0px"> -->
<!-- 			      <input type="radio"  id="choose1" name="choose"  class="col-sm-1" value = "1" > -->
<!-- 			      <input type="text" id="month1" name="month1" class="col-sm-2" disabled> -->
<!-- 			      <div class="col-sm-3"> -->
<!-- 			      		 <h5>月，自合同生效之日算起</h5> -->
<!-- 			      </div>		      -->
<!-- 					时间输入框 -->
<!-- 			  			<div class="input-group date form_datetime1 col-sm-6"> -->
<%-- 						    <input size="20" type="text" id="starttime1" name="starttime1" value="${starttime}" class="form-control "  placeholder="请输入导入起始时间" readonly> --%>
<!-- 						    <span  class="input-group-addon"> -->
<!-- 						    <span  class="glyphicon glyphicon-calendar"></span> -->
<!-- 						    </span> -->
<!-- 						</div>					 -->
<!-- 			    </div> 	 -->
<!-- 			    <br> -->
<!-- 			    <div  style="padding-left: 0px"> -->
<!-- 			      <input type="radio"  id="choose2" name="choose"  class="col-sm-1" value = "2"> -->
<!-- 			      <input type="text" id="month2" name="month2"class="col-sm-2" disabled> -->
<!-- 			      <div class="col-sm-3"> -->
<!-- 			      		 <h5>月，自产品激活之日算起(合同生效三个月视同激活)</h5> -->
<!-- 			      </div>		      -->
<!-- 					时间输入框 -->
<!-- 			  			<div class="input-group date form_datetime2 col-sm-6" > -->
<%-- 						    <input size="20" type="text" id="starttime2" name="starttime2" value="${tacitstarttime}" class="form-control "  placeholder="请输入导入起始时间" readonly> --%>
<!-- 						    <span  class="input-group-addon"> -->
<!-- 						    <span class="glyphicon glyphicon-calendar"></span> -->
<!-- 						    </span> -->
<!-- 						</div>	 -->
										
<!-- 			    </div> 	 -->
<!-- 			    <br> -->
<!-- 			     <br> -->
<!-- 			      <br> -->
<!-- 			     <div  style="padding-left: 0px"> -->
<!-- 			      <input type="radio"  id="choose3" name="choose"  class="col-sm-1" value = "3"> -->
<!-- 			      <div class="col-sm-2"> -->
<!-- 			      		 <h5>永久有效</h5> -->
<!-- 			      </div>		      -->
<!-- 					时间输入框 -->
<!-- 			  			<div class="input-group date form_datetime3 col-sm-6" > -->
<%-- 						    <input size="20" type="text" id="endtime" name="endtime" value="${endtime}" class="form-control "  placeholder="请输入导入起始时间" readonly> --%>
<!-- 						    <span  class="input-group-addon"> -->
<!-- 						    <span class="glyphicon glyphicon-calendar"></span> -->
<!-- 						    </span> -->
<!-- 						</div>					 -->
<!-- 			    </div> 		    -->
<!-- 			  </div> -->
			  
			</form:form>
	      </div>
	      <div class="modal-footer">
	    		  <button type="button" class="btn btn-default" onclick="JudgesubmitForm()">返回</button> 
	      		  <button id="projectsave" name="projectsave" type="button" class="btn btn-primary" onclick="addSubmit()">确定</button> 
	      </div>
	    </div>
	  </div>
	  
	  
<script>
// $(".form_datetime").datetimepicker({
//     format: "yyyy-mm-dd hh:ii:ss",
//     autoclose: true,
//     todayBtn: true,
//    	clearBtn:true,
//     language:'zh-CN',
//     pickerPosition: "bottom-left"
// });



// $("#querydata1").off("click");
// $("#querydata2").off("click");
// $("#querydata3").off("click");
</script>
	</div>

	
   
<script src="${ctx}/js/common.js"></script>
<SCRIPT type="text/javascript">

$(function(){
	  $(":radio").click(function(){
	  // alert("您是..."  );
		  var val=$('input:radio[name="choose"]:checked').val();
		  if(val == 1 )
			  {
//choose 1
			  document.getElementById("month1").disabled = false;
// 			  document.getElementById("starttime1").disabled = false;
			  document.getElementById("month2").disabled = true;
// 			  document.getElementById("starttime2").disabled = true;
// 			  document.getElementById("endtime").disabled = true;
			  $(".form_datetime1").datetimepicker({
				    format: "yyyy-mm-dd hh:ii:ss",
				    autoclose: true,
				    todayBtn: true,
				   	clearBtn:true,
				    language:'zh-CN',
				    pickerPosition: "bottom-left"
				});
// 			  $(".form_datetime2").datetimepicker('remove');
// 			  $(".form_datetime3").datetimepicker('remove');

//choose 2
			  $("#month2").val("");
			  $("#starttime2").val("");
			  $("#endtime").val("");
			  }
		  else if(val == 2)
			  {
//  choose 1
			  document.getElementById("month1").disabled = true;
// 			  document.getElementById("starttime1").disabled = true;
			  document.getElementById("month2").disabled = false;
// 			  document.getElementById("starttime2").disabled = false;
// 			  document.getElementById("endtime").disabled = true;
			  $(".form_datetime2").datetimepicker({
				    format: "yyyy-mm-dd hh:ii:ss",
				    autoclose: true,
				    todayBtn: true,
				   	clearBtn:true,
				    language:'zh-CN',
				    pickerPosition: "bottom-left"
				});
// 			  $(".form_datetime1").datetimepicker('remove');
// 			  $(".form_datetime3").datetimepicker('remove');


//   choose 2
			  $("#month1").val("");
			  $("#starttime1").val("");
			  $("#endtime").val("");
			  }
		  else{
//   choose 1
			  document.getElementById("month1").disabled = true;
// 			  document.getElementById("starttime1").disabled = true;
			  document.getElementById("month2").disabled = true;
// 			  document.getElementById("starttime2").disabled = true;
// 			  document.getElementById("endtime").disabled = false;
			  $(".form_datetime3").datetimepicker({
				    format: "yyyy-mm-dd hh:ii:ss",
				    autoclose: true,
				    todayBtn: true,
				   	clearBtn:true,
				    language:'zh-CN',
				    pickerPosition: "bottom-left"
				});
// 			  $(".form_datetime1").datetimepicker('remove');
// 			  $(".form_datetime2").datetimepicker('remove');


//  choose 2
			  $("#month1").val("");
			  $("#starttime1").val("");
			  $("#month2").val("");
			  $("#starttime2").val("");
		  }
	  });
 });



var setting = {
		view: {
			//addHoverDom: addHoverDom,
			//removeHoverDom: removeHoverDom,
			//addDiyDom: addDiyDom,
			selectedMulti: false
		},
		edit: {
// 			enable: true,
// 			editNameSelectAll: true,
	//		showRemoveBtn: showRemoveBtn,
// 			showRenameBtn: showRenameBtn
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			//beforeDrag: beforeDrag,
			//beforeEditName: beforeEditName,
		//	beforeRemove: delGroup,
	//		beforeRename: beforeRename,
			//onRemove: delGroup,
			//onRename: onRename
// 			onDblClick: zTreeBeforeClick
			beforeClick: zTreeBeforeClick
		}
	};
	
var IDMark_Switch = "_switch",
IDMark_Icon = "_ico",
IDMark_Span = "_span",
IDMark_Input = "_input",
IDMark_Check = "_check",
// IDMark_Edit = "_edit",
// IDMark_Remove = "_remove",
IDMark_Ul = "_ul",
IDMark_A = "_a";


// function zTreeOnDblClick(event, treeId, treeNode) {
//     alert(treeNode ? treeNode.tId + ", " + treeNode.name : "isRoot");
// };
// var setting = {
// 	callback: {
// 		onDblClick: zTreeOnDblClick
// 	}
// };

function zTreeBeforeClick(treeId, treeNode , clickFlag) {
	
//  		var nodes = zTree.getSelectedNodes();
		
// 		if(nodes[0].id == -1)
	     if(treeNode.id == -1)
			{
			 	 document.location.href = '${ctx}/project/list.do?page=${1}&treeNodeTid=' + treeNode.tId ;
// 			 	document.location.href = '${ctx}/project/list.do?page=${page}';
 			 

	//			gotourl('${ctx}/project/list.do?page=${page}&treeNodeTid=' + treeNode.tId);
			}
		else{
// 			 return document.location.href = '${ctx}/project/list.do?page=${page}&id='+nodes[0].id + '&treeNodeTid='+ treeNode.tId;
			  document.location.href = '${ctx}/project/list.do?page=${1}&id='+treeNode.id + '&treeNodeTid='+ treeNode.tId;
// 			  document.location.href = '${ctx}/project/list.do?page=${page}&id='+treeNode.id;
	//		 gotourl('${ctx}/project/list.do?page=${page}&id='+nodes[0].id + '&treeNodeTid='+ treeNode.tId);
// 				+'&treeNodeTid='+ treeNode.tId
		}
				
};

function addDiyDom(treeId, treeNode) {
	
	var aObj = $("#" + treeNode.tId + IDMark_A);
	if (treeNode.userCount > 0) {
		var editStr = "&nbsp;&nbsp;<span id='userCount_" +treeNode.id+ "' class='count' onclick='alert("+treeNode.userCount+");return false;'>用户数:"+treeNode.userCount+"</span>";
		aObj.append(editStr);
		var btn = $("#userCount_"+treeNode.id);
		//if (btn) btn.bind("click", function(){alert("diy Button for " + treeNode.name);});
	} 
	
	if (treeNode.terminalCount > 0) {
		var editStr = "&nbsp;&nbsp;<span id='terminalCount_" +treeNode.id+ "' class='count' onclick='alert("+treeNode.terminalCount+");return false;'>终端数:"+treeNode.terminalCount+"</span>";
		aObj.append(editStr);
		var btn = $("#terminalCount_"+treeNode.id);
		//if (btn) btn.bind("click", function(){alert("diy Button for " + treeNode.name);});
	}
	if (treeNode.resourceCount > 0) {
		var editStr = "&nbsp;&nbsp;<span id='resourceCount_" +treeNode.id+ "' class='count' onclick='alert("+treeNode.resourceCount+");return false;'>资源数:"+treeNode.resourceCount+"</span>";
		aObj.append(editStr);
		var btn = $("#resourceCount_"+treeNode.id);
		//if (btn) btn.bind("click", function(){alert("diy Button for " + treeNode.name);});
	}
	if (treeNode.noticeCount > 0) {
		var editStr = "&nbsp;&nbsp;<span id='noticeCount_" +treeNode.id+ "' class='count' onclick='alert("+treeNode.noticeCount+");return false;'>公告数:"+treeNode.noticeCount+"</span>";
		aObj.append(editStr);
		var btn = $("#noticeCount_"+treeNode.id);
		//if (btn) btn.bind("click", function(){alert("diy Button for " + treeNode.name);});
	}
}

function removeHoverDom(treeId, treeNode) {
	$("#userCount_"+treeNode.id).unbind().remove();
	$("#terminalCount_"+treeNode.id).unbind().remove();
	$("#resourceCount_"+treeNode.id).unbind().remove();
	$("#noticeCount_"+treeNode.id).unbind().remove();
}

var zNodes =${groupTreeBeans};

// function showRenameBtn(treeId, treeNode){
// // 	<c:if test="${(empty userPrivilege['10000101'])}">
	
// 		if(true){
// 			return false;
// 		}
// // 	</c:if>
// 	return  treeNode.id != 2 && treeNode.id != 1;
// }

// function showRemoveBtn(treeId, treeNode) {
// // 	<c:if test="${(empty userPrivilege['10000102'])}">
// 		if(true){
// 			return false;
// 		}
// // 	</c:if>
// 	var isParent = treeNode.isParent;
// 	return !isParent && treeNode.id != 2;
// }

var zTree;
$(document).ready(function(){
	
	
	zTree = $.fn.zTree.init($("#tree"), setting, zNodes);
	var treeObj = $.fn.zTree.getZTreeObj("tree");
// 	var checkedNodetid = $('#hiddenInput').val();
	var treeNodeid = $('#treeNodeid').val();
	var node = treeObj.getNodeByParam("id", treeNodeid, null);
	if(node !=null){
		$("#" + node.tId + "_a").attr('class', 'curSelectedNode');
	}
	
// 	var node_tem = treeObj.getNodeByTId(checkedNodetid);
// 	if (node_tem != null) {
// 		treeObj.selectNode(node_tem[0]);
// 	}
	
// 	var treeObj = $.fn.zTree.getZTreeObj("tree");
// 	var nodes = treeObj.getNodes();
// 	if (nodes.length>0) {
// 	    for(var i=0;i<nodes.length;i++){
// 	    treeObj.expandNode(nodes[i], true, false, false);
// 	    }
// 	}
	
});

function JudgesubmitForm(){
	var change = 0;
    var form = document.getElementById('projectForm');
    for (var i = 0; i < form.length; i++) {
        var element = form.elements[i];
        var type = element.type;
        if (type == "checkbox" || type == "radio") {
 
            if (element.checked != element.defaultChecked) {
               change = 1;
            }
        }
        if (type == "hidden" || type == "password" || type == "text" || type == "textarea") {
 
            if (element.value != element.defaultValue) {
                change = 1;
            }
        }
 
        if (type == "select-one" || type == "select-multiple") {
 
            for (var j = 0; j < element.options.length; j++) {
 
                if (element.options[j].selected != element.options[j].defaultSelected) {
                	change = 1;
                }
            }
        }
        if (type == "file") {
            if (element.value.length != 0) {
                Filechange = false;
                change = 1;
            }
        }
    }
    if(change == 1){
    	if(confirm("您输入的信息未保存,确认退出吗?")){
    		gotourl('${ctx}/project/list.do?page=${page}');
    	}
    }
    else{
    	gotourl('${ctx}/project/list.do?page=${page}');
    }
}

function specieSelChange(selBox) {
	var data = selBox.value;
	$.ajax({ 
		type        : "POST",
		url         : "${base}order/querySoftwareNameByERPcode.do",
		data        :  {erpCode : data},
		contentType : "application/x-www-form-urlencoded;charset=utf-8;",
		scriptCharset: 'utf-8',
		success: function(info) {
            $('#softwaretypeversion').val(info);
		}
	});
}


function addProject(){
	
// 	var checkedNodetid = $('#hiddenInput').val();
	var treeNodeid = $('#treeNodeid').val();
	$('#activitiTag').val('true');
// 	if(checkedNodetid == "" || checkedNodetid == "tree_1"){
	if(treeNodeid==""){
		alert("请选择一个公司");
		return false;
	}
	else{
		var treeObj = $.fn.zTree.getZTreeObj("tree");
// 	 	var node_tem = treeObj.getNodeByTId(checkedNodetid);
	 	var node_tem = treeObj.getNodeByParam("id", treeNodeid, null);
	}

// 	var nodes = zTree.getSelectedNodes();
// 	if (nodes.length != 1) {
// 		alert("请选择一个公司!");
// 		return false;
// 	}
// 	else if(nodes[0].id == -1)
// 		{
// 		alert("请选择一个公司!");
// 		return false;
// 		}
	 
	$('#myform').modal('show');
	document.getElementById("projecthide").style.display = "none";
	$.ajax({
		url:'${base}project/companyshow.do',
		type:'post',
		data:{id : node_tem.id},
		async:false,
		success:function(data){
			$("#companyname1").val(data.companyname);
			$("#address").val(data.address);
			$("#contactname").val(data.contactname);
			$("#contactphone").val(data.contactphone);
		}
	
	});
	
}

function EditProject(obj){
	var projectId = $(obj).parents('tr').children('td').eq(1).text();
	editProject(projectId);
}


function editProject(type){
	var result = type.value;
	if(result == 'edit'){
		var selectedIds = getCheckedRowValue("");
		if(selectedIds==""){
			alert("请选择一个进行编辑");
			return false;
		}
		var selectedId = selectedIds.split(",");
		if (selectedId.length != 1) {
			alert("请选择一个进行编辑");
			return false;
		}	
	}
	else if(result == 'check'){
		var selectedIds = getCheckedRowValue("");
		if(selectedIds==""){
			alert("请选择一个进行编辑");
			return false;
		}
		var selectedId = selectedIds.split(",");
		if (selectedId.length != 1) {
			alert("请选择一个进行编辑");
			return false;
		}
		$('#projectsave').attr('disabled','disabled');
	}else{
		if(type!=null){
			var selectedIds = type;
		}
	}
	
	$('#myform').modal('show');
// 	 document.getElementById("projectnameadd").readOnly = true;
	 document.getElementById("projectregname").readOnly = true;
	 document.getElementById("sdkname").readOnly = true;
	 document.getElementById("licensekey").readOnly = true;
	$.ajax({
		url:'${base}project/projectshow.do',
		type:'post',
		data:{id : selectedIds},
		async:false,
		success:function(data){
			var project = data.obj;
			var company = data.obj1;
			//判断是否有编辑权限
			if(data.code == 0){
				$('#projectsave').attr('disabled','disabled');
			}
			
// 			if(project.starttime != null){
// 				$("#month1").val(project.month);
// 				$("#starttime1").val(project.starttime);
// 				 document.getElementById("choose1").checked = "cheched";
// 			}
// 			else if(project.tacitstarttime !=null){
// 				$("#month2").val(project.month);
// 				$("#starttime2").val(project.tacitstarttime);
// 				 document.getElementById("choose2").checked = "cheched";
// 			}
// 			else if(project.endtime != null){
// 				$("#endtime").val(project.endtime);	
// 				 document.getElementById("choose3").checked = "cheched";
// 			}
			$("#activitiTag").val("");
			$("#companyname1").val(company.companyname);
			$("#address").val(company.address);
			$("#contactname").val(company.contactname);
			$("#contactphone").val(company.contactphone);
// 			$("#projectname").val(company.projectname);
			$("#projectid").val(project.id);
			$("#projectregname").val(project.projectregname);
			$("#projectnameadd").val(project.projectname);
			$("#projectadd").val(project.projectadd);
			$("#cusprojectname").val(project.cusprojectname);
			$("#cusprojectphone").val(project.cusprojectphone);
			$("#demandquantity").val(project.demandquantity);
			$("#requirementtype").val(project.requirementtype);
			$("#industry").val(project.industry);
			$("#sdkname").val(project.sdkname);
			$("#softwareerpnumber").val(project.softwareerpnumber);
			$("#softwaretypeversion").val(project.softwaretypeversion);
			$("#licensekey").val(project.licensekey);
// 			$("#month1").val(project.month);
// 			$("#starttime1").val(project.starttime);
// 			$("#starttime2").val(project.tacitstarttime);
// 			$("#endtime").val(project.endtime);		
		}
	
	});
	
}

function Reallength(s) { 
	var totalLength = 0; 
	var i; 
	var charCode; 
	for (i = 0; i < s.length; i++) { 
	charCode = s.charCodeAt(i); 
	if (charCode < 0x007f) { 
	totalLength = totalLength + 1; 
	} else if ((0x0080 <= charCode) && (charCode <= 0x07ff)) { 
	totalLength += 2; 
	} else if ((0x0800 <= charCode) && (charCode <= 0xffff)) { 
	totalLength += 3; 
	} 
	} 
	//alert(totalLength); 
	return totalLength; 
} 


//检查字符串实际长度
// function Reallength(str){
// 	var realLength = 0, len = str.length, charCode = -1;  
//     for ( var i = 0; i < len; i++) {  
//         charCode = str.charCodeAt(i);  
//         if (charCode >= 0 && charCode <= 128)  
//             realLength += 1;  
//         else  
//             realLength += 2;  
//     }  
//     return realLength; 
// }

//检查非必填项（非空或者超过长度）
function checkvalue(checkValue , inputname , length){
	var flag = false;
	if(checkValue != "" && Reallength(checkValue) > length){
		alert("您输入的" + inputname + "长度超过"+length+"，请重新输入");
		return flag;
	}
}

//检查必填项（为空、超过长度）
function checkRedvalue(checkValue , inputname , length){
	var flag = false;

// 	alert("输入的字节数为"+Reallength(checkValue));
	if(checkValue == ""){
		alert("请输入"+inputname);
		return flag;
	}
	else if(Reallength(checkValue) > length){
			
		alert("您输入的" + inputname + "长度超过"+length+"，请重新输入");
		return flag;
	}
}




function addSubmit(){
		var projectnameadd = null;
		if(document.getElementById("projecthide").style.display != "none"){
			 projectnameadd = $("#projectnameadd").val().trim();
			var projectnameaddinfo = "项目名称";
			var projectnameaddlength = 32;
			var projectnameaddflag = checkRedvalue(projectnameadd,projectnameaddinfo,projectnameaddlength);
			if(projectnameaddflag == false){
				var projectnameaddInput = document.getElementById("projectnameadd");
				projectnameaddInput.focus();
				return projectnameaddflag;
			}
		}

		var projectregname = $("#projectregname").val().trim();
		var projectregnameinfo = "项目注册名称";
		var projectregnamelength = 32;
		var projectregnameflag = checkRedvalue(projectregname,projectregnameinfo,projectregnamelength);
		if(projectregnameflag == false){
			var projectregnameInput = document.getElementById("projectregname");
			projectregnameInput.focus();
			return projectregnameflag;
		}
		
		var projectadd = $("#projectadd").val().trim();
		var projectaddinfo = "项目地址";
		var projectaddlength = 500;
		var projectaddflag = checkRedvalue(projectadd,projectaddinfo,projectaddlength);
		if(projectaddflag == false){
			var projectaddInput = document.getElementById("projectadd");
			projectaddInput.focus();
			return projectaddflag;
		}
		
		var cusprojectname = $("#cusprojectname").val().trim();
		var cusprojectnameinfo = "客户项目负责人员姓名";
		var cusprojectnamelength = 25;
		var cusprojectnameflag = checkRedvalue(cusprojectname,cusprojectnameinfo,cusprojectnamelength);
		if(cusprojectnameflag == false){
			var cusprojectnameInput = document.getElementById("cusprojectname");
			cusprojectnameInput.focus();
			return cusprojectnameflag;
		}
		
		var cusprojectphone = $("#cusprojectphone").val().trim();
		var checkcusprojectphone = /^1[34578]\d{9}$/; 
		var len = cusprojectphone.length;
		if(cusprojectphone == ""){
			alert("请输入手机号码");
			var cusprojectphoneInput = document.getElementById("cusprojectphone");
			cusprojectphoneInput.focus();
			return false;
		}
		else if(!checkcusprojectphone.test(cusprojectphone)){
			alert("您输入的手机号码格式不对");
			var cusprojectphoneInput = document.getElementById("cusprojectphone");
			cusprojectphoneInput.focus();
			return false;
		}
		
		//绑定方式检测
		var softwareerpnumber = $("#softwareerpnumber").val();
		if(softwareerpnumber == ""){
			alert("请选择一个软件ERP编码");
			var softwareerpnumberInput = document.getElementById("softwareerpnumber");
			softwareerpnumberInput.focus();
			return false;
		}
		
		//拟需求数量检查
		var demandquantity = $("#demandquantity").val().trim();
		var checkdemandquantity = /^[+]{0,1}(\d+)$/; 
		if(demandquantity == ""){
			alert("请输入拟需求数量");
			var demandquantityInput = document.getElementById("demandquantity");
			demandquantityInput.focus();
			return false;
		}
		else if(!checkdemandquantity.test(demandquantity)){
			alert("您输入的拟需求数量格式不对");
			var demandquantityInput = document.getElementById("demandquantity");
			demandquantityInput.focus();
			return false;
		}
		else if(parseInt(demandquantity)>1000000){
			alert("您输入的数值过大,请重新输入");
			return false;
		}
		
		
		
// 		var demandquantityinfo = "拟需求总数量";
// 		var demandquantitylength = 12;
// 		var demandquantityflag = checkvalue(demandquantity,demandquantityinfo,demandquantitylength);
// 		if(demandquantityflag == false){
// 			var demandquantityInput = document.getElementById("demandquantity");
// 			demandquantityInput.focus();
// 			return demandquantityflag;
// 		}
		
		var requirementtype = $("#requirementtype").val();
		if(requirementtype == ""){
			alert("请选择一个需求类型");
			var requirementtypeInput = document.getElementById("requirementtype");
			requirementtypeInput.focus();
			return false;
		}
		
		var industry = $("#industry").val();
		if(industry == ""){
			alert("请选择一个所属行业");
			var industryInput = document.getElementById("industry");
			industryInput.focus();
			return false;
		}
		
		var authentication = $("#authentication").val();
		if(authentication == ""){
			alert("请选择一个授权认证方式");
			var authenticationInput = document.getElementById("authentication");
			authenticationInput.focus();
			return false;
		}
		
		var sdkname = $("#sdkname").val().trim();
		var sdknameinfo = "sdkname";
		var sdknamelength = 32;
		var sdknameflag = checkRedvalue(sdkname,sdknameinfo,sdknamelength);
		if(sdknameflag == false){
			var sdknameInput = document.getElementById("sdkname");
			sdknameInput.focus();
			return sdknameflag;
		}
		
		var licensekey = $("#licensekey").val().trim();
		if(licensekey == ""){
			alert("请输入SDK_key信息");
			return false;
		}
		else if(licensekey.length != 32){
			alert("您输入的SDK_key必须为32位字符串");
			return false;
		}
		else{
			var realLength = 0, len = licensekey.length, charCode = -1;  
	        for ( var i = 0; i < len; i++) {  
	            charCode = licensekey.charCodeAt(i);  
	            if (charCode >= 0 && charCode <= 128)  
	                realLength += 1;  
	            else  
	                realLength += 2;  
	        }  
	        if(realLength > 32){
	        	alert("您输入的SDK_key必须为32位字符串");
				return false;
	        }  
		}
		
		
// 		var licensekeyinfo = "sdkkey";
// 		var licensekeylength = 16;
// 		var licensekeyflag = checkRedvalue(licensekey,licensekeyinfo,licensekeylength);
// 		if(licensekeyflag == false){
// 			var licensekeyInput = document.getElementById("licensekey");
// 			licensekeyInput.focus();
// 			return licensekeyflag;
// 		}
		

	
		
		
		
// 	    var demandquantity = $("#demandquantity").val();
// 	    var requirementtype = $("#requirementtype").val();
// 	    var industry = $("#industry").val();
// 	    var authentication = $("#authentication").val();
// 	    var licensekey = $("#licensekey").val();
// 	    if(licensekey == ""){
// 	    	alert("请输入授权秘钥!");
// 	    	return false;
// 	    }


// 	    var month1 = $("#month1").val();
// 	    var starttime1 = $("#starttime1").val();
// 	    var month2 = $("#month2").val();
// 	    var starttime2 = $("#starttime2").val();
// 	    var endtime = $("#endtime").val();

		var projectid = $("#projectid").val().trim();
		
// 	    var softwareerpnumber = $("#softwareerpnumber").val();
	    var softwaretypeversion = $("#softwaretypeversion").val();

		
	    var data = {};
	    
	    data.softwareerpnumber = softwareerpnumber;
		data.softwaretypeversion = softwaretypeversion;
	    
	    data.proid = projectid;
		data.projectname = projectnameadd;
		data.projectregname = projectregname;
		data.projectadd = projectadd;
		data.cusprojectname = cusprojectname;
		data.cusprojectphone = cusprojectphone;
		data.demandquantity = demandquantity;
		data.requirementtype = requirementtype;
		data.industry = industry;
		data.authentication = authentication;
		data.sdkname = sdkname;
		data.licensekey = licensekey;
// 		data.month = month1;
// 		data.starttime = starttime1;
// 		data.month = month2;
// 		data.tacitstarttime = starttime2;
// 		data.endtime = endtime;
		var selectedIds = getCheckedRowValue("");
		var val=$('input:radio[name="choose"]:checked').val();
		var activitiTag = $('#activitiTag').val();
// 		 if(val == 1 ){
// // 			 var month1 = $("#month1").val();
// 			    var month1 = $("#month1").val().trim();
// 				var month1info = "月份";
// 				var month1length = 8;
// 				var month1flag = checkRedvalue(month1,month1info,month1length);
// 				if(month1flag == false){
// 					var month1Input = document.getElementById("month1");
// 					month1Input.focus();
// 					return month1flag;
// 				}
// 			 data.month = month1;
// 			 data.starttime = starttime1;
// 		  }
// 		 else if(val == 2){
// 			    var month2 = $("#month2").val().trim();
// 				var month2info = "月份";
// 				var month2length = 8;
// 				var month2flag = checkRedvalue(month2,month2info,month2length);
// 				if(month2flag == false){
// 					var month2Input = document.getElementById("month2");
// 					month2Input.focus();
// 					return month2flag;
// 				}
// 			 data.month = month2;
// 			 data.tacitstarttime = starttime2;
// 		 }
// 		 else if(val == 3){
// 			 data.endtime = endtime;
// 		 }
// 		 else{
// 			 alert("请选择一种有效期!");
// 			 return false;
// 		 }

// 	    data.id = selectedIds;

	    var checkedNodetid = $('#hiddenInput').val();
	    if(checkedNodetid != "" && checkedNodetid != "tree_1"){
// 	    	var treeObj = $.fn.zTree.getZTreeObj("tree");
// 		 	var node_tem = treeObj.getNodeByTId(checkedNodetid);
// 		 	if(node_tem.id > 0 ){
// 	 			data.companyid = node_tem.id;
// 	 		}
		var companyid = $('#treeNodeid').val();
			if(companyid>0){
				data.companyid = companyid;
			}
		 	else{
		 		return false;
		 	}
	    }
 	//	var nodes = zTree.getSelectedNodes();
 			
	var url = "${ctx}/project/save.do?activitiTag=" + activitiTag;

		$.ajax({ 
			type        : "POST"
			,url         : url
			,data        : data
			,contentType : "application/x-www-form-urlencoded;charset=utf-8;"
			,dataType    : "json"
			,cache		  : false	
			,success: function(info) {
				
				if (info.code == 1) {
				//	var obj = eval('(' + info.msg + ')');
					//var node =  info.msg;
					//zTree.addNodes(nodes[0], obj);
					$('#myDlgContent').text('保存成功，若该项目还未分配客户商务人员和项目管理人员，请前往用户管理进行分配');
					$('#myModal').on('hidden.bs.modal', function (e) {
						gotourl('${ctx}/project/list.do?page=${page}');

					});
					$('#myModal').modal('show');
				//	zTree.cancelSelectedNode(nodes[0]);
				//    $('#myform').modal('hide');
				//	gotourl('${ctx}/project/list.do?page=${page}');
					
				}
				else if (info.code == 0) {
					alert(info.msg);
				}
				else if (info.code == -1) {
					alert("新增项目异常");
				}					
			}
			,error : function(XMLHttpRequest, textStatus, errorThrown) {    
				alert(XMLHttpRequest.status + textStatus);    
			} 
			
		}
		);

    
}

function disableProject(){
	
	
	var  num = getCheckedRowValue("");
	var  selectedIds=""; 
	//将字符串以空格分开
	var strArr = num.split(' ');
	for(var i=0,len=strArr.length;i<len;i++){
        if(!isNaN(parseInt(strArr[i]))){
        	if(selectedIds == "") {
        		selectedIds += strArr[i];
        	}
        	else{
        		selectedIds += ",";
        		selectedIds += strArr[i];
        	}
        }
    }	

// 	var selectedIds = getCheckedRowValue("");
		if(selectedIds == ""){
		alert("请至少选择一个项目进行关闭");
		return false;
	}
	if(confirm("确认关闭该项目吗？")){
		var querydata ={};
		querydata.id = selectedIds;
		$.ajax({
			type        : "POST"
			,url         : "${ctx}/project/disableProject.do"
			,data        : querydata
			,contentType : "application/x-www-form-urlencoded;charset=utf-8;"
			,dataType    : "json"
			,cache		  : false	
			,success: function(info){
				if (info.code == 1) {
					alert("关闭项目成功");
					gotourl('${ctx}/project/list.do?page=${1}');
				}
				else if (info.code == 0) {
					alert(info.msg);
				}
				else if (info.code == -1) {
					alert("关闭项目异常");
				}	
			}
		,error : function(XMLHttpRequest, textStatus, errorThrown) {    
			alert(XMLHttpRequest.status + textStatus);    
		} 	
		}
				
			);
	}
	
}



// function beforeRename(treeId, treeNode, newName, isCancel) {
// 	var flag = false;
// 	if(newName.length ==0){
// 		alert("请输入分组名称!");
// 		return flag;
// 	}
	
// 	var data = {};
// 	data.id = treeNode.id;
// 	data.name = newName;
// 	data.pid = treeNode.pid;
// 	$.ajax({ 
// 		type        : "POST"
// 		,url         :"${ctx}/group/save.do"
// 		,data        : data
// 		,contentType : "application/x-www-form-urlencoded;charset=utf-8;"
// 		,dataType    : "json"
// 		,cache		  : false	
// 		,async:false
// 		,success: function(info) {
			
// 			if (info.code == 1) {
// 				flag = true;
// 			}
// 			else if (info.code == 0) {
// 				alert(info.msg);
// 			}
// 			else if (info.code == -1) {
// 				alert("保存异常!");
// 			}					
// 		}
// 		,error : function(XMLHttpRequest, textStatus, errorThrown) {    
// 			alert(XMLHttpRequest.status + textStatus);    
// 		} 
// 	}
// 	);
	
// 	return flag;
// }

// function delGroup(treeId, treeNode){
// 	var flag = false;
// 	if(confirm("确认删除吗?")) {
// 		var querydata = {};
// 		querydata.id = treeNode.id;
// 		$.ajax({ 
// 			type        : "POST"
// 			,url         :"${ctx}/group/delete.do"
// 			,data        : querydata
// 			,contentType : "application/x-www-form-urlencoded;charset=utf-8;"
// 			,dataType    : "json"
// 			,cache		  : false	
// 			 ,async:false
// 			,success: function(info) {
				
// 				if (info.code == 1) {
// 					alert("删除成功!");
// 					//$("#queryform").submit();
// 					//document.location.href = jumpUrl;
// 					//zTree.removeNode(treeNode, false);
// 					flag = true;
// 				}
// 				else if (info.code == 0) {
// 					alert(info.msg);
					
// 				}
// 				else if (info.code == -1) {
// 					alert("删除异常!");
// 				}					
// 			}
// 			,error : function(XMLHttpRequest, textStatus, errorThrown) {    
// 				alert(XMLHttpRequest.status + textStatus);    
// 			} 	
// 		}
// 		);
// 	}
	
// 	return flag;

	
	
	
// }





//-->
</SCRIPT>	
<%@ include file="/WEB-INF/views/common/myModal.jsp" %>	
<script src="${ctx}/js/jquery.ztree.all-3.5.min.js"></script>	
<%@ include file="/WEB-INF/views/foot_n.jsp" %>

