<%@page import="com.routon.plcloud.common.decorator.PageCheckboxDecorator"%>
<%@page import="com.routon.plcloud.common.decorator.PageDateTimeDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@ include file="/WEB-INF/views/head_n.jsp" %>
<link rel="stylesheet" href="${ctx}/css/zTreeStyle.css">
<style>
<!--
.ztree li a.curSelectedNode { height: 18px; }
.ztree li a:hover {text-decoration:none}
.count:hover {text-decoration:underline;}
-->
</style>


<div class="container" style=" width: 1560px;">
<div class="panel panel-default" style=" width: 1530px; margin-left: -15px;">
<!-- 	<div class="panel panel-default container" style="width: 1560px; margin-bottom: 20px;"> -->
  		<div class="panel-heading" >
  			
  			<div class="pull-right">
  			<c:if test="${(!empty userPrivilege['90000402'])}">
  				<div class="btn-group" >
  					<button type="button" class="btn btn-primary" onclick="downloadlicence()">授权文件下载</button>
  				</div>
  			</c:if>
<%--   			<c:if test="${(!empty userPrivilege['90000402'])}"> --%>
<!--   				<div class="btn-group" > -->
<!--   					<button type="button" class="btn btn-primary" onclick="licencedownload()">离线授权文件下载</button> -->
<!--   				</div>  -->
<%--   			</c:if>	 --%>
  			<c:if test="${(!empty userPrivilege['90000403'])}">
  			    <div class="btn-group" >
  					<button type="button" class="btn btn-primary" onclick="authrequest()">离线授权申请</button>
  				</div> 	
  				
  			</c:if>		
				<!-- <div class="btn-group" >
  					<button type="button" class="btn btn-primary" onclick="downloadExcel()">Excel模板下载</button>
  				</div>
  				<div class="btn-group" >
  					<button type="button" class="btn btn-primary" onclick="downloadUserFile()">导入说明文档下载</button>
  				</div> -->
  			<%-- <c:if test="${(!empty userPrivilege['90000401'])}">
  				<div class="btn-group" >
  					<button type="button" class="btn btn-primary" onclick="importExcel()">Excel导入</button>
  				</div>
  			</c:if> --%>
  				<!-- <div class="btn-group" >
  					<button type="button" class="btn btn-primary" onclick="addTerminal()">新增业务代码</button>
  				</div> -->
  			</div> 
  			 <h5>  订单    > 终端管理 </h5>
  		</div>
  		
  		<div class="panel-body">
  			<div class="panel panel-default col-sm-2" style=" overflow-y:auto; overflow-x:auto;width:249px; height:534px; ">
  			
  			<!--  ***** 	终端管理模块ztree搜索                  ****-->
  			<form  class="form-inline" style="width:220px;" role="form" id="queryform" name="queryform" action="${ctx}/terminal/list.do"  method="post">  
				 <div class="btn-group"  >
				  <select id="requirementtype" name="requirementtype" class="form-control "  >			    
			      		<option value="1" selected="selected" >公司</option>
						<option value="2"  >项目</option>
						<option value="3"  >订单</option>
				  </select>
				
  				   <input style= "width:83px;float:right" id="searchname" name="searchname" type="text" class="form-control" placeholder="查询信息" value="${searchname}" onkeyup="this.value=this.value.replace(/[^\uFF00-\uFFFF\u4e00-\u9fa5\w\@\&\-]/g,'')">			
  			    </div>
  			    <div class="btn-group" >
  				   <button type="submit" class="btn btn-primary" >查询</button>
  			    </div>
  			    </form>	
  			
				<ul id="tree" class="ztree "></ul>
			</div>
			<div class="panel panel-default col-sm-10">			
			    <form class="form-inline" role="form" id="queryform" name="queryform" action="${ctx}/project/list.do"  method="post">  
			    
			    <input id="hiddenInput" type="hidden" value="${treeNodeTid}">
			    <input id="tmpcompanyid" type="hidden" value="${tmpcompanyid}">
			    <input id="tmpprojectid" type="hidden" value="${tmpprojectid}">
			    <input id="tmporderid" type="hidden" value="${tmporderid}">
			    
