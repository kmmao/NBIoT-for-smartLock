<%@ page import="com.routon.plcloud.common.decorator.PageCheckboxDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ include file="/WEB-INF/views/head_n.jsp" %>
<%@ include file="/WEB-INF/views/activiti/RuntimeProcess.jsp" %>

<link rel="stylesheet" href="${ctx}/css/zTreeStyle.css">

<div style="
    width: 1520px;
    padding-left: 0px;
    margin-left: 0px;
    margin-top: 50px;
">
	<div class="panel panel-default"  style="width: 1530px;">
		<div class="panel-heading " style="width:1528px;height:48px;">
  			<div class="" style="display: inline-block;width: 100%;">
  			
	  			<div class=" pull-right">
		  			<form class="form-inline" role="form" id="queryform" name="queryform" action="${ctx}/role/list.do"  method="post">  		
<!-- 		  				<div class="btn-group" style="margin-top: 5px;margin-bottom: 5px;"> -->
<%-- 		  					<input size="20" type="text" id="rolename" name="rolename" value="${rolename}" class="form-control" placeholder="请输入角色名称"> --%>
<!-- 		  				</div> -->
<!-- 		  				<div class="btn-group"> -->
<!-- 		  					<button id="queryBtn" type="submit" class="btn btn-primary" >查询</button> -->
<!-- 		  				</div> -->
		  			<div class="btn-group">
		  			  <c:if test="${(!empty userPrivilege['40000201'])}">
		  				<button id="newBtn" type="button" class="btn btn-primary" data-toggle="modal" data-target="#RoleModal" onclick="newRole()">新增</button>
		  			  </c:if>
		  			</div>
		  			
		  			  <%-- <c:if test="${(!empty userPrivilege['40000202'])}">
		  				<button id= "editBtn" type="button" class="btn btn-primary" value="edit" onclick="edit(this)">编辑</button>
		  			  </c:if> --%>
		  			  
		  			  <c:choose>
   								<c:when test="${(!empty userPrivilege['40000202'])}"> 
   									<button id= "editBtn" type="button" class="btn btn-primary" value="edit" onclick="edit(this)">编辑</button>   
   								</c:when>
   								<c:otherwise> 
   								 	<c:if test="${(!empty userPrivilege['40000200'])}">
		  								<button id= "queryBtn" type="button" class="btn btn-primary" value="query" onclick="edit(this)">角色详情</button>
		  			  				</c:if> 
   								</c:otherwise>
					 </c:choose>
					
		  			  <%-- <c:if test="${(!empty userPrivilege['40000200'])}">
		  				<button id= "queryBtn" type="button" class="btn btn-primary" value="query" onclick="edit(this)">角色详情</button>
		  			  </c:if> --%>
		  			  
		  			  <c:if test="${(!empty userPrivilege['40000203'])}">
		  				<button id= "deleteBtn" type="button" class="btn btn-danger" onclick="delRole()">删除</button>
		  			  </c:if>
		  			</form>
	  			</div>
	  			 <h5>系统    > 角色管理 </h5>
  			</div> 
  		</div>
  		
  		<div class = "panel-body" style ="height : 64px;">
  		  <div class="" style="display: inline-block;width: 100%;">
  			<div class="btn-group" style ="height : 34px;">
  				<form class="form-inline" role="form" id="queryform" name="queryform" action="${ctx}/role/list.do"  method="post">  
  					<%-- <div class="btn-group" style="margin-top: 5px;margin-bottom: 5px;">
		  					<input size="20" type="text" id="rolename" name="rolename" value="${rolename}" class="form-control" placeholder="请输入角色名称">
		  				</div> --%>
		  				
		  					<!-- <button id="queryBtn" type="submit" class="btn btn-primary" >查询</button> -->
		  					<c:if test="${(!empty userPrivilege['40000200'])}">
		  					<div class="btn-group" style="margin-top: 5px;margin-bottom: 5px;">
		  						<input size="20" type="text" id="rolename" name="rolename" value="${rolename1}" class="form-control" placeholder="请输入角色名称" onkeyup="this.value=this.value.replace(/[^\uFF00-\uFFFF\u4e00-\u9fa5\w\@\&\-]/g,'')">
		  					</div>
		  					<div class="btn-group">
		  						<button id="queryBtn" type="submit" class="btn btn-primary" >查询</button>
		  					</div>
		  			  		</c:if>
		  				
  				</form>
  			</div>
  			</div>
  		</div>
  		
		<display:table name="${pageList}" id="curPage" class="table table-striped" sort="external"
			requestURI="list.do"
			export="false">
			<display:column property="id" title="<%=PageCheckboxDecorator.getTitle(pageContext)%>" decorator="com.routon.plcloud.common.decorator.PageCheckboxDecorator"  style="width:2%;"/>
			<display:column title="ID"  property="id"  sortable="true"  style="width:5%;" />
			<%-- <display:column title="序号" sortable="true" style="width:5%;" >
				<c:out value="${curPage_rowNum}"/>
			</display:column> --%>
			<display:column title="角色名称" property="name" sortable="true" style="width:5%;" ></display:column>
			<display:column title="创建时间"  property="createTime"  sortable="true"  style="width:5%;" maxLength="50" decorator="com.routon.plcloud.common.decorator.PageDateTimeDecorator"></display:column>
			<display:column title="修改时间"  property="modifyTime" sortable="true"  style="width:5%;" decorator="com.routon.plcloud.common.decorator.PageDateTimeDecorator"></display:column>
		</display:table>
