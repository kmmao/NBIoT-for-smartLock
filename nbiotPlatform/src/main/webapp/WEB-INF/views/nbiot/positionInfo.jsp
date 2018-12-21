<%@page import="com.routon.plcloud.common.decorator.PageCheckboxDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@ include file="/WEB-INF/views/head_n.jsp" %>
<div class="panel panel-default">

  		<div class="panel-heading" style="width:1528px;height:48px;">
  		<div class="" style="display: inline-block;width: 100%;">
  		
  		<div class="pull-right" >
  			
  			<div class="btn-group">
  					<button type="button" class="btn btn-primary" onclick="editposinfo(this)">新增位置</button>
  			</div>
  			<div class="btn-group">
  					<button type="button" class="btn btn-primary" onclick="delposition()">删除位置</button>
  			</div>
  			<%-- <div class="btn-group">
  					<button type="button" class="btn btn-primary" onclick="edit1(this)">修改位置</button>
  			</div> --%>
  		</div>
  		<h5><strong>位置管理</strong></h5>
  			</div>
  		</div>
  		
  		<div class="panel-body" style="padding-top: 0px;padding-left: 0px;padding-right: 0px;padding-bottom: 0px;">
		<display:table name="requestScope.pageList" id="curPage" class="table table-striped" sort="external"
			requestURI="positionInfo.do"
			decorator="com.routon.plcloud.common.decorator.PagePositionDecorator"
			export="false">
			<display:column property="id" title="<%=PageCheckboxDecorator.getTitle(pageContext)%>" decorator="com.routon.plcloud.common.decorator.PageCheckboxDecorator" media="html" style="width:2%;"/>
			
			<display:column title="设备id"  property="deviceid"  sortable="true"  style="width:25%;" />
			<display:column title="地址" sortable="true"  property="addr" style="width:15%;"></display:column>
			<display:column title="房间号"  property="roomnum"  sortable="true"  style="width:10%;"></display:column>
			<display:column title="业主姓名"  property="name"  sortable="true"  style="width:10%;"></display:column>
			<display:column title="手机号"  property="phone"  sortable="true"  style="width:10%;"></display:column>
			<display:column title="管辖机构"  property="jurisdiction"  sortable="true"  style="width:15%;"></display:column>	
			<display:column title="管辖机构联系方式"  property="jurisdictioncon"  sortable="true"  style="width:25%;"></display:column>
		</display:table>
		</div>
		<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" aria-hidden="true">
  			<div class="modal-dialog" style="width: 450px;">
  				<div class="modal-content">
  					<div class="modal-header">
                		<h6 class="modal-title" id="myModalLabel">新增位置</h6>
            		</div>
            		<div class="modal-body">
            		<form id="form00" name="form00" action="${ctx}/nbiot/addPositionInfo.do"  method="post" >
            			<div class="form-group">
            				<label for="title" class="col-sm-4 control-label">设备ID：</label>
            				<select id="deviceid" name="deviceid" class="form-control" onchange="selectDeviceId(this)">
			      				<option value="1">―请选择设备ID―</option>  
				  			</select>
            			</div>
            			<div class="form-group">
            				<label for="title" class="col-sm-4 control-label">地址：</label>
            				<input id="addr" name="addr" type="text" class="form-control" placeholder="请输入地址" >
            			</div>
            			<div class="form-group">
            				<label for="title" class="col-sm-5 control-label">房间号：</label>
            				<input id="roomnum" name="roomnum" type="text" class="form-control" placeholder="请输入房间号" >
            			</div>
            			<div class="form-group">
            				<label for="title" class="col-sm-5 control-label">业主姓名：</label>
            				<input id="name" name="name" type="text" class="form-control" placeholder="请输入姓名" >
            			</div>
            			<div class="form-group">
            				<label for="title" class="col-sm-5 control-label">手机号：</label>
            				<input id="phone" name="phone" type="text" class="form-control" placeholder="请输入手机号" >
            			</div>
            			<div class="form-group">
            				<label for="title" class="col-sm-5 control-label">管辖机构：</label>
            				<input id="jurisdiction" name="jurisdiction" type="text" class="form-control" placeholder="请输入管辖机构">
            			</div>
            			<div class="form-group">
            				<label for="title" class="col-sm-5 control-label">管辖机构联系方式：</label>
            				<input id="jurisdictioncon" name="jurisdictioncon" type="text" class="form-control" placeholder="请输入管辖机构联系方式">
            			</div>
            		</form>
            			
            			
            			<div id="commandDiv2" style="margin-bottom: 10px;font-size:15px"><font id="commandFont2" color="red"></font></div>
            		</div>
            <div class="modal-footer">
                <button id="closeButton" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" id="send0" name="send" class="btn btn-primary" onclick="commandSubmit(0)" >确定</button>
            </div>
  				</div>
  			</div>
  		</div>
  		
   	<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" aria-hidden="true">
  			<div class="modal-dialog" style="width: 450px;">
  				<div class="modal-content">
  					<div class="modal-header">
                		<h6 class="modal-title" id="myModalLabel">修改位置</h6>
            		</div>
            		<div class="modal-body">
            		<form id="form01" name="form01" action="${ctx}/nbiot/updatePositionInfo.do"  method="post">
            			<div class="form-group">
            				<label for="title" class="col-sm-4 control-label">设备ID：</label>
            				<input id="deviceid1" name="deviceid1" type="text" class="form-control" placeholder="请输入地址" >
            			</div>
            			<div class="form-group">
            				<label for="title" class="col-sm-4 control-label">地址：</label>
            				<input id="addr1" name="addr1" type="text" class="form-control" placeholder="请输入地址" >
            			</div>
            			<div class="form-group">
            				<label for="title" class="col-sm-5 control-label">房间号：</label>
            				<input id="roomnum1" name="roomnum1" type="text" class="form-control" placeholder="请输入房间号" >
            			</div>
            			<div class="form-group">
            				<label for="title" class="col-sm-5 control-label">业主姓名：</label>
            				<input id="name1" name="name1" type="text" class="form-control" placeholder="请输入姓名" >
            			</div>
            			<div class="form-group">
            				<label for="title" class="col-sm-5 control-label">手机号：</label>
            				<input id="phone1" name="phone1" type="text" class="form-control" placeholder="请输入手机号" >
            			</div>
            			<div class="form-group">
            				<label for="title" class="col-sm-5 control-label">管辖机构：</label>
            				<input id="jurisdiction1" name="jurisdiction1" type="text" class="form-control" placeholder="请输入管辖机构">
            			</div>
            			<div class="form-group">
            				<label for="title" class="col-sm-5 control-label">管辖机构联系方式：</label>
            				<input id="jurisdictioncon1" name="jurisdictioncon1" type="text" class="form-control" placeholder="请输入管辖机构联系方式">
            			</div>
            		</form>
            			
            			
            			<div id="commandDiv2" style="margin-bottom: 10px;font-size:15px"><font id="commandFont2" color="red"></font></div>
            		</div>
            <div class="modal-footer">
                <button id="closeButton" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" id="send0" name="send" class="btn btn-primary" onclick="commandSubmit1()">修改</button>
            </div>
  				</div>
  			</div>
  		</div>
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
$(document).ready(function(){
	$.ajax({ 
		type        : "POST",
		url         : "${base}nbiot/deviceIdInfo.do",
		data        :  {},
		contentType : "application/x-www-form-urlencoded;charset=utf-8;",
		dataType    : "json",
		cache		: false,
		success: function(info) {
			var data = info;
			//$("#personId3").html("");
            $.each(data, function(index, value){
                $("#deviceid").append("<option value=\""+data[index]+"\">"+data[index]+"</option>");
                //$("#deviceid1").append("<option value=\""+data[index]+"\">"+data[index]+"</option>");
            });
		}
	});
});