<!-- 				<div class="btn-group"> -->
<%--   				   <input id="projectname" name="projectname" type="text" class="form-control" placeholder="请输入项目名称" value="${projectname}">			 --%>
<!--   			    </div> -->
<!--   			    <div class="btn-group"> -->
<!--   				   <button type="submit" class="btn btn-primary" >查询</button> -->
<!--   			    </div> -->
  			    </form>	 
  			    
  			    <!-- table -->
		<display:table name="requestScope.pageList" id="curPage" class="table table-striped" sort="external"
			requestURI="list.do"
			decorator="com.routon.plcloud.common.decorator.PageDecorator"
			export="false">
			<display:column property="term_licence" title="<%=PageCheckboxDecorator.getTitle(pageContext)%>" decorator="com.routon.plcloud.common.decorator.PageCheckboxDecorator" media="html" style="width:2%;"/>
<%-- 			<display:column property="client_code" title="<%=PageCheckboxDecorator.getTitle(pageContext)%>" decorator="com.routon.plcloud.common.decorator.PageCheckboxDecorator" media="html" style="width:2%;"/> --%>
<%-- 			<display:column title="ID"  property="id"  sortable="true"  style="width:5%;" /> --%>
			<display:column title="省"  sortable="true" property="province" style="width:5%;">
			</display:column>
			<display:column title="城市" sortable="true"  property="city" style="width:5%;">
			</display:column>
			<display:column title="区" sortable="true"  property="district" style="width:5%;">
			</display:column>
			<display:column title="业务代码" sortable="true"  property="client_code" style="width:10%;">
			</display:column>
<%-- 			<display:column title="酒店名称" sortable="true"  property="client_name" style="width:15%;"> --%>
<%-- 			</display:column> --%>
			<display:column title="联系人" sortable="true"  property="contact" style="width:5%;">
			</display:column>
			<display:column title="联系电话" sortable="true"  property="telno" style="width:5%;">
			</display:column>
			<display:column title="联系地址" sortable="true"  property="address" style="width:10%;">
			</display:column>
<%-- 			<display:column title="终端代码" sortable="true"  property="term_code" style="width:10%;"> --%>
<%-- 			</display:column> --%>

<!-- media="csv xml"  隐藏属性    -->
			<display:column title="机器码" sortable="true"  property="term_sn" style="width:10%;" >
			</display:column>
			<display:column title="授权文件" sortable="true"  property="term_licence" style="width:15%;" media="csv xml">
			</display:column>
			<display:column title="最近授权方式" sortable="true"  property="request_type" style="width:10%;" >
			</display:column>
			<display:column title="激活时间" sortable="true"  property="time" style="width:10%;">
			</display:column>
			<display:column title="截止时间" sortable="true"  property="expire" style="width:10%;">
			</display:column>
		</display:table>
    
  			   
  			       
			</div>
		 <%@ include file="/WEB-INF/views/common/pagination.jsp" %>   
 		</div>
 		</div>
 		</div>
<!-- 模态框（Modal） -->
<!-- 	模态框 -->
	<div class="modal fade" id="AuthModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content" style="width: 500px;">
	      <div class="modal-header">
<!-- 	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button> -->
	        <h4 class="modal-title" id="myModalLabel">离线授权申请</h4>
	      </div>
	      <div class="modal-body">
	       	<form:form class="form-horizontal"  id="terminalForm" name = "terminalForm" role="form" method="post" enctype="multipart/form-data" >
	       	
<%-- 	       	<input id="hiddenInput" type="hidden" value="${treeNodeTid}"> --%>
	       	
			 
			  <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;color:red;">业务代码</label>
			    <div class="col-sm-8" style="padding-left: 0px">
			      <input type="text" class="form-control" id="clientcode" name="clientcode"  placeholder="长度不能超过16个字符">
			    </div> 			   
			  </div>
			  <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;color:red;">机器码</label>
			    <div class="col-sm-8" style="padding-left: 0px">
			      <input type="text" class="form-control" id="termsn" name="termsn" placeholder="长度不能超过40个字符" >
			    </div> 			   
			  </div>	  
			</form:form>
	      </div>
	      <div class="modal-footer">
	    		  <button type="button" class="btn btn-default" onclick="Fallback()">返回</button> 
	      		  <button type="button" class="btn btn-primary" onclick="addSubmit()">确定</button> 
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- 模态框（TerminalModal） -->
<!-- 	模态框 -->
	<div class="modal fade" id="TerminalModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content" style="width: 500px;">
	      <div class="modal-header">
<!-- 	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button> -->
	        <h4 class="modal-title" id="myModalLabel">离线授权申请</h4>
	      </div>
	      <div class="modal-body">
	       	<form:form class="form-horizontal"  id="terminalForm1" name = "terminalForm1" role="form" method="post" enctype="multipart/form-data" >
	       	