</div>
<%@ include file="/WEB-INF/views/common/pagination.jsp" %>
<!-- 模态框（Modal） -->
<div class="modal fade" id="RoleModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" aria-hidden="true">
    <div class="modal-dialog" style="width: 400px;">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">角色类型</h4>
            </div>
            <div class="modal-body">
            	<form  class="form-horizontal" role="form" id="roleForm" name="roleForm" method='post' action='${ctx}/role/add.do'>
<!--             		<div class="form-group"> -->
            			<input id="updateId" name="updateId" type="hidden">
            			<input id="menuIds" name="menuIds" type="hidden" value="${role.menuIds}" >
            			
            			<div class="form-group" style="margin-right: 0px;">
 								<label for="title" class="col-sm-3 control-label"  style="padding-left: 5px;padding-right: 5px;color:red;">角色名称:</label>		
 							<div class="col-sm-9" style="padding-left: 0px">					
							  	<input type="text" class="form-control" id="name" name="name" placeholder="长度不超过25个字符" onkeyup="this.value=this.value.replace(/[^\uFF00-\uFFFF\u4e00-\u9fa5\w\@\&\-]/g,'')">  
						   </div>
						</div>
            			
<!--     					<label for="name">角色名称</label> -->
<!--     					<input type="text" class="form-control" id="name" name="name"> -->
<!--     					<input type="text" class="form-control" id="name" name="name" placeholder="角色名称"> -->
    					
    					<div class="form-group" style="margin-right: 0px;">
 								<label for="title" class="col-sm-3 control-label"  style="padding-left: 5px;padding-right: 5px;color:red;">权限菜单:</label>		
 							<div class="col-sm-9 input-group" style="padding-left: 0px">					
							  	<input type="text" class="form-control" id="menuNames" name="menuNames" readonly placeholder="请选择权限菜单" value="${role.menuNames}">
					    		<span class="input-group-btn " >
					    		<button class="btn btn-default" type="button" data-toggle="modal" data-target="#mytree">选择<span class="caret"></span></button>  
						  		 </span>  
						   </div>
						</div>
						
						 <div class="form-group" style="margin-right: 0px;">
					  		<label for="title" class="col-sm-3 control-label"  style="padding-left: 5px;padding-right: 5px;color:red;">角色类型:</label>
					  		<div class="col-sm-9 input-group" style="padding-left: 0px">
					  		 <select id="status" name="status" class="form-control" >			    
					      		<option value="我方" <c:if test="${company.status=='我方'}">selected="selected"</c:if> >我方</option>
								<option value="客户" <c:if test="${company.status=='客户'}">selected="selected"</c:if> >客户</option>
							 </select>
						   </div>
					    </div>
    					
<!--     					<label for="name">权限菜单</label> -->
<!--     					<input type="text" class="form-control" id="menuNames" name="menuNames"> -->
<%--     					<input type="text" class="form-control" id="menuNames" name="menuNames" readonly placeholder="请选择权限菜单" value="${role.menuNames}"> --%>
<!-- 					    <button class="btn btn-default" type="button" data-toggle="modal" data-target="#mytree">选择<span class="caret"></span></button> -->
					      
					     <div class="form-group" style="margin-right: 0px;">
 								<label for="title" class="col-sm-3 control-label"  style="padding-left: 5px;padding-right: 5px;">备注:</label>		
 							<div class="col-sm-9" style="padding-left: 0px">					
							  	<textarea id="remark" name="remark" class="form-control" rows="5" placeholder="长度不超过512个字符"></textarea>
						   </div>
						</div> 
					      
					      
<!--     					<label for="name">备注:</label> -->
<!--     					<textarea id="remark" name="remark" class="form-control" rows="5"></textarea> -->
<!--     					<textarea id="remark" name="remark" class="form-control" rows="5"></textarea> -->
<!--   					</div> -->
            	</form>
			</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" onclick = "JudgesubmitForm()">关闭</button>
                <button type="button" id="addsub" name="addsub" class="btn btn-primary" onclick="saveRole()">保存</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="mytree" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog  modal-sm">
    <div class="modal-content" style="width: 300px;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">选择菜单</h4>
      </div>
      <div class="modal-body">
        <ul id="treeDemo" class="ztree"></ul>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="select()">选择</button>
      </div>
    </div>
  </div>
