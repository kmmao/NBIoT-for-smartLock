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
	  			<div class=" pull-right">
		  			<form class="form-inline" role="form" id="queryform" name="queryform" action="${ctx}/hardware/show.do"  method="post">  		
<!-- 		  				<div class="btn-group" style="margin-top: 5px;margin-bottom: 5px;"> -->
<%-- 		  					<input size="20" type="text" id="productName" name="productName" value="${productName}" class="form-control" placeholder="请输入外部硬件产品名称"> --%>
<!-- 		  				</div> -->
<!-- 		  				<div class="btn-group"> -->
<!-- 		  					<button id="queryBtn" type="submit" class="btn btn-primary" >查询</button> -->
<!-- 		  				</div> -->
		  			<div class="btn-group">
		  				<button id="newBtn" type="button" class="btn btn-primary" data-toggle="modal" data-target="#ProductModal" onclick="newProduct()">新增</button>
		  			</div>
		  				<button id= "editBtn" type="button" class="btn btn-primary" onclick="edit()">编辑</button>
		  				<button id= "deleteBtn" type="button" class="btn btn-danger" onclick="delProduct()">删除</button>
		  			</form>
	  			</div>
	  			 <h5>产品    > 硬件产品管理 </h5>
  			</div> 
  		</div>
  		
  		<div class = "panel-body" style ="height : 64px;">
  		  <div class="" style="display: inline-block;width: 100%;">
  			<div class="btn-group" style ="height : 34px;">
  				<form class="form-inline" role="form" id="queryform" name="queryform" action="${ctx}/hardware/show.do"  method="post">  
<!--   					<div class="btn-group" style="margin-top: 5px;margin-bottom: 5px;"> -->
<%-- 		  					<input size="20" type="text" id="rolename" name="rolename" value="${rolename}" class="form-control" placeholder="请输入角色名称"> --%>
<!-- 		  				</div> -->
<!-- 		  				<div class="btn-group"> -->
<!-- 		  					<button id="queryBtn" type="submit" class="btn btn-primary" >查询</button> -->
<!-- 		  				</div> -->
		  				<div class="btn-group" style="margin-top: 5px;margin-bottom: 5px;">
		  					<input size="20" type="text" id="hardwareProductName" name="hardwareProductName" value="${productName}" class="form-control" placeholder="请输入硬件产品名称">
		  				</div>
		  				<div class="btn-group">
		  					<button id="queryBtn" type="submit" class="btn btn-primary" >查询</button>
		  				</div>

  				</form>
  			</div>
  			</div>
  		</div>
  		
		<display:table name="${pageList}" id="curPage" class="table table-striped" sort="external"
			requestURI="show.do"
			decorator="com.routon.plcloud.common.decorator.PageLinkDecorator"
			export="false">
			<display:column property="id" title="<%=PageCheckboxDecorator.getTitle(pageContext)%>" decorator="com.routon.plcloud.common.decorator.PageCheckboxDecorator"  style="width:2%;"/>
<%-- 			<display:column title="序号" sortable="true" style="width:5%;" > --%>
<%-- 				<c:out value="${curPage_rowNum}"/> --%>
<%-- 			</display:column> --%>
			<display:column title="ID"  property="id"  sortable="true"  style="width:5%;" />	
			<display:column title="外部硬件产品名称" property="hardwareProductName" sortable="true" style="width:5%;" ></display:column>
			<display:column title="ERP编码" property="erpCode" sortable="true"  style="width:5%;" ></display:column>
			<display:column title="硬件版本" property="hardwareProductVersion" sortable="true"  style="width:5%;" ></display:column>
			<display:column title="硬件平台" property="hardwareStation" sortable="true"  style="width:5%;" ></display:column>
			<display:column title="系统版本" property="operateSystem" sortable="true" style="width:5%;" ></display:column>
			<display:column title="创建时间"  property="createtime"  sortable="true"  style="width:5%;" maxLength="50" decorator="com.routon.plcloud.common.decorator.PageDateTimeDecorator"></display:column>
			<display:column title="修改时间"  property="modifytime" sortable="true"  style="width:5%;" decorator="com.routon.plcloud.common.decorator.PageDateTimeDecorator"></display:column>
		</display:table>