<%-- 	       	<input id="hiddenInput" type="hidden" value="${treeNodeTid}"> --%>
	       	
			 
			  <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;">省份</label>
			    <div class="col-sm-8" style="padding-left: 0px">
			      <input type="text" class="form-control" id="province" name="province"  placeholder="长度不能超过16个字符">
			    </div> 			   
			  </div>
			  <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;">城市</label>
			    <div class="col-sm-8" style="padding-left: 0px">
			      <input type="text" class="form-control" id="city" name="city" placeholder="长度不能超过16个字符" >
			    </div> 			   
			  </div>
			  <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;">区</label>
			    <div class="col-sm-8" style="padding-left: 0px">
			      <input type="text" class="form-control" id="district" name="district" placeholder="长度不能超过16个字符" >
			    </div> 			   
			  </div>
			  <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;color:red;">业务代码</label>
			    <div class="col-sm-8" style="padding-left: 0px">
			      <input type="text" class="form-control" id="clientCode" name="clientCode" placeholder="长度不能超过16个字符" >
			    </div> 			   
			  </div>
			  <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;">单位名称</label>
			    <div class="col-sm-8" style="padding-left: 0px">
			      <input type="text" class="form-control" id="clientname" name="clientname" placeholder="长度不能超过32个字符" >
			    </div> 			   
			  </div>
			  <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;color:red;">联系人</label>
			    <div class="col-sm-8" style="padding-left: 0px">
			      <input type="text" class="form-control" id="contact" name="contact" placeholder="长度不能超过16个字符" >
			    </div> 			   
			  </div>
			  <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;color:red;">联系人电话</label>
			    <div class="col-sm-8" style="padding-left: 0px">
			      <input type="text" class="form-control" id="telno" name="telno" placeholder="长度不能超过16个字符" >
			    </div> 			   
			  </div>
			  <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;">联系地址</label>
			    <div class="col-sm-8" style="padding-left: 0px">
			      <input type="text" class="form-control" id="address" name="address" placeholder="长度不能超过32个字符" >
			    </div> 			   
			  </div>
			  <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;">备注</label>
			    <div class="col-sm-8" style="padding-left: 0px">
			      <input type="text" class="form-control" id="remark" name="remark" placeholder="长度不能超过16个字符" >
			    </div> 			   
			  </div>	  
			</form:form>
	      </div>
	      <div class="modal-footer">
	    		  <button type="button" class="btn btn-default" onclick="Fallback()">返回</button> 
	      		  <button type="button" class="btn btn-primary" onclick="addSubmitTerminal()">确定</button> 
	      </div>
	    </div>
	  </div>
	</div>
 		
 		
		


	
   
<script src="${ctx}/js/common.js"></script>
<SCRIPT type="text/javascript">

//浏览器版本
var BROWER = {
	mozilla : /firefox/.test(navigator.userAgent.toLowerCase()),
	webkit : /webkit/.test(navigator.userAgent.toLowerCase()),
	opera : /opera/.test(navigator.userAgent.toLowerCase()),
	chrome : /chrome/.test(navigator.userAgent.toLowerCase()),
	msie : /msie/.test(navigator.userAgent.toLowerCase())
}


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
// 		 var checkedNodetid = $('#hiddenInput').val();
// 		 var treeObj = $.fn.zTree.getZTreeObj("tree");
// 		 var node = treeObj.getNodeByTId(checkedNodetid);
		 if(treeNode.getParentNode() == null){
			 document.location.href = '${ctx}/terminal/list.do?page=${1}&companyid='+treeNode.id + '&treeNodeTid='+ treeNode.tId;
		 }
		 else{
			 var Parentnode = treeNode.getParentNode();
		 	 if(Parentnode.getParentNode() == null){
		 		document.location.href = '${ctx}/terminal/list.do?page=${1}&projectid='+treeNode.id + '&treeNodeTid='+ treeNode.tId;
		 	 }
		 	 else{
		 		document.location.href = '${ctx}/terminal/list.do?page=${1}&orderid='+treeNode.id + '&treeNodeTid='+ treeNode.tId;
		 	 }
			 
		 }

// 		 document.location.href = '${ctx}/terminal/list.do?page=${page}&id='+treeNode.id + '&treeNodeTid='+ treeNode.tId;

				
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

var zNodes =${terminalTreeBeans};

