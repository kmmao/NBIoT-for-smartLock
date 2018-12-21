<%@page import="com.routon.plcloud.common.decorator.PageCheckboxDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@ include file="/WEB-INF/views/head_n.jsp" %>
<div class="panel panel-default">
  		<div class="panel-heading" style="width:1528px;height:48px;">
  		<div class="pull-right" >
  			<div class="btn-group">
  					<button type="button" class="btn btn-primary" onclick="sendmanager()">下发管理员</button>
  			</div>
  			<div class="btn-group">
  					<button type="button" class="btn btn-primary" onclick="delmanager()">删除管理员</button>
  			</div>
  		</div>
  			<h5><strong>设备注册信息管理</strong></h5>
  		</div>
  		<div class="panel-body" style="padding-top: 0px;padding-left: 0px;padding-right: 0px;padding-bottom: 0px;">
		<display:table name="requestScope.pageList" id="curPage" class="table table-striped" sort="external"
			requestURI="registerInfo.do"
			decorator="com.routon.plcloud.common.decorator.PageDecorator"
			export="false">
			<display:column property="face_id" title="<%=PageCheckboxDecorator.getTitle(pageContext)%>" decorator="com.routon.plcloud.common.decorator.PageCheckboxDecorator" media="html" style="width:2%;"/>
			<display:column title="人员ID"  property="face_id"  sortable="true"  style="width:5%;" />
			<display:column title="姓名" sortable="true"  property="name" style="width:5%;"></display:column>
			<display:column title="性别"  property="gender"  sortable="true"  style="width:5%;"></display:column>
			<display:column title="地址"  property="addr"  sortable="true"  style="width:20%;"></display:column>
			<display:column title="绑定的卡ID"  property="idcard_no"  sortable="true"  style="width:20%;"></display:column>
			<display:column title="注册时间"  property="update_time"  sortable="true"  style="width:15%;"></display:column>	
			<%-- <display:column title="用户角色"  property="role"  sortable="true"  style="width:5%;"></display:column> --%>	
			<display:column title="设备id"  property="device_id"  sortable="true"  style="width:20%;"></display:column>
			<display:column title="照片"  property="image_url"  sortable="true"  style="width:20%;"></display:column>
		</display:table>
		</div>
		<!-- <div class="modal fade" id="sendModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" aria-hidden="true">
  			<div class="modal-dialog" style="width: 450px;">
  				<div class="modal-content">
  					<div class="modal-header">
                		<h6 class="modal-title" id="myModalLabel">下发管理员</h6>
            		</div>
            		<div class="modal-body">
            		<form id="form00" name="form00" action=""  method="post" >
            			<div class="form-group">
            				<label for="title" class="col-sm-4 control-label">设备ID：</label>
            				<select id="deviceid" name="deviceid" class="form-control" onchange="selectDeviceId(this)">
			      				<option value="1">―请选择设备ID―</option>  
				  			</select>
            			</div>
            			<div class="form-group">
            				<label for="title" class="col-sm-4 control-label">设备ID：</label>
            				<select id="deviceid" name="deviceid" class="form-control" onchange="selectDeviceId(this)">
			      				<option value="1">―请选择人员―</option>  
				  			</select>
            			</div>
            
            		</form>
            			
            			
            			<div id="commandDiv2" style="margin-bottom: 10px;font-size:15px"><font id="commandFont2" color="red"></font></div>
            		</div>
            <div class="modal-footer">
                <button id="closeButton" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" id="send0" name="send" class="btn btn-primary" onclick="" >下发管理员</button>
            </div>
  				</div>
  			</div>
  		</div> -->
		<%@ include file="/WEB-INF/views/common/pagination.jsp" %>
</div>
	<link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.css">
	<script src="${ctx}/js/bootstrap-datetimepicker.js"></script>
	<script src="${ctx}/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="${ctx}/js/jquery-easyui/jquery.easyui.min.js"></script>	
	<script src="${ctx}/js/common.js"></script>
	<script src="${ctx}/js/bootstrap-dialog.min.js"></script>
	<script src="${ctx}/js/fileinput.min.js"></script>
	<%@ include file="/WEB-INF/views/common/myModal.jsp" %>
<script>
function sendmanager(){
	var selectedIds = getCheckedRowValue("");
	if(selectedIds==""){
		alert("请至少选择一个进行下发！");
		return false;
	}
    send(selectedIds,'${ctx}/nbiot/sendmanager.do', g_ctx + '/nbiot/registerInfo.do');
}

function delmanager(){
	var selectedIds = getCheckedRowValue("");
	if(selectedIds==""){
		alert("请至少选择一个进行下发！");
		return false;
	}
    send(selectedIds,'${ctx}/nbiot/sendmanager.do?delTag=1', g_ctx + '/nbiot/registerInfo.do');
}

function send(id, sendUrl, jumpUrl){
	if(confirm("确认下发吗?")) {
		var querydata = {};
		querydata.id = id;
		$.ajax({ 
			type        : "POST"
			,url         : sendUrl
			,data        : querydata
			,contentType : "application/x-www-form-urlencoded;charset=utf-8;"
			,dataType    : "json"
			,cache		  : false	
			,success: function(info) {
				
				if (info.code == 1) {
					alert("下发成功!");
					//$("#queryform").submit();
					document.location.href = jumpUrl;
					
				}
				else if (info.code == 0) {
					alert(info.msg);
					document.location.href = jumpUrl;
				}
				else if (info.code == -1) {
					alert("下发异常!");
				}					
			}
			,error : function(XMLHttpRequest, textStatus, errorThrown) {    
	            
		        alert(XMLHttpRequest.status + textStatus);    
			} 
		}
		);
	}
}
</script>