</div>
<%@ include file="/WEB-INF/views/common/pagination.jsp" %>
<!-- 模态框（Modal） -->
<div class="modal fade" id="ProductModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" aria-hidden="true">
    <div class="modal-dialog" style="width: 420px;">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">产品类型</h4>
            </div>
            <div class="modal-body">
            	<form class="form-horizontal" role="form" id="productForm" name="productForm" method='post' action='${ctx}/hardware/add.do'>
            		
            			<input id="updateId" name="updateId" type="hidden">
            			
            			<div class="form-group" style="margin-right: 0px;">
 								<label for="title" class="col-sm-3 control-label"  style="padding-left: 5px;padding-right: 5px;color:red;">硬件产品名称:</label>		
 							<div class="col-sm-9" style="padding-left: 0px">					
							  	<input type="text" class="form-control" id="productNameTable" name="productNameTable" placeholder="硬件产品名称" >  
						   </div>
						</div>
            			
<!--     					<label for="name">外部硬件产品名称</label> -->
<!--     					<input type="text" class="form-control" id="productNameTable" name="productNameTable"> -->

						<div class="form-group" style="margin-right: 0px;">
 								<label for="title" class="col-sm-3 control-label"  style="padding-left: 5px;padding-right: 5px;color:red;">ERP编码:</label>		
 							<div class="col-sm-9" style="padding-left: 0px">					
							  	<input type="text" class="form-control" id="erpCode" name="erpCode" placeholder="ERP编码" >  
						   </div>
						</div>

<!--     					<label for="name">ERP编码</label> -->
<!--     					<input type="text" class="form-control" id="erpCode" name="erpCode"> -->

						<div class="form-group" style="margin-right: 0px;">
 								<label for="title" class="col-sm-3 control-label"  style="padding-left: 5px;padding-right: 5px;color:red;">硬件版本:</label>		
 							<div class="col-sm-9" style="padding-left: 0px">					
							  	<input type="text" class="form-control" id="hardwareVersion" name="hardwareVersion" placeholder="硬件版本" >  
						   </div>
						</div>

<!--     					<label for="name">硬件版本</label> -->
<!--     					<input type="text" class="form-control" id="hardwareVersion" name="hardwareVersion"> -->

						<div class="form-group" style="margin-right: 0px;">
 								<label for="title" class="col-sm-3 control-label"  style="padding-left: 5px;padding-right: 5px;color:red;">硬件平台:</label>		
 							<div class="col-sm-9" style="padding-left: 0px">					
							  	<select class="form-control" id="hardwarePlatform" name="hardwarePlatform">
<!-- 					      			<option value="" disabled selected = "selected">―请选择―</option> -->
					      			<option value="" >―请选择―</option>
					      			<c:forEach var="item" items="${hardwarelist}">
										<option value="${item}" selected="selected">${item}</option>
								    </c:forEach> 
					      		</select>
						   </div>
						</div>

<!--     					<label for="name">硬件平台</label> -->
<!--     					<select class="form-control" id="hardwarePlatform" name="hardwarePlatform"> -->
<!-- 			      			<option value="">―请选择―</option> -->
<%-- 			      			<c:forEach var="item" items="${hardwarelist}"> --%>
<%-- 								<option value="${item}">${item}</option> --%>
<%-- 						    </c:forEach>  --%>
<!-- 			      		</select> -->

						<div class="form-group" style="margin-right: 0px;">
 								<label for="title" class="col-sm-3 control-label"  style="padding-left: 5px;padding-right: 5px;color:red;">系统版本:</label>		
 							<div class="col-sm-9" style="padding-left: 0px">					
		    					<select class="form-control" id="systemVersion" name="systemVersion">
					      			<option value=""  >―请选择―</option>
					      			<c:forEach var="item" items="${systemVersionlist}">
										<option value="${item}">${item}</option>
								    </c:forEach> 
					      		</select>
						   </div>
						</div>

<!--     					<label for="name">系统版本</label> -->
<!--     					<select class="form-control" id="systemVersion" name="systemVersion"> -->
<!-- 			      			<option value="">―请选择―</option> -->
<%-- 			      			<c:forEach var="item" items="${systemVersionlist}"> --%>
<%-- 								<option value="${item}">${item}</option> --%>
<%-- 						    </c:forEach>  --%>
<!-- 			      		</select> -->

						<div class="form-group" style="margin-right: 0px;">
 								<label for="title" class=" control-label"  style="padding-left: 5px;padding-right: 5px;">请上传附件（外观、铭牌、产品规格说明书）:</label>		
 							
 							<div  style="padding-left: 100px">	
 							<br>				
							  	<input type="file"  id="inputfile" name="inputfile" >  
						   </div>
						</div>