var zTree;
$(document).ready(function(){
	
	zTree = $.fn.zTree.init($("#tree"), setting, zNodes);
	var treeObj = $.fn.zTree.getZTreeObj("tree");
// 	var checkedNodetid = $('#hiddenInput').val();
	var tmpcompanyid = $('#tmpcompanyid').val();
	var tmpprojectid = $('#tmpprojectid').val();
	var tmporderid = $('#tmporderid').val();
	var node = null;
	if(tmpcompanyid!=""){
		 node = treeObj.getNodeByParam("id", tmpcompanyid, null);
	}
	if(tmpprojectid!=""){
		 node = treeObj.getNodeByParam("id", tmpprojectid, null);
	}
	if(tmporderid!=""){
		 node = treeObj.getNodeByParam("id", tmporderid, null);
	}
	if(node !=null){
		$("#" + node.tId + "_a").attr('class', 'curSelectedNode');
	}

// 	$("#" + checkedNodetid + "_a").attr('class', 'curSelectedNode');
	
});

/* function downloadExcel(){
	var browerType = "";
	// 判断浏览器版本
	if(BROWER.mozilla){
		browerType = "firefox";
	}
	else if(BROWER.webkit){
		browerType = "webkit";
	}
	else if(BROWER.opera){
		browerType = "opera";
	}
	else{
		browerType = "msie";
	}
	window.location.href="${base}terminal/downExcel.do?browerType="+browerType+"&fileName=template.xls";
} */

/* function downloadUserFile(){
	var browerType = "";
	// 判断浏览器版本
	if(BROWER.mozilla){
		browerType = "firefox";
	}
	else if(BROWER.webkit){
		browerType = "webkit";
	}
	else if(BROWER.opera){
		browerType = "opera";
	}
	else{
		browerType = "msie";
	}
	window.location.href="${base}terminal/downExcel.do?browerType="+browerType+"&fileName=importinstruction.doc";
} */

//检查字符串实际长度
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

function Fallback(){
	gotourl('${ctx}/terminal/list.do?page=${page}');
}

