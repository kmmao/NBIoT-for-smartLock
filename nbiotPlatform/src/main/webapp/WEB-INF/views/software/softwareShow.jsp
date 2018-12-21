<%@ page import="com.routon.plcloud.common.decorator.PageCheckboxDecorator"%>
<%@ page import="com.routon.plcloud.common.decorator.PageLinkDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ include file="/WEB-INF/views/head_n.jsp" %>
<%@ include file="/WEB-INF/views/activiti/RuntimeProcess.jsp" %>
<link rel="stylesheet" href="${ctx}/css/zTreeStyle.css">
<div class="panel panel-default" style="width: 1560px;">
		<div class="panel-heading " style="padding: 0px;">
  			<div class="" style="display: inline-block;width: 100%;">
  			
	  			<div class=" col-sm-8" style="width: 1530px;">
		  			<form class="form-inline" role="form" id="queryform" name="queryform" action="${ctx}/software/show.do"  method="post">  		
		  				<div class="btn-group" style="margin-top: 5px;margin-bottom: 5px;">
		  					<input size="20" type="text" id="productName" name="productName" value="${productName}" class="form-control" placeholder="请输入软件名称">
		  					<input size="20" type="text" id="productName" name="productName" value="${productName}" class="form-control" placeholder="请输入软件版本">
		  				</div>
		  				<div class="btn-group">
		  					<button id="queryBtn" type="submit" class="btn btn-primary" >查询</button>
		  				</div>
		  			<div class="btn-group">
		  				<button id="newBtn" type="button" class="btn btn-primary" data-toggle="modal" data-target="#SoftwareModal" onclick="newProduct()">上传软件</button>
		  			</div>
		  				<button id= "editBtn" type="button" class="btn btn-primary" onclick="addGroup()">指派分组</button>
		  				<button id= "deleteBtn" type="button" class="btn btn-danger" onclick="delProduct()">禁用</button>
		  			</form>
	  			</div>
  			</div> 
  		</div>
		<display:table name="${pageList}" id="curPage" class="table table-striped" sort="external"
			requestURI="show.do"
			decorator="com.routon.plcloud.common.decorator.PageLinkDecorator"
			export="false">
			<display:column property="id" title="<%=PageCheckboxDecorator.getTitle(pageContext)%>" decorator="com.routon.plcloud.common.decorator.PageCheckboxDecorator"  style="width:2%;"/>
			<display:column title="序号" sortable="true" style="width:1%;" >
				<c:out value="${curPage_rowNum}"/>
			</display:column>
			<display:column title="软件产品名称" property="softwareName" sortable="true" style="width:5%;" ></display:column>
			<display:column title="软件版本号" property="softwareVersion" sortable="true"  style="width:5%;" ></display:column>
			<display:column title="ERP编码" property="erpCode" sortable="true"  style="width:5%;" ></display:column>
			<display:column title="软件著作权编号" property="softwareCopyrightNumber" sortable="true"  style="width:5%;" ></display:column>
			<display:column title="适配硬件产品" property="detailofHardwareProduct" sortable="true" style="width:5%;" ></display:column>
			<display:column title="适配硬件平台" property="detailofHardwarePlatform" sortable="true"  style="width:5%;"></display:column>
			<display:column title="适配操作系统" property="detailofOS" sortable="true"  style="width:5%;"></display:column>
			<display:column title="上传时间"  property="uploadTime" sortable="true"  style="width:5%;" decorator="com.routon.plcloud.common.decorator.PageDateTimeDecorator"></display:column>
			<display:column title="软件大小"  property="size" sortable="true"  style="width:5%;"></display:column>
			<display:column title="上传用户"  property="uploadUser" sortable="true"  style="width:5%;"></display:column>
			<display:column title="客户姓名"  property="customerName" sortable="true"  style="width:5%;"></display:column>
			<display:column title="对应项目"  property="correspondProject" sortable="true"  style="width:5%;"></display:column>
			<display:column title="对应终端型号"  property="correspondTerminalType" sortable="true"  style="width:5%;"></display:column>
		</display:table>