function editposinfo(obj){
	$('#addModal').modal('show');

}
/* function edit1(obj){
	$('#updateModal').modal('show');

} */

function commandSubmit(obj){
	//$("#form0"+obj).submit();
	//window.location.reload()
	save('#form00', '${base}nbiot/addPositionInfo.do', '${base}nbiot/positionInfo.do');
}

function commandSubmit1(obj){
	//$("#form0"+obj).submit();
	//window.location.reload()
	save('#form01', '${base}nbiot/updatePositionInfo.do', '${base}nbiot/positionInfo.do');
}

function delposition(){
	var selectedIds = getCheckedRowValue("");
	if(selectedIds==""){
		alert("请至少选择一个进行删除");
		return false;
	}
	/* var selectedId = selectedIds.split(",");
	if (selectedId.length != 1) {
		alert("请选择一个进行删除!");
		return false;
	} */
	del(selectedIds,'${ctx}/nbiot/delPositionInfo.do', g_ctx + '/nbiot/positionInfo.do');
}

function editPosition(obj){
		var deviceid = $(obj).parents('tr').children('td').eq(1).text();
		edit2(deviceid);
}

 function edit2(type){
	if(type!=null){
		var selectedIds = type;
	}
		$('#updateModal').modal('show');
		//	document.getElementById("realname").readOnly = true;
		 document.getElementById("deviceid1").readOnly = true;
		
		//gotourl('${ctx}/user/querybyId.do?page=${page}&id='+selectedIds);
		$.ajax({
			url:'${base}nbiot/selectById.do',
			type:'post',
			data:{ id : selectedIds },
			async:false,
			success:function(str){
				
				$("#deviceid1").val(str.deviceid);
				$("#addr1").val(str.addr);
				$("#roomnum1").val(str.roomnum);
				$("#name1").val(str.name);
				$("#phone1").val(str.phone);
				$("#jurisdiction1").val(str.jurisdiction);
		
				$("#jurisdictioncon1").val(str.jurisdictioncon);
			}
		});
}
</script>