function addSubmitTerminal(){
	//项目id
	 var checkedNodetid = $('#hiddenInput').val();
	 var treeObj = $.fn.zTree.getZTreeObj("tree");
	 var node = treeObj.getNodeByTId(checkedNodetid);
	 //省份
	 var province = $('#province').val().trim();
	 var provinceinfo = "省份";
	 var provincelength = 32;
	 var provinceflag = checkvalue(province,provinceinfo,provincelength);
	 if(provinceflag == false){
		var provinceInput = document.getElementById("province");
		provinceInput.focus();
		return provinceflag;
	 }
	 //城市
	 var city = $('#city').val().trim();
	 var cityinfo = "城市";
	 var citylength = 32;
	 var cityflag = checkvalue(city,cityinfo,citylength);
	 if(cityflag == false){
		var cityInput = document.getElementById("city");
		cityInput.focus();
		return cityflag;
	 }
	 //区
	 var district = $('#district').val().trim();
	 var districtinfo = "区";
	 var districtlength = 32;
	 var districtflag = checkvalue(district,districtinfo,districtlength);
	 if(districtflag == false){
		var districtInput = document.getElementById("district");
		districtInput.focus();
		return districtflag;
	 }
	 //业务代码
	 var clientcode = $('#clientCode').val().trim();
	 var clientcodeinfo = "业务代码";
	 var clientcodelength = 32;
	 var clientcodeflag = checkRedvalue(clientcode,clientcodeinfo,clientcodelength);
	 if(clientcodeflag == false){
		var clientcodeInput = document.getElementById("clientCode");
		clientcodeInput.focus();
		return clientcodeflag;
	 }
	//单位名称
	 var clientname = $('#clientname').val().trim();
	 var clientnameinfo = "单位名称";
	 var clientnamelength = 64;
	 var clientnameflag = checkvalue(clientname,clientnameinfo,clientnamelength);
	 if(clientnameflag == false){
		var clientnameInput = document.getElementById("clientname");
		clientnameInput.focus();
		return clientnameflag;
	 }
	//联系人
	 var contact = $('#contact').val().trim();
	 var contactinfo = "联系人";
	 var contactlength = 32;
	 var contactflag = checkRedvalue(contact,contactinfo,contactlength);
	 if(contactflag == false){
		var contactInput = document.getElementById("contact");
		contactInput.focus();
		return contactflag;
	 }
	//联系人电话
	 var telno = $('#telno').val().trim();
	 var checkphoneNum = /^1[34578]\d{9}$/; 
		var len = telno.length;
		if(telno == ""){
			alert("请输入手机号码");
			var phoneNumInput = document.getElementById("telno");
			phoneNumInput.focus();
			return false;
		}
		else if(!checkphoneNum.test(telno)){
			alert("您输入的手机号码格式不对");
			var phoneNumInput = document.getElementById("telno");
			phoneNumInput.focus();
			return false;
		}
	//联系地址
	 var address = $('#address').val().trim();
	 var addressinfo = "联系地址";
	 var addresslength = 64;
	 var addressflag = checkvalue(address,addressinfo,addresslength);
	 if(addressflag == false){
		var addressInput = document.getElementById("address");
		addressInput.focus();
		return addressflag;
	 }
	//备注
	 var remark = $('#remark').val().trim();
	 var remarkinfo = "备注";
	 var remarklength = 32;
	 var remarkflag = checkvalue(remark,remarkinfo,remarklength);
	 if(remarkflag == false){
		var remarkInput = document.getElementById("remark");
		remarkInput.focus();
		return remarkflag;
	 }
	 //上报信息
	 var data = {};
	data.province = province;
	data.city = city;
	data.district = district;
	data.client_code = clientcode;
	data.client_name = clientname;
	data.contact = contact;
	data.telno = telno;
	data.address = address;
	data.remark = remark;
	data.orderid = node.id;
	var url = "${ctx}/terminal/reportinfo.do";
	 
	 
		$.ajax({ 
			type        : "POST"
			,url         : url
			,data        : data
			,contentType : "application/x-www-form-urlencoded;charset=utf-8;"
			,dataType    : "json"
			,cache		  : false	
			,success: function(info) {
				
				if (info.code == 1) {
					
					alert(info.msg);
					gotourl('${ctx}/terminal/list.do?page=${page}');
					
				//	var obj = eval('(' + info.msg + ')');
					//var node =  info.msg;
					//zTree.addNodes(nodes[0], obj);
// 					$('#myDlgContent').text('离线授权成功');
// 					$('#myModal').on('hidden.bs.modal', function (e) {
// 						gotourl('${ctx}/terminal/list.do?page=${page}');

// 					});
// 					$('#myModal').modal('show');
				//	zTree.cancelSelectedNode(nodes[0]);
				//    $('#myform').modal('hide');
				//	gotourl('${ctx}/project/list.do?page=${page}');
					
				}
				else  {
					alert(info.msg);
//	 				gotourl('${ctx}/terminal/list.do?page=${page}');
				}
// 				else if (info.code == -1) {
// 					alert("离线授权异常");
// //	 				gotourl('${ctx}/terminal/list.do?page=${page}');
// 				}					
			}
			,error : function(XMLHttpRequest, textStatus, errorThrown) {    
				alert(XMLHttpRequest.status + textStatus);    
			} 
			
		}
		);
	
}


function addSubmit(){
	
	//订单号
	 var checkedNodetid = $('#hiddenInput').val();
	 var treeObj = $.fn.zTree.getZTreeObj("tree");
	 var node = treeObj.getNodeByTId(checkedNodetid);
	
//业务代码
	var clientcode = $('#clientcode').val().trim();
	var clientcodeinfo = "业务代码";
	var clientcodelength = 32;
	var clientcodeflag = checkRedvalue(clientcode,clientcodeinfo,clientcodelength);
	if(clientcodeflag == false){
		var clientcodeInput = document.getElementById("clientcode");
		clientcodeInput.focus();
		return clientcodeflag;
	}
	
//机器码
	var termsn = $('#termsn').val().trim();
	var termsninfo = "机器码";
	var termsnlength = 40;
	var termsnflag = checkRedvalue(termsn,termsninfo,termsnlength);
	if(termsnflag == false){
		var termsnInput = document.getElementById("termsn");
		termsnInput.focus();
		return termsnflag;
	}
	
//申请授权
	var data = {};
	data.clientcode = clientcode;
	data.termsn = termsn;
	data.projectid = node.id;
	var url = "${ctx}/terminal/response.do";
	
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
				$('#myDlgContent').text('离线授权成功');
				$('#myModal').on('hidden.bs.modal', function (e) {
					gotourl('${ctx}/terminal/list.do?page=${page}');

				});
				$('#myModal').modal('show');
			//	zTree.cancelSelectedNode(nodes[0]);
			//    $('#myform').modal('hide');
			//	gotourl('${ctx}/project/list.do?page=${page}');
				
			}
			else if (info.code == 0) {
				alert(info.msg);
// 				gotourl('${ctx}/terminal/list.do?page=${page}');
			}
			else if (info.code == -1) {
				alert("离线授权异常");
// 				gotourl('${ctx}/terminal/list.do?page=${page}');
			}					
		}
		,error : function(XMLHttpRequest, textStatus, errorThrown) {    
			alert(XMLHttpRequest.status + textStatus);    
		} 
		
	}
	);
	
}