</div>
<%@ include file="/WEB-INF/views/common/pagination.jsp" %>
<!-- 模态框（Modal） -->
<div class="modal fade" id="SoftwareModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" aria-hidden="true">
    <div class="modal-dialog" style="width: 400px;">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">上传软件</h4>
            </div>
            <div class="modal-body">
            	<form role="form" id="softwareForm" name="softwareForm" method='post' action='${ctx}/software/add.do'>
            		<div class="form-group">
            			<input id="updateId" name="updateId" type="hidden">
    					<label for="name">软件产品名称</label>
    					<input type="text" class="form-control" id="softwareName" name="softwareName">
    					<label for="name">软件版本号</label>
    					<input type="text" class="form-control" id="softwareVersion" name="softwareVersion">
    					<label for="name">ERP编码</label>
    					<input type="text" class="form-control" id="erpCode" name="erpCode">
    					<label for="name">软件著作权编号</label>
    					<input type="text" class="form-control" id="softwareCopyrightNumber" name="softwareCopyrightNumber">
    					<label for="name">适配硬件产品:</label>
    					<select id="hardwareProductSelect" name="hardwareProductSelect" class="select_class form-control" multiple="multiple">
			      			<c:forEach var="item" items="${hardwareProductList}">
								<option value="${item}">${item}</option>
						    </c:forEach> 
						</select></br>
			      		<label for="name">适配硬件平台:</label>
    					<select id="hardwareStationSelect" name="hardwareStationSelect" class="select_class form-control" multiple="multiple">
			      			<c:forEach var="item" items="${hardwareStationList}">
								<option value="${item}">${item}</option>
						    </c:forEach> 
						</select></br>
			      		<label for="name">适配操作系统:</label>
    					<select id="OSSelect" name="OSSelect" class="select_class form-control" multiple="multiple">
			      			<c:forEach var="item" items="${operatingSystemList}">
								<option value="${item}">${item}</option>
						    </c:forEach>
						</select></br>
    					<label for="name">请上传软件:</label>
    					<input type="file" id="inputfile">
  					</div>
            	</form>
			</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="save('#softwareForm', '${base}software/add.do', '${base}software/show.do?page=${page}')">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<div class="modal fade" id="mytree" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
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

<script>
 $(document).ready(function() {
    ToolTip.init({
        delay: 400,
        fadeDuration: 250,
        fontSize: '1.0em',
        theme: 'light',
        textColor: '#757575',
        shadowColor: '#000',
        fontFamily: "'Roboto-Medium', 'Roboto-Regular', Arial"
    });
}); 


/* $(function() {  $("[data-toggle='popover']").popover();  });  */

/* $(document).ready(function() {
	
	//$("#popover1").attr('data-content','i am wangxiwei');
	$("[data-toggle='popover']").popover();
}); */


function hoverEventA(obj){
	
	var idString = $(obj).attr("id");
	var id = idString.substring(8);
	var reuslt = "";
	var arr = new Array();
	
    $.ajax({
		url:'${base}software/queryAdoptedHardwareProduct.do',
		type:'post',
		data:{ softwareId : id },
		async:false,
		success:function(data){
			for(var i = 0; i < data.length; i++){
				arr.push(data[i]);
			}
			var str=arr.join(",");
			$("#popoverA" + id).popover().mousemove(function() {  
				$("#popoverA" + id).attr('data-content', str);
		    });  
		}
	});  
	

}

function hoverEventB(obj){
	
	var idString = $(obj).attr("id");
	var id = idString.substring(8);
	var reuslt = "";
	var arr = new Array();
	
	$.ajax({
		url:'${base}software/queryAdoptedHardwarePlatform.do',
		type:'post',
		data:{ softwareId : id },
		async:false,
		success:function(data){
			for(var i = 0; i < data.length; i++){
				arr.push(data[i]);
			}
			var str=arr.join(",");
			$("#popoverB" + id).popover().mousemove(function() {  
				$("#popoverB" + id).attr('data-content', str);
		    });  
		}
	}); 

}

function hoverEventC(obj){
	
	var idString = $(obj).attr("id");
	var id = idString.substring(8);
	var reuslt = "";
	var arr = new Array();
	
	$.ajax({
		url:'${base}software/queryAdoptedOS.do',
		type:'post',
		data:{ softwareId : id },
		async:false,
		success:function(data){
			for(var i = 0; i < data.length; i++){
				arr.push(data[i]);
			}
			var str=arr.join(",");
			$("#popoverC" + id).popover().mousemove(function() {  
				$("#popoverC" + id).attr('data-content', str);
		    });  
		}
	}); 

}

function addGroup(){
	var selectedIds = getCheckedRowValue("");
	if(selectedIds==""){
		alert("请选择一个进行编辑!");
		return false;
	}
	var selectedId = selectedIds.split(",");
	if (selectedId.length != 1) {
		alert("请选择一个进行编辑!");
		return false;
	}
	$('#mytree').modal('show');
	
 	$.ajax({
		url:'${base}software/ztree.do',
		type:'post',
		async:false,
		success:function(data){
			var id1 = data[0];
			var id2 = data[1];
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			treeObj.checkNode(treeObj.getNodeByParam("id", id1, null), true, true); 
			treeObj.checkNode(treeObj.getNodeByParam("id", id2, null), true, true); 
		}
	}); 
	

}


</script>
<script type="text/javascript">
    $(document).ready(function() {
        $('.select_class').multiselect();
        
        
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
    			
/*     			$("#menuIds").val(ids);
    			$("#menuNames").val(menuNames);
    			$('#mytree').modal('hide') */
    		}
    });
</script>
<script src="${ctx}/js/tooltip.js"></script>
<script src="${ctx}/js/common.js"></script>
<script src="${ctx}/js/jquery.ztree.all-3.5.min.js"></script>
<!-- Include the plugin's CSS and JS: -->
<script type="text/javascript" src="${ctx}/js/bootstrap-multiselect.js"></script>
<link rel="stylesheet" href="${ctx}/css/bootstrap-multiselect.css" type="text/css"/>
<%@ include file="/WEB-INF/views/common/myModal.jsp" %>
<%@ include file="/WEB-INF/views/foot_n.jsp" %>