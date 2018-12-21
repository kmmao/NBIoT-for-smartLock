<%@ page import="com.routon.plcloud.common.decorator.PageCheckboxDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ include file="/WEB-INF/views/head_n.jsp" %>
<%@ include file="/WEB-INF/views/activiti/RuntimeProcess.jsp" %>
<div style="
    width: 1520px;
    padding-left: 0px;
    margin-left: 0px;
    margin-top: 50px;
">
	<div class="panel panel-default" style="width: 1530px;">
		<div class="panel-heading " style="width:1528px;height:48px;">
  			<div class="" style="display: inline-block;width: 100%;">
  			
	  			<div class="pull-right">
		  			<form class="form-inline" role="form" id="queryform" name="queryform" action="${ctx}/operatingsystem/list.do"  method="post">  		
<!-- 		  				<div class="btn-group" style="margin-top: 5px;margin-bottom: 5px;"> -->
<%-- 		  					<input size="20" type="text" id="operatingsystemname" name="operatingsystemname" value="${operatingsystemname}" class="form-control" placeholder="请输入系统名称"> --%>
<!-- 		  				</div> -->
<!-- 		  				<div class="btn-group"> -->
<!-- 		  					<button id="queryBtn" type="submit" class="btn btn-primary" >查询</button> -->
<!-- 		  				</div> -->
		  			<div class="btn-group">
		  			  <c:if test="${(!empty userPrivilege['40000401'])}">
		  				<button id="newBtn" type="button" class="btn btn-primary" data-toggle="modal" data-target="#OpsystemModal" onclick="newOpsystem()">新增</button>
		  			  </c:if>	
		  			</div>
		  			  <c:if test="${(!empty userPrivilege['40000402'])}">
		  				<button id= "editBtn" type="button" class="btn btn-primary" onclick="edit()">编辑</button>
		  			  </c:if>
		  			  <c:if test="${(!empty userPrivilege['40000403'])}">
		  				<button id= "deleteBtn" type="button" class="btn btn-danger" onclick="delOpsystem()">删除</button>
		  			  </c:if>
		  			</form>
	  			</div>
	  			 <h5>系统   > 操作系统管理</h5>
  			</div> 
  		</div>
  		<div class="panel-body" style ="height : 64px;">
  			<div class="btn-group" style ="height : 34px;">
  			<form class="form-inline" role="form" id="queryform" name="queryform" action="${ctx}/operatingsystem/list.do"  method="post">  
  				<div class="btn-group" style="margin-top: 5px;margin-bottom: 5px;">
		  			<input size="20" type="text" id="operatingsystemname" name="operatingsystemname" value="${operatingsystemname}" class="form-control" placeholder="请输入系统名称" onkeyup="this.value=this.value.replace(/[^\uFF00-\uFFFF\u4e00-\u9fa5\w\@\&\-]/g,'')">
		  		</div>
		  		<div class="btn-group">
		  			<button id="queryBtn" type="submit" class="btn btn-primary" >查询</button>
		  		</div>
  			</form>
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
			<display:column title="系统名称" property="operatingsystemname" sortable="true" style="width:5%;" ></display:column>
			<display:column title="创建时间"  property="createTime"  sortable="true"  style="width:5%;" maxLength="50" decorator="com.routon.plcloud.common.decorator.PageDateTimeDecorator"></display:column>
			<display:column title="修改时间"  property="modityTime" sortable="true"  style="width:5%;" decorator="com.routon.plcloud.common.decorator.PageDateTimeDecorator"></display:column>
		</display:table>
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="OpsystemModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" aria-hidden="true">
    <div class="modal-dialog" style="width: 400px;">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">编辑操作系统</h4>
            </div>
            <div class="modal-body">
            	<form  class="form-horizontal" role="form" id="productForm" name="productForm" method='post' action='${ctx}/operatingsystem/add.do'>          		
            			<input id="updateId" name="updateId" type="hidden">
            		<div class="form-group" style="margin-right: 0px;">
    					<label for="name" class="col-sm-3 control-label"  style="padding-left: 5px;padding-right: 5px;color:red;">系统名称</label>
    					 <div class="col-sm-9" style="padding-left: 0px">	
    					<input type="text" class="form-control" id="opsystemName" name="opsystemName" placeholder="长度不超过25个字符" onkeyup="this.value=this.value.replace(/[^\uFF00-\uFFFF\u4e00-\u9fa5\w\@\&\-]/g,'')">
    					</div>
  					</div>
            	</form>
			</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" onclick="JudgesubmitForm()">关闭</button>
                <button type="button" class="btn btn-primary" onclick="saveOpsystem()">保存</button>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/views/common/pagination.jsp" %>