function licencedownload(){
	
// 	获取页面上的酒店代码
	var objs = document.getElementsByName('checkRow_');
	var selectedIds = null;
	for(var i=0;i < objs.length;i=i+1){
		if(objs[i].checked){
			//获取第八行
		   selectedIds = $(objs[i]).parents('td').parents('tr').children('td').eq(8).text();
		}
	}
	
// 	var selectedIds = getCheckedRowValue("");
	if(selectedIds==""){
		alert("请选择一个进行编辑");
		return false;
	}
	var selectedId = selectedIds.split(",");
	if (selectedId.length != 1) {
		alert("请选择一个进行编辑");
		return false;
	}
	var browerType = "";
	// 判断浏览器版本
	if(BROWER.mozilla){
		browerType = "firefox";
	}
	else if(BROWER.webkit){
		browerType = "webkit";
	}
	else if(BROWER.opera){
		browerType = "opera";
	}
	else{
		browerType = "msie";
	}
	
	 var data = {};
	 data.termsn = selectedIds;
// 	 data.browerType = browerType;
	 
	 $.ajax({
		url:'${base}terminal/licencejudge.do',
		type:'post',
		data:data,
		dataType    : "json",
		contentType : "application/x-www-form-urlencoded;charset=utf-8;",
		async:false,
		success:function(info){
			if (info.code == 0){
				alert(info.msg);
			}	
			else if(info.code == 1){
				 window.location.href="${base}terminal/licencedownload.do?browerType="+browerType+"&termsn=" + selectedIds ;
				 alert("下载授权文件成功");
			}
		}
	
	});
	  
	
	 
// 	 $.ajax({
// 			url:'${base}terminal/licencedownload.do',
// 			type:'post',
// 			data:data,
// 			dataType    : "json",
// 			async:false,
// 			success:function(info){
// 				if (info.code == 0){
// 					alert(info.msg);
// 				}	
// 				else if(info.code == 1){
// 					alert("下载授权文件成功!");
// 				}
// 			}
		
// 		});
	 
	 
// 	$.ajax({
// 		type        : "POST"
// 		,url         : "${base}/terminal/licencedownload.do"
// 		,data        : data
// 		,contentType : "application/x-www-form-urlencoded;charset=utf-8;"
// // 		,dataType    : "json"
// 		,cache		  : false	
// 		,success: function(jqXHR){
// 			alert(jqXHR.getResponseHeader("Content-disposition"));
			
			
// // 			if (info.code == 1) {
// // 				alert("下载授权文件成功!");
// // 				gotourl('${ctx}/terminal/list.do?page=${1}');
// // 			}
// // 			else if (info.code == 0) {
// // 				alert(info.msg);
// // 			}
// // 			else if (info.code == -1) {
// // 				alert("下载授权文件失败!");
// // 			}	
// 		}
// 	,error : function(XMLHttpRequest, textStatus, errorThrown) {    
// 		alert(XMLHttpRequest.status + textStatus);    
// 	} 	
// 	}
			
// 		);

}