</div>

</div>

<SCRIPT type="text/javascript">

		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};

		var zNodes =${menuTreeBeans};
		
		
		var zTree;
		$(document).ready(function(){
			zTree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
			
			
		});
		
		function select(){
			var nodes = zTree.getCheckedNodes(true);
			var ids = "";
			var menuNames = "";
			for(var i=0;i<nodes.length;i++){
				if(ids==""){
					ids = nodes[i].id;
					menuNames = nodes[i].name;
				}else {
					ids +=",";
					ids +=nodes[i].id;
					
					menuNames +=",";
					menuNames +=nodes[i].name;
				}
				
			}
			
			$("#menuIds").val(ids);
			$("#menuNames").val(menuNames);
			$('#mytree').modal('hide')
		}
	
	</SCRIPT>	


<script>

//返回时判断是否有修改
function JudgesubmitForm(){
	var change = 0;
    var form = document.getElementById('roleForm');
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
    		gotourl('${ctx}/role/list.do?page=${page}');
    	}
    }
    else{
    	gotourl('${ctx}/role/list.do?page=${page}');
    }
}


//检查字符串实际长度
/* function Reallength(str){
	var realLength = 0, len = str.length, charCode = -1;  
    for ( var i = 0; i < len; i++) {  
        charCode = str.charCodeAt(i);  
        if (charCode >= 0 && charCode <= 128)  
            realLength += 1;  
        else  
            realLength += 2;  
    }  
    return realLength; 
} */
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
	return totalLength; 
} 


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
	if(checkValue == ""){
		alert("请输入"+inputname);
		return flag;
	}
	else if(Reallength(checkValue) > length){
		alert("您输入的" + inputname + "长度超过"+length+"，请重新输入");
		return flag;
	}
}

	function saveRole(){
		var name = $("#name").val().trim();
		var nameinfo = "角色名称";
		var namelength = 25;
		var nameflag = checkRedvalue(name,nameinfo,namelength);
		if(nameflag == false){
			var nameinput = document.getElementById("name");
			nameinput.focus();
			return nameflag;
		}
		
		var menuNames = $("#menuNames").val();
		if(menuNames == ""){
			alert("请选择权限菜单");
			var menuNamesinput = document.getElementById("menuNames");
			menuNamesinput.focus();
			return false;
		}
		
		var remark = $("#remark").val().trim();
		var remarkinfo = "备注";
		var remarklength = 512;
		var remarkflag = checkvalue(remark,remarkinfo,remarklength);
		if(remarkflag == false){
			var remarkinput = document.getElementById("remark");
			remarkinput.focus();
			return remarkflag;
		}
		
		
		save('#roleForm', '${base}role/add.do', '${base}role/list.do?page=${page}');
	}




	function newRole(){
		$("#name").val("");
		$("#menuNames").val("");
		$("#remark").val("");
		$("#status").val("");
	}

	
  
	function edit(bel){
		var result = bel.value;
		if(result == 'query'){
			var selectedIds = getCheckedRowValue("");
			if(selectedIds==""){
				alert("请选择一个进行查看");
				return false;
			}
			var selectedId = selectedIds.split(",");
			if (selectedId.length != 1) {
				alert("请选择一个进行查看");
				return false;
			}
			$('#addsub').attr('disabled','disabled');
		}else if(result == 'edit'){
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
		
		
		$('#RoleModal').modal('show');
		/* 回填input */
		$.ajax({
			url:'${base}role/querybyId.do',
			type:'post',
			data:{ id : selectedIds },
			async:false,
			success:function(data){
				$("#updateId").val(data.id);
				$("#name").val(data.name);
				$("#menuNames").val(data.menuNames);
				$("#menuIds").val(data.menuIds);
				$("#remark").val(data.remark);
				$("#status").val(data.status);
				var rolename = data.name;
				if(rolename == "超级管理员"){
					$('#name').attr('disabled','disabled');
				}
			}
		});
		/* 回填树是否checked */
		$.ajax({
			url:'${base}role/queryChecked.do',
			type:'post',
			data:{ id : selectedIds },
			async:false,
			success:function(data){
				
				var treeCheck = $.fn.zTree.getZTreeObj("treeDemo");
				
				for(var i = 0;i < data.length; i++) {
					treeCheck.checkNode(treeCheck.getNodeByParam("id", data[i], null), true, true);
				}
				
			}
		});

	}
	
	function delRole(){
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
		del(selectedIds,'${ctx}/role/delete.do', g_ctx + '/role/list.do?page=${1}');
	}
</script>
<script src="${ctx}/js/common.js"></script>
<script src="${ctx}/js/jquery.ztree.all-3.5.min.js"></script>
<%@ include file="/WEB-INF/views/common/myModal.jsp" %>
<%@ include file="/WEB-INF/views/foot_n.jsp" %>