<!--     					<label for="name">请上传附件（外观、铭牌、产品规格说明书）:</label> -->
<!--     					<input type="file" id="inputfile"> -->
  				
            	</form>
			</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" onclick = "JudgesubmitForm()">关闭</button>
                <button type="button" class="btn btn-primary" onclick="saveHardware()">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</div>
<script>
/* 	$("#option1").on("change",function(){
		alert('wangxiwei');
	}) */
	//检查必填项（为空、超过长度）
	function checkRedvalue(checkValue , inputname , length){
		var flag = false;
		if(checkValue == ""){
			alert("请输入"+inputname);
			return flag;
		}
		else if(checkValue.length > length){
			alert("您输入的" + inputname + "长度超过"+length+"，请重新输入");
			return flag;
		}
	}
	
	
	
	function saveHardware(){
		
		var productNameTable = $("#productNameTable").val().trim();
		var productNameTableinfo = "硬件产品名称";
		var productNameTablelength = 50;
		var productNameTableflag = checkRedvalue(productNameTable,productNameTableinfo,productNameTablelength);
		if(productNameTableflag == false){
			var productNameTableInput = document.getElementById("productNameTable");
			productNameTableInput.focus();
			return productNameTableflag;
		}
		
		var erpCode = $("#erpCode").val().trim();
		var erpCodeinfo = "ERP编码";
		var erpCodelength = 50;
		var erpCodeflag = checkRedvalue(erpCode,erpCodeinfo,erpCodelength);
		if(erpCodeflag == false){
			var erpCodeInput = document.getElementById("erpCode");
			erpCodeInput.focus();
			return erpCodeflag;
		}
		
		var hardwareVersion = $("#hardwareVersion").val().trim();
		var hardwareVersioninfo = "硬件版本";
		var hardwareVersionlength = 8;
		var hardwareVersionflag = checkRedvalue(hardwareVersion,hardwareVersioninfo,hardwareVersionlength);
		if(hardwareVersionflag == false){
			var hardwareVersionInput = document.getElementById("hardwareVersion");
			hardwareVersionInput.focus();
			return hardwareVersionflag;
		}
		
		var hardwarePlatform = $("#hardwarePlatform").val().trim();
		if(hardwarePlatform == ""){
			alert("请选择一个硬件平台");
			var hardwarePlatformInput = document.getElementById("hardwarePlatform");
			hardwarePlatformInput.focus();
			return false;
		}
		
		var systemVersion = $("#systemVersion").val().trim();
		if(systemVersion == ""){
			alert("请选择一个系统版本");
			var systemVersionInput = document.getElementById("systemVersion");
			systemVersionInput.focus();
			return false;
		}
	
		save('#productForm', '${base}hardware/add.do', '${base}hardware/show.do?page=${page}')
}
	
	
	function JudgesubmitForm(){
		var change = 0;
	    var form = document.getElementById('productForm');
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
	    		gotourl('${ctx}/hardware/show.do?page=${page}');
	    	}
	    }
	    else{
	    	gotourl('${ctx}/hardware/show.do?page=${page}');
	    }
	}
	
	
	function newProduct(){
		//document.getElementById("productForm").submit();
		$("#productNameTable").val("");
		$("#erpCode").val("");
		$("#hardwareVersion").val("");
		document.getElementById("hardwarePlatform").value = "";
		document.getElementById("systemVersion").value = "";
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
		$('#ProductModal').modal('show');
		$.ajax({
			url:'${base}hardware/querybyId.do',
			type:'post',
			data:{ id : selectedIds },
			async:false,
			success:function(data){
				$("#updateId").val(data.id);
				$("#productNameTable").val(data.hardwareProductName);
				$("#erpCode").val(data.erpCode);
				$("#hardwareVersion").val(data.hardwareProductVersion);
				document.getElementById("hardwarePlatform").value = data.hardwareStation;
				document.getElementById("systemVersion").value = data.operateSystem;
			}
		});
	}
	
	function delProduct(){
		var selectedIds = getCheckedRowValue("");
		if(selectedIds==""){
			alert("请选择一个进行删除");
			return false;
		}
// 		var selectedId = selectedIds.split(",");
// 		if (selectedId.length != 1) {
// 			alert("请选择一个进行编辑!");
// 			return false;
// 		}
		del(selectedIds,'${ctx}/hardware/delete.do', g_ctx + '/hardware/show.do?page=${1}');
	}
</script>
<script src="${ctx}/js/common.js"></script>
<%@ include file="/WEB-INF/views/common/myModal.jsp" %>
<%@ include file="/WEB-INF/views/foot_n.jsp" %>