function downloadlicence(){
	var termlicence = getCheckedRowValue("");
	if(termlicence==""){
		alert("请选择一个需要下载授权文件的终端");
		return false;
	}
	var termlicences = termlicence.split(",");
	if (termlicences.length != 1) {
		alert("请选择一个需要下载授权文件的终端");
		return false;
	}
	var browerType = "";
	// 判断浏览器版本
	if(BROWER.mozilla){
		browerType = "firefox";
	}
	else if(BROWER.webkit){
		browerType = "webkit";
	}
	else if(BROWER.opera){
		browerType = "opera";
	}
	else{
		browerType = "msie";
	}
// 	var data = {};
	var url = '${base}terminal/downloadlicence.do';
	var form=$("<form>");//定义一个form表单
    form.attr("style","display:none");
	form.attr("target","");
	form.attr("method","get");  //请求类型
	form.attr("action",url);   //请求地址
	$("body").append(form);//将表单放置在web中
	var input1 = $("<input>");
	input1.attr("type","hidden");
	input1.attr("name","termlicence");
	input1.attr("value",termlicence);
	form.append(input1);
	var input2 = $("<input>");
	input2.attr("type","hidden");
	input2.attr("name","browerType");
	input2.attr("value",browerType);
	form.append(input2);
	form.submit();
// 	data.browerType = browerType;
// 	data.termlicence = termlicence;
// 	$.ajax({
// 			url:"${base}terminal/downloadlicence.do",
// 			type:"post",
// 			data:data,
// // 			dataType    : "json",
// 			contentType : "application/x-www-form-urlencoded;charset=utf-8;",
// 			async:false,
// // 			success:function(info){
// // 				if (info.code == 1){
// // 					alert(info.msg);
// // 				}	
// // 				else {
// // 					alert("授权文件下载失败!");
// // 				}
// // 			}
		
// 		});
	
	
// 	 window.location.href="${base}terminal/downloadlicence.do?browerType="+browerType+"&termlicence=" + termlicence ;
	
	
	
	//获取页面上的酒店代码
// 	var objs = document.getElementsByName('checkRow_');
// 	for(var i=0;i < objs.length;i=i+1){
// 		if(objs[i].checked){
// 			//获取第四行
// 			var clientcode = $(objs[i]).parents('td').parents('tr').children('td').eq(4).text();
// 		}
// 	}
	
// 	 var checkedNodetid = $('#hiddenInput').val();
// 	 var termsn = getCheckedRowValue("");
// 		if(checkedNodetid == ""){
// 			alert("请选择一个订单!");
// 			return false;
// 		}
// 		else{
// // 			var treeObj = $.fn.zTree.getZTreeObj("tree");
// // 		 	var node = treeObj.getNodeByTId(checkedNodetid);
		 	
// 		 	if(node.getParentNode() == null){
// 		 		alert("您选择的是公司,请选择一个订单!");
// 		 		return false;
// 		 	}
// 		 	else{
// 		 		var Parentnode = node.getParentNode();
// 		 		if(Parentnode.getParentNode() == null){
// 		 			alert("您选择的是项目,请选择一个订单!");
// 			 		return false;
// 		 		}
// 		 		else{
// 		 			if(termsn == ""){
// 		 				$('#AuthModal').modal('show');
// 		 			}
// 		 			else{
// 		 				$('#AuthModal').modal('show');
// 		 				$("#termsn").val(termsn);
// 		 				$("#clientcode").val(clientcode);
// 		 			}
		 			
		 			
// 		 		}
// 		 	}
// 		}
}


function authrequest(){
	 var checkedNodetid = $('#hiddenInput').val();
		if(checkedNodetid == ""){
			alert("请选择一个项目 ");
			return false;
		}
		else{
			var treeObj = $.fn.zTree.getZTreeObj("tree");
		 	var node = treeObj.getNodeByTId(checkedNodetid);
		 	
		 	if(node.getParentNode() == null){
		 		alert("您选择的是公司,请选择一个项目");
		 		return false;
		 	}
		 	else{
		 		var Parentnode = node.getParentNode();
		 		if(Parentnode.getParentNode() == null){
		 			$('#AuthModal').modal('show');
		 			
		 		}
		 		else{
// 		 			var ordernode = Parentnode.getParentNode();
		 			

// 		 			$.ajax({
// 		 				url:'${base}terminal/orderask.do',
// 		 				type:'post',
// 		 				data:{id : node.id},
// 		 				dataType    : "json",
// 		 				async:false,
// 		 				success:function(info){
// 		 					if (info.code == 0){
// 		 						alert(info.msg);
// 		 					}	
// 		 					else if(info.code == 1){
		 			 		
// 		 					}
// 		 				}
		 			alert("您选择的是订单,请选择一个项目");
			 		return false;
// 		 			});
		 			
		 		}
		 			
		 	}
		}
}

/* function addTerminal(){
    var checkedNodetid = $('#hiddenInput').val();
	if(checkedNodetid == ""){
		alert("请选择一个订单");
		return false;
	}
	else{
		var treeObj = $.fn.zTree.getZTreeObj("tree");
	 	var node = treeObj.getNodeByTId(checkedNodetid);
	 	
 	
	 	if(node.getParentNode() == null){
	 		alert("您选择的是公司,请选择一个订单");
	 		return false;
	 	}
	 	else{
	 		var Parentnode = node.getParentNode();
	 		if(Parentnode.getParentNode() == null){		
	 			alert("您选择的是项目，请选择一个订单");
	 			return false;
	 		
	 		}
	 		else{
	 			$('#TerminalModal').modal('show');
	 		}
	 	}
	}
	
	
} */