</div>
<script>

function JudgesubmitForm() {
	var change = 0;
    var form = document.getElementById('productForm');
    for (var i = 0; i < form.length; i++) {
        var element = form.elements[i];
        var type = element.type;
        if (type == "checkbox" || type == "radio") {
 
            if (element.checked != element.defaultChecked) {
               change = 1;
//                 console.log("没有选择单选或多选框");
            }
        }
        if (type == "hidden" || type == "password" || type == "text" || type == "textarea") {
 
            if (element.value != element.defaultValue) {
//                 alert("文本框被修改");
                change = 1;
//                 console.log("没有进行文本输入");
            }
        }
 
        if (type == "select-one" || type == "select-multiple") {
 
            for (var j = 0; j < element.options.length; j++) {
 
                if (element.options[j].selected != element.options[j].defaultSelected) {
//                     alert("下拉框被修改");
//                     console.log("没有选择下拉框");
                	change = 1;
                }
            }
        }
        if (type == "file") {
            if (element.value.length != 0) {
                Filechange = false;
//                 alert("已选择图片");
//                 console.log("没选择图片");
                change = 1;
            }
        }
    }
    if(change == 1){
    	if(confirm("您输入的信息未保存,确认退出吗?")){
    		gotourl('${ctx}/operatingsystem/list.do?page=${page}');
    	}
    }
    else{
    	gotourl('${ctx}/operatingsystem/list.do?page=${page}');
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

	function saveOpsystem(){
		var opsystemName = $("#opsystemName").val().trim();
		
		/* var opname = opsystemName.getBytes(); */
			
		if(opsystemName == ""){
			alert("请输入系统名称");
			var opsystemNameInput = document.getElementById("opsystemName");
			opsystemNameInput.focus();
			return false;
		}
		else if(Reallength(opsystemName) > 25){
			alert("您输入的系统名称长度超过"+25+"，请重新输入");
			var opsystemNameInput = document.getElementById("opsystemName");
			opsystemNameInput.focus();
			return false;
		}
		
		
		save('#productForm', '${base}operatingsystem/add.do', '${base}operatingsystem/list.do?page=${page}');
	}

	function newOpsystem(){
		$("#opsystemName").val("");
	}

	function edit(){
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
		$('#OpsystemModal').modal('show');
		$.ajax({
			url:'${base}operatingsystem/querybyId.do',
			type:'post',
			data:{ id : selectedIds },
			async:false,
			success:function(data){
				$("#updateId").val(data.id);
				$("#opsystemName").val(data.operatingsystemname);
			}
		});
	}
	
	function delOpsystem(){
		var selectedIds = getCheckedRowValue("");
		if(selectedIds==""){
			alert("请至少选择一个进行删除");
			return false;
		}
// 		var selectedId = selectedIds.split(",");
// 		if (selectedId.length != 1) {
// 			alert("请选择一个进行删除!");
// 			return false;
// 		}
		del(selectedIds,'${ctx}/operatingsystem/delete.do', g_ctx + '/operatingsystem/list.do?page=${1}');
	}
</script>
<script src="${ctx}/js/common.js"></script>
<%@ include file="/WEB-INF/views/common/myModal.jsp" %>
<%@ include file="/WEB-INF/views/foot_n.jsp" %>