/* function importExcel(){
    var checkedNodetid = $('#hiddenInput').val();
	if(checkedNodetid == ""){
		alert("请选择一个订单");
		return false;
	}
	else{
		var treeObj = $.fn.zTree.getZTreeObj("tree");
	 	var node = treeObj.getNodeByTId(checkedNodetid);
	 	
 	
	 	if(node.getParentNode() == null){
	 		alert("您选择的是公司,请选择一个订单");
	 		return false;
	 	}
	 	else{
	 		var Parentnode = node.getParentNode();
	 		if(Parentnode.getParentNode() == null){		
	 			alert("您选择的是项目，请选择一个订单");
	 			return false;
// 		 		$.ajax({
// 		 			url:'${base}terminal/importExcel.do',
// 		 			type:'post',
// 		 			data:{id : node.id},
// 		 			async:false,
// 		 			success:function(){
		 				
// 		 			}	
// 		 		});
	 			
	 		}
	 		else{
	 			var html = "<form id=\"submit_form\" method=\"post\" action=\"${base}terminal/importExcel.do\" target=\"exec_target\" enctype=\"multipart/form-data\"> <input id=\"orderid\" name=\"orderid\" type=\"hidden\" value= " + node.id + "><input id = \"input-1\" name=\"images\" type=\"file\" multiple class=\"file\"><br><input class=\"btn btn-primary\" name=\"upload\" type=\"submit\" value=\"上传\" /></form>";
	 		    BootstrapDialog.show({
	 		        title: '客户信息Excel导入',
	 		        message: html
	 		    })	
	 		}
	 	}
	 	
	 	
	 	
// 	 	if (node.length > 0) {
// 	 		var Parentnode = node[0].getParentNode();
// 	 		if(Parentnode.length > 0){
// 	 			var html = "<form id=\"submit_form\" method=\"post\" action=\"${base}terminal/importExcel.do\" target=\"exec_target\" enctype=\"multipart/form-data\"><input id = \"input-1\" name=\"images\" type=\"file\" multiple class=\"file\"><br><input class=\"btn btn-primary\" name=\"upload\" type=\"submit\" value=\"上传\" /></form>";
// 	 		    BootstrapDialog.show({
// 	 		        title: '客户信息Excel导入',
// 	 		        message: html
// 	 		    })	
	 			
// 	 		}
// 	 		else{
// 	 			alert("您选择的是订单，请选择一个项目!");
// 	 			return false;
// 	 		}
// 	 	}
// 	 	else{
// 	 		alert("您选择的是公司,请选择一个项目!");
//  			return false;
// 	 	}
 	}
	
	
	
	


} */




//-->
</SCRIPT>	
<script type="text/javascript">
		jQuery(document).ready(function() {
			var result = "${result}";
	 		var readdata = "${readdata}";
			var geshi = "${geshi}"; 
			var iread = true;
			var igeshi = true;
			if (geshi != "" && geshi != null){
// 				$("#queryBtn").click();
				 gotourl('${ctx}/terminal/list.do?page=${1}');
				alert(geshi);
			}
			if (result != "" && result != null) {
				if (result == "uploadfail") {
// 					$("#queryBtn").click();
 				gotourl('${ctx}/terminal/list.do?page=${1}');
					alert('上传失败！');
				}/*  else if (result == "uploadsucess") {
					$("#queryBtn").click();
					alert('上传成功！');  
			}*/
		   }
		   if(readdata != "" && readdata != null){
// 			   $("#queryBtn").click();
			   gotourl('${ctx}/terminal/list.do?page=${1}');
			   alert(readdata);
		   }
		});
</script>
<script src="${ctx}/js/jquery.ztree.all-3.5.min.js"></script>
<%@ include file="/WEB-INF/views/common/myModal.jsp" %>
<%@ include file="/WEB-INF/views/foot_n.jsp" %>
<%-- <%@ include file="/WEB-INF/views/common/myModal.jsp" %>	 --%>
<%-- <script src="${ctx}/js/jquery.ztree.all-3.5.min.js"></script>	 --%>
<%-- <%@ include file="/WEB-INF/views/foot_n.jsp" %> --%>

