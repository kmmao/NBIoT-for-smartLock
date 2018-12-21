<%@page
	import="com.routon.plcloud.common.decorator.PageCheckboxDecorator"%>
<%@page
	import="com.routon.plcloud.common.decorator.PageDateTimeDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ include file="/WEB-INF/views/head_n.jsp"%>
<link rel="stylesheet" href="${ctx}/css/zTreeStyle.css">
<style>
<!--
.ztree li a.curSelectedNode {
	height: 18px;
}

.ztree li a:hover {
	text-decoration: none
}

.count:hover {
	text-decoration: underline;
}
-->
</style>

<!-- 	编辑客户信息模态框 -->
<div class="modal fade" id="AuthModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content" style="width: 500px;">
			<div class="modal-header">
				<h4 class="modal-title" id="myModalLabel">客户信息编辑</h4>
			</div>
			<div class="modal-body">
				<form:form class="form-horizontal" id="userForm" name="userForm"
					role="form" method="post" enctype="multipart/form-data">
					<div class="form-group" style="margin-right: 0px;">
						<label for="title" class="col-sm-4 control-label"
							style="padding-left: 5px; padding-right: 5px;">公司名称</label>
						<div class="col-sm-8" style="padding-left: 0px">
							<input id="companyName" name="companyName" disabled="disabled"
								type="text" class="form-control" />
						</div>
					</div>
					<div class="form-group" style="margin-right: 0px;">
						<label for="title" class="col-sm-4 control-label"
							style="padding-left: 5px; padding-right: 5px;">项目名称</label>
						<div class="col-sm-8" style="padding-left: 0px">
							<input id="projectName" name="projectName" disabled="disabled"
								type="text" class="form-control" />
						</div>
					</div>
					<div class="form-group" style="margin-right: 0px;">
						<label for="title" class="col-sm-4 control-label"
							style="padding-left: 5px; padding-right: 5px;">订单号</label>
						<div class="col-sm-8" style="padding-left: 0px">
							<input id="ordernum" name="ordernum" disabled="disabled"
								type="text" class="form-control" />
						</div>
					</div>
					<div class="form-group" style="margin-right: 0px;">
						<label for="title" class="col-sm-4 control-label"
							style="padding-left: 5px; padding-right: 5px; color: red;">业务代码</label>
						<div class="col-sm-8" style="padding-left: 0px">
							<input id="clientcode" name="clientcode" disabled="disabled"
								type="text" class="form-control" />
						</div>
					</div>
					<div class="form-group" style="margin-right: 0px;">
						<label for="title" class="col-sm-4 control-label"
							style="padding-left: 5px; padding-right: 5px;">省</label>
						<div class="col-sm-8" style="padding-left: 0px">
							<input type="text" class="form-control" id="province"
								name="province">
						</div>
					</div>
					<div class="form-group" style="margin-right: 0px;">
						<label for="title" class="col-sm-4 control-label"
							style="padding-left: 5px; padding-right: 5px;">城市</label>
						<div class="col-sm-8" style="padding-left: 0px">
							<input type="text" class="form-control" id="city" name="city">
						</div>
					</div>
					<div class="form-group" style="margin-right: 0px;">
						<label for="title" class="col-sm-4 control-label"
							style="padding-left: 5px; padding-right: 5px;">区</label>
						<div class="col-sm-8" style="padding-left: 0px">
							<input type="text" class="form-control" id="area" name="area">
						</div>
					</div>
					<div class="form-group" style="margin-right: 0px;">
						<label for="title" class="col-sm-4 control-label"
							style="padding-left: 5px; padding-right: 5px;">地址</label>
						<div class="col-sm-8" style="padding-left: 0px">
							<input type="text" class="form-control" id="address"
								name="address">
						</div>
					</div>
					<div class="form-group" style="margin-right: 0px;">
						<label for="title" class="col-sm-4 control-label"
							style="padding-left: 5px; padding-right: 5px;">业务名称</label>
						<div class="col-sm-8" style="padding-left: 0px">
							<input type="text" class="form-control" id="clientname"
								name="clientname">
						</div>
					</div>
					<div class="form-group" style="margin-right: 0px;">
						<label for="title" class="col-sm-4 control-label"
							style="padding-left: 5px; padding-right: 5px; color: red;">联系人</label>
						<div class="col-sm-8" style="padding-left: 0px">
							<input type="text" class="form-control" id="contact"
								name="contact">
						</div>
					</div>
					<div class="form-group" style="margin-right: 0px;">
						<label for="title" class="col-sm-4 control-label"
							style="padding-left: 5px; padding-right: 5px; color: red;">联系电话</label>
						<div class="col-sm-8" style="padding-left: 0px">
							<input type="text" class="form-control" id="telno" name="telno">
						</div>
					</div>
					<div class="form-group" style="margin-right: 0px;">
						<label for="title" class="col-sm-4 control-label"
							style="padding-left: 5px; padding-right: 5px;">备注</label>
						<div class="col-sm-8" style="padding-left: 0px">
							<input type="text" class="form-control" id="remark" name="remark">
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

<!-- 	新增业务代码模态框 -->
<div class="modal fade" id="TerminalModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content" style="width: 500px;">
			<div class="modal-header">
				<h4 class="modal-title" id="myModalLabel">新增业务代码</h4>
			</div>
			<div class="modal-body">
				<form:form class="form-horizontal" id="terminalForm1"
					name="terminalForm1" role="form" method="post"
					enctype="multipart/form-data">
					<div class="form-group" style="margin-right: 0px;">
						<label for="title" class="col-sm-4 control-label"
							style="padding-left: 5px; padding-right: 5px;">省份</label>
						<div class="col-sm-8" style="padding-left: 0px">
							<input type="text" class="form-control" id="province1"
								name="province1" placeholder="长度不能超过16个字符">
						</div>
					</div>
					<div class="form-group" style="margin-right: 0px;">
						<label for="title" class="col-sm-4 control-label"
							style="padding-left: 5px; padding-right: 5px;">城市</label>
						<div class="col-sm-8" style="padding-left: 0px">
							<input type="text" class="form-control" id="city1" name="city1"
								placeholder="长度不能超过16个字符">
						</div>
					</div>
					<div class="form-group" style="margin-right: 0px;">
						<label for="title" class="col-sm-4 control-label"
							style="padding-left: 5px; padding-right: 5px;">区</label>
						<div class="col-sm-8" style="padding-left: 0px">
							<input type="text" class="form-control" id="district"
								name="district" placeholder="长度不能超过16个字符">
						</div>
					</div>
					<div class="form-group" style="margin-right: 0px;">
						<label for="title" class="col-sm-4 control-label"
							style="padding-left: 5px; padding-right: 5px; color: red;">业务代码</label>
						<div class="col-sm-8" style="padding-left: 0px">
							<input type="text" class="form-control" id="clientCode1" name="clientCode1" placeholder="长度为10个数字">
						</div>
					</div>
					<div class="form-group" style="margin-right: 0px;">
						<label for="title" class="col-sm-4 control-label"
							style="padding-left: 5px; padding-right: 5px;">业务名称</label>
						<div class="col-sm-8" style="padding-left: 0px">
							<input type="text" class="form-control" id="clientname1"
								name="clientname1" placeholder="长度不能超过32个字符">
						</div>
					</div>
					<div class="form-group" style="margin-right: 0px;">
						<label for="title" class="col-sm-4 control-label"
							style="padding-left: 5px; padding-right: 5px; color: red;">联系人</label>
						<div class="col-sm-8" style="padding-left: 0px">
							<input type="text" class="form-control" id="contact1"
								name="contact1" placeholder="长度不能超过16个字符">
						</div>
					</div>
					<div class="form-group" style="margin-right: 0px;">
						<label for="title" class="col-sm-4 control-label"
							style="padding-left: 5px; padding-right: 5px; color: red;">联系人电话</label>
						<div class="col-sm-8" style="padding-left: 0px">
							<input type="text" class="form-control" id="telno1" name="telno1"
								placeholder="长度不能超过16个字符">
						</div>
					</div>
					<div class="form-group" style="margin-right: 0px;">
						<label for="title" class="col-sm-4 control-label"
							style="padding-left: 5px; padding-right: 5px;">联系地址</label>
						<div class="col-sm-8" style="padding-left: 0px">
							<input type="text" class="form-control" id="address1"
								name="address1" placeholder="长度不能超过32个字符">
						</div>
					</div>
					<div class="form-group" style="margin-right: 0px;">
						<label for="title" class="col-sm-4 control-label"
							style="padding-left: 5px; padding-right: 5px;">备注</label>
						<div class="col-sm-8" style="padding-left: 0px">
							<input type="text" class="form-control" id="remark1"
								name="remark1" placeholder="长度不能超过16个字符">
						</div>
					</div>
				</form:form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" onclick="Fallback()">返回</button>
				<button type="button" class="btn btn-primary"
					onclick="addSubmitTerminal()">确定</button>
			</div>
		</div>
	</div>
</div>

<div class="container" style="width: 1560px;">
	<div class="panel panel-default"
		style="width: 1530px; margin-left: -15px;">
		<div class="panel-heading">

			<div class="pull-right">
				<div class="btn-group">
					<button type="button" class="btn btn-primary"
						onclick="downloadExcel()">Excel模板下载</button>
				</div>
				<div class="btn-group">
					<button type="button" class="btn btn-primary"
						onclick="downloadUserFile()">导入说明文档下载</button>
				</div>

				<c:if test="${(!empty userPrivilege['90000502'])}">
					<div class="btn-group">
						<button type="button" class="btn btn-primary"
							onclick="importExcel()">Excel导入</button>
					</div>

					<div class="btn-group">
						<button type="button" class="btn btn-primary"
							onclick="addTerminal()">新增业务代码</button>
					</div>
				</c:if>

				<c:if test="${(!empty userPrivilege['90000501'])}">
					<div class="btn-group">
						<button type="button" class="btn btn-primary" onclick="edit()">编辑</button>
					</div>
				</c:if>
			</div>
			<h5>订单 > 用户信息管理</h5>
		</div>

		<div class="panel-body">

			<!-- <div class="panel panel-default col-sm-2" style=" overflow-y:auto; overflow-x:auto;width:240px; height:624px; ">
				<ul id="tree" class="ztree "></ul>
			</div> -->
			<div class="panel panel-default col-sm-2"
				style="overflow-y: auto; overflow-x: auto; width: 249px; height: 534px;">

				<!--  ***** 	客户信息管理模块ztree搜索                  ****-->
				<form class="form-inline" style="width: 220px;" role="form"
					id="queryform" name="queryform" action="${ctx}/clientinfo/list.do"
					method="post">
					<input id="hiddenInput" type="hidden" value="${treeNodeTid}">

					<input id="tmpcompanyid" type="hidden" value="${tmpcompanyid}">
					<input id="tmpprojectid" type="hidden" value="${tmpprojectid}">
					<input id="tmporderid" type="hidden" value="${tmporderid}">

					<div class="btn-group">
						<select id="requirementtype" name="requirementtype"
							class="form-control ">
							<option value="1" selected="selected">公司</option>
							<option value="2">项目</option>
							<option value="3">订单</option>
						</select> <input style="width: 83px; float: right" id="searchname"
							name="searchname" type="text" class="form-control"
							placeholder="查询信息" value="${searchname}"
							onkeyup="this.value=this.value.replace(/[^\uFF00-\uFFFF\u4e00-\u9fa5\w\@\&\-]/g,'')">
					</div>
					<div class="btn-group">
						<button type="submit" class="btn btn-primary">查询</button>
					</div>
				</form>

				<ul id="tree" class="ztree "></ul>
			</div>
			<div class="panel panel-default col-sm-10">
				<display:table name="requestScope.pageList" id="curPage"
					class="table table-striped" sort="external" requestURI="list.do"
					decorator="com.routon.plcloud.common.decorator.PageDecorator"
					export="false">
					<display:column property="client_code"
						title="<%=PageCheckboxDecorator.getTitle(pageContext)%>"
						decorator="com.routon.plcloud.common.decorator.PageCheckboxDecorator"
						style="width:2%;" />
					<display:column title="订单号" sortable="true" property="orderid"
						style="width:10%;"></display:column>
					<display:column title="省" sortable="true" property="province"
						style="width:5%;"></display:column>
					<display:column title="城市" sortable="true" property="city"
						style="width:5%;"></display:column>
					<display:column title="区" sortable="true" property="district"
						style="width:5%;"></display:column>
					<display:column title="业务代码" sortable="true" property="client_code"
						style="width:8%;"></display:column>
					<display:column title="业务名称" sortable="true" property="client_name"
						style="width:10%;"></display:column>
					<display:column title="联系人" sortable="true" property="contact"
						style="width:5%;"></display:column>
					<display:column title="联系电话" sortable="true" property="telno"
						style="width:5%;"></display:column>
					<display:column title="联系地址" sortable="true" property="address"
						style="width:12%;"></display:column>
					<display:column title="备注" sortable="true" property="remark"
						style="width:10%;"></display:column>
				</display:table>
			</div>
			<%@ include file="/WEB-INF/views/common/pagination.jsp"%>
		</div>
	</div>
</div>
<%-- <div class="panel panel-default">
  		<div class="panel-heading" style="width:1528px;height:48px;"> 
  		    <div class="" style="display: inline-block;width: 100%;">
				<div class="pull-right" >
				<form class="form-inline" role="form" id="queryform" name="queryform" action="${ctx}/clientinfo/list.do"  method="post">  
	  				<c:if test="${(!empty userPrivilege['90000501'])}">
		  				<div class="btn-group">
		  					<button type="button" class="btn btn-primary" onclick="edit()">编辑客户信息</button>
		  				</div>
	  				</c:if>
  				</form>
  			</div> 
  			 <h5>  系统    > 客户信息管理 </h5>
  			 </div>
  		</div>
  		
  		<div class="panel-body" >
  			<div class="btn-group">
  			<form class="form-inline" role="form" id="userForm" name="userForm" action="${ctx}/clientinfo/list.do"  method="post">  		
  			
  			<div class="form-group">
			    <div class="col-sm-4">
			      <select id="company" name="company" class="form-control" >
			      <option value="">―请选择公司―</option> 
					 <c:forEach var="item" items="${companynames}">
						<option value="${item}">${item}</option>
					 </c:forEach> 
				  </select>
			    </div>
			  </div>
			  <div class="form-group">
			    <div class="col-sm-4">
			      <select id="project" name="project" class="form-control" >
			      <option value="">―请选择项目―</option> 
					 <c:forEach var="item" items="${projects}">
						<option value="${item}">${item}</option>
					 </c:forEach> 
				  </select>
			    </div>
			  </div>
			  <div class="form-group">
			    <div class="col-sm-4">
			      <select id="ordernum" name="ordernum" class="form-control" >
			      <option value="">―请选择订单号―</option> 
					 <c:forEach var="item" items="${ordernums}">
						<option value="${item}">${item}</option>
					 </c:forEach> 
				  </select>
			    </div>
			  </div>
			
			<c:if test="${(!empty userPrivilege['90000500'])}">  
	  			<div class="btn-group">
	  				<button type="submit" class="btn btn-primary" >查询</button>
	  			</div>
  			</c:if>
  			</form>
  			</div>
  		</div>
  		
		<!-- table -->
		<display:table name="requestScope.pageList" id="curPage" class="table table-striped" sort="external"
			requestURI="list.do"
			decorator="com.routon.plcloud.common.decorator.PageDecorator"
			export="false">
			<display:column property="client_code" title="<%=PageCheckboxDecorator.getTitle(pageContext)%>" decorator="com.routon.plcloud.common.decorator.PageCheckboxDecorator"  style="width:2%;"/>
					<display:column title="订单号"  sortable="true" property="orderid" style="width:10%;"></display:column>
					<display:column title="省"  sortable="true" property="province" style="width:5%;"></display:column>
					<display:column title="城市" sortable="true"  property="city" style="width:5%;"></display:column>
					<display:column title="区" sortable="true"  property="district" style="width:5%;"></display:column>
					<display:column title="业务代码" sortable="true"  property="client_code" style="width:8%;"></display:column>
					<display:column title="业务名称" sortable="true"  property="client_name" style="width:10%;"></display:column>
					<display:column title="联系人" sortable="true"  property="contact" style="width:5%;"></display:column>
					<display:column title="联系电话" sortable="true"  property="telno" style="width:5%;"></display:column>
					<display:column title="联系地址" sortable="true"  property="address" style="width:12%;"></display:column>
					<display:column title="备注" sortable="true"  property="remark" style="width:10%;"></display:column>
		</display:table>
	</div> --%>

<script src="${ctx}/js/common.js"></script>
<SCRIPT type="text/javascript">
	var setting = {
		view : {
			selectedMulti : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			beforeClick : zTreeBeforeClick
		}
	};

	function zTreeBeforeClick(treeId, treeNode, clickFlag) {
		if (treeNode.getParentNode() == null) {
			//document.location.href = '${ctx}/clientinfo/list.do?page=${1}&companyid='
				//	+ treeNode.id + '&treeNodeTid=' + treeNode.tId;
			//alert("请选择订单查看导入信息");
			return false;
		} else {
			var Parentnode = treeNode.getParentNode();
			if (Parentnode.getParentNode() == null) {
				//document.location.href = '${ctx}/clientinfo/list.do?page=${1}&projectid='
					//	+ treeNode.id + '&treeNodeTid=' + treeNode.tId;
				//alert("请选择订单查看导入信息");
				return false;
			} else {
				document.location.href = '${ctx}/clientinfo/list.do?page=${1}&orderid='
						+ treeNode.id + '&treeNodeTid=' + treeNode.tId;
			}

		}
	};

	var zNodes = ${userTreeBeans};

	var zTree;
	$(document).ready(function() {

		/* zTree = $.fn.zTree.init($("#tree"), setting, zNodes);
		var checkedNodetid = $('#hiddenInput').val();
		$("#" + checkedNodetid + "_a").attr('class', 'curSelectedNode'); */

		zTree = $.fn.zTree.init($("#tree"), setting, zNodes);
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		// 	var checkedNodetid = $('#hiddenInput').val();
		var tmpcompanyid = $('#tmpcompanyid').val();
		var tmpprojectid = $('#tmpprojectid').val();
		var tmporderid = $('#tmporderid').val();
		var node = null;
		if (tmpcompanyid != "") {
			node = treeObj.getNodeByParam("id", tmpcompanyid, null);
		}
		if (tmpprojectid != "") {
			node = treeObj.getNodeByParam("id", tmpprojectid, null);
		}
		if (tmporderid != "") {
			node = treeObj.getNodeByParam("id", tmporderid, null);
		}
		if (node != null) {
			$("#" + node.tId + "_a").attr('class', 'curSelectedNode');
		}
	});

	//浏览器版本
	var BROWER = {
		mozilla : /firefox/.test(navigator.userAgent.toLowerCase()),
		webkit : /webkit/.test(navigator.userAgent.toLowerCase()),
		opera : /opera/.test(navigator.userAgent.toLowerCase()),
		chrome : /chrome/.test(navigator.userAgent.toLowerCase()),
		msie : /msie/.test(navigator.userAgent.toLowerCase())
	}

	function downloadExcel() {
		var browerType = "";
		// 判断浏览器版本
		if (BROWER.mozilla) {
			browerType = "firefox";
		} else if (BROWER.webkit) {
			browerType = "webkit";
		} else if (BROWER.opera) {
			browerType = "opera";
		} else {
			browerType = "msie";
		}
		window.location.href = "${base}terminal/downExcel.do?browerType="
				+ browerType + "&fileName=template.xls";
	}

	function downloadUserFile() {
		var browerType = "";
		// 判断浏览器版本
		if (BROWER.mozilla) {
			browerType = "firefox";
		} else if (BROWER.webkit) {
			browerType = "webkit";
		} else if (BROWER.opera) {
			browerType = "opera";
		} else {
			browerType = "msie";
		}
		window.location.href = "${base}terminal/downExcel.do?browerType="
				+ browerType + "&fileName=importinstruction.doc";
	}

	function importExcel() {
		var checkedNodetid = $('#hiddenInput').val();
		if (checkedNodetid == "") {
			alert("请选择一个订单");
			return false;
		} else {
			var treeObj = $.fn.zTree.getZTreeObj("tree");
			var node = treeObj.getNodeByTId(checkedNodetid);

			if (node.getParentNode() == null) {
				alert("您选择的是公司,请选择一个订单");
				return false;
			} else {
				var Parentnode = node.getParentNode();
				if (Parentnode.getParentNode() == null) {
					alert("您选择的是项目，请选择一个订单");
					return false;
				} else {
					var html = "<form id=\"submit_form\" method=\"post\" action=\"${base}terminal/importExcel.do\" target=\"exec_target\" enctype=\"multipart/form-data\"> <input id=\"orderid\" name=\"orderid\" type=\"hidden\" value= " + node.id + "><input id = \"input-1\" name=\"images\" type=\"file\" multiple class=\"file\"><br><input class=\"btn btn-primary\" name=\"upload\" type=\"submit\" value=\"上传\" /></form>";
					BootstrapDialog.show({
						title : '客户信息Excel导入',
						message : html
					})
				}
			}
		}
	}

	function addTerminal() {
		var checkedNodetid = $('#hiddenInput').val();
		if (checkedNodetid == "") {
			alert("请选择一个订单");
			return false;
		} else {
			var treeObj = $.fn.zTree.getZTreeObj("tree");
			var node = treeObj.getNodeByTId(checkedNodetid);

			if (node.getParentNode() == null) {
				alert("您选择的是公司,请选择一个订单");
				return false;
			} else {
				var Parentnode = node.getParentNode();
				if (Parentnode.getParentNode() == null) {
					alert("您选择的是项目，请选择一个订单");
					return false;

				} else {
					$('#TerminalModal').modal('show');
				}
			}
		}
	}

	function addSubmitTerminal() {
		//项目id
		var checkedNodetid = $('#hiddenInput').val();
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var node = treeObj.getNodeByTId(checkedNodetid);
		//省份
		var province = $('#province1').val().trim();
		var provinceinfo = "省份";
		var provincelength = 16;
		var provinceflag = checkvalue(province, provinceinfo, provincelength);
		if (provinceflag == false) {
			var provinceInput = document.getElementById("province1");
			provinceInput.focus();
			return provinceflag;
		}
		//城市
		var city = $('#city1').val().trim();
		var cityinfo = "城市";
		var citylength = 16;
		var cityflag = checkvalue(city, cityinfo, citylength);
		if (cityflag == false) {
			var cityInput = document.getElementById("city1");
			cityInput.focus();
			return cityflag;
		}
		//区
		var district = $('#district').val().trim();
		var districtinfo = "区";
		var districtlength = 16;
		var districtflag = checkvalue(district, districtinfo, districtlength);
		if (districtflag == false) {
			var districtInput = document.getElementById("district");
			districtInput.focus();
			return districtflag;
		}
		//业务代码
		var clientcode = $('#clientCode1').val().trim();
		var clientcodelength = 10;
		var checkclientcode = /^\+?[0-9][0-9]*$/;
		if(clientcode == ""){
			alert("请输入业务代码");
			var clientcodeInput = document.getElementById("clientCode1");
			clientcodeInput.focus();
			return false;
		}else if(!checkclientcode.test(clientcode)){
			alert("您输入的业务代码格式不对，请重新输入");
			var clientcodeInput = document.getElementById("clientCode1");
			clientcodeInput.focus();
			return false;
		}else if(Reallength(clientcode) != clientcodelength){
			alert("您输入的业务代码长度不等于"+clientcodelength+ "，请重新输入");
			var clientcodeInput = document.getElementById("clientCode1");
			clientcodeInput.focus();
			return false;
		}
		/* var clientcodeflag = checkRedvalue(clientcode, clientcodeinfo,clientcodelength);
		if (clientcodeflag == false) {
			var clientcodeInput = document.getElementById("clientCode1");
			clientcodeInput.focus();
			return clientcodeflag;
		} */
		
		//单位名称
		var clientname = $('#clientname1').val().trim();
		var clientnameinfo = "业务名称";
		var clientnamelength = 32;
		var clientnameflag = checkvalue(clientname, clientnameinfo,
				clientnamelength);
		if (clientnameflag == false) {
			var clientnameInput = document.getElementById("clientname1");
			clientnameInput.focus();
			return clientnameflag;
		}
		//联系人
		var contact = $('#contact1').val().trim();
		var contactinfo = "联系人";
		var contactlength = 16;
		var contactflag = checkRedvalue(contact, contactinfo, contactlength);
		if (contactflag == false) {
			var contactInput = document.getElementById("contact1");
			contactInput.focus();
			return contactflag;
		}
		//联系人电话
		var telno = $('#telno1').val().trim();
		var checkphoneNum = /^1[34578]\d{9}$/;
		var len = telno.length;
		if (telno == "") {
			alert("请输入手机号码");
			var phoneNumInput = document.getElementById("telno1");
			phoneNumInput.focus();
			return false;
		} else if (!checkphoneNum.test(telno)) {
			alert("您输入的手机号码格式不对");
			var phoneNumInput = document.getElementById("telno1");
			phoneNumInput.focus();
			return false;
		}
		//联系地址
		var address = $('#address1').val().trim();
		var addressinfo = "联系地址";
		var addresslength = 32;
		var addressflag = checkvalue(address, addressinfo, addresslength);
		if (addressflag == false) {
			var addressInput = document.getElementById("address1");
			addressInput.focus();
			return addressflag;
		}
		//备注
		var remark = $('#remark1').val().trim();
		var remarkinfo = "备注";
		var remarklength = 16;
		var remarkflag = checkvalue(remark, remarkinfo, remarklength);
		if (remarkflag == false) {
			var remarkInput = document.getElementById("remark1");
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
			type : "POST",
			url : url,
			data : data,
			contentType : "application/x-www-form-urlencoded;charset=utf-8;",
			dataType : "json",
			cache : false,
			success : function(info) {
				if (info.code == 1) {
					alert(info.msg);
					gotourl('${ctx}/clientinfo/list.do?page=${page}');
				} else {
					alert(info.msg);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(XMLHttpRequest.status + textStatus);
			}

		});

	}

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
		return totalLength;
	}

	//检查非必填项（非空或者超过长度）
	function checkvalue(checkValue, inputname, length) {
		var flag = false;
		if (checkValue != "" && Reallength(checkValue) > length) {
			alert("您输入的" + inputname + "长度超过" + length + "，请重新输入");
			return flag;
		}
	}

	//检查必填项（为空、超过长度）
	function checkRedvalue(checkValue, inputname, length) {
		var flag = false;
		if (checkValue == "") {
			alert("请输入" + inputname);
			return flag;
		} else if (Reallength(checkValue) > length) {
			alert("您输入的" + inputname + "长度超过" + length + "，请重新输入");
			return flag;
		}
	}

	function edit() {
		var selectedIds = getCheckedRowValue("");
		if (selectedIds == "") {
			alert("请选择一个进行编辑");
			return false;
		}

		var selectedId = selectedIds.split(",");
		if (selectedId.length != 1) {
			alert("请选择一个进行编辑");
			return false;
		}

		var objs = document.getElementsByName('checkRow_');
		var ordernum = null;
		var province = null;
		var city = null;
		var district = null;
		var clientname = null;
		var contact = null;
		var telno = null;
		var address = null;
		var remark = null;
		for (var i = 0; i < objs.length; i = i + 1) {
			if (objs[i].checked) {
				//获取列值
				ordernum = $(objs[i]).parents('td').parents('tr')
						.children('td').eq(1).text();
				province = $(objs[i]).parents('td').parents('tr')
						.children('td').eq(2).text();
				city = $(objs[i]).parents('td').parents('tr').children('td')
						.eq(3).text();
				district = $(objs[i]).parents('td').parents('tr')
						.children('td').eq(4).text();
				clientname = $(objs[i]).parents('td').parents('tr').children(
						'td').eq(6).text();
				contact = $(objs[i]).parents('td').parents('tr').children('td')
						.eq(7).text();
				telno = $(objs[i]).parents('td').parents('tr').children('td')
						.eq(8).text();
				address = $(objs[i]).parents('td').parents('tr').children('td')
						.eq(9).text();
				remark = $(objs[i]).parents('td').parents('tr').children('td')
						.eq(10).text();
			}
		}

		var data = {};
		data.client_code = selectedIds;
		data.orderid = ordernum;
		data.province = province;
		data.city = city;
		data.district = district;
		data.client_name = clientname;
		data.contact = contact;
		data.telno = telno;
		data.address = address;
		data.remark = remark;
		$('#AuthModal').modal('show');

		$.ajax({
			url : '${base}clientinfo/clientinfoShow.do',
			type : 'post',
			data : data,
			async : false,
			success : function(data) {
				$("#companyName").val(data.companyname);
				$("#projectName").val(data.projectname);
				$("#ordernum").val(data.orderid);
				$("#clientcode").val(data.client_code);
				$("#province").val(data.province);
				$("#city").val(data.city);
				$("#area").val(data.district);
				$("#address").val(data.address);
				$("#clientname").val(data.client_name);
				$("#contact").val(data.contact);
				$("#telno").val(data.telno);
				$("#remark").val(data.remark);
			}
		});
	}

	function Fallback() {
		gotourl('${ctx}/clientinfo/list.do?page=${page}');
	}

	function addSubmit() {
		//联系人
		var contact = $('#contact').val().trim();
		var contactinfo = "联系人";
		var contactlength = 16;
		var contactflag = checkRedvalue(contact, contactinfo, contactlength);
		if (contactflag == false) {
			var contactInput = document.getElementById("contact");
			contactInput.focus();
			return contactflag;
		}
		//联系人电话
		var telno = $('#telno').val().trim();
		var checkphoneNum = /^1[34578]\d{9}$/;
		var len = telno.length;
		if (telno == "") {
			alert("请输入手机号码");
			var phoneNumInput = document.getElementById("telno");
			phoneNumInput.focus();
			return false;
		} else if (!checkphoneNum.test(telno)) {
			alert("您输入的手机号码格式不对");
			var phoneNumInput = document.getElementById("telno");
			phoneNumInput.focus();
			return false;
		}

		var companyName = $('#companyName').val().trim();
		var projectName = $('#projectName').val().trim();
		var ordernum = $('#ordernum').val().trim();
		var clientcode = $('#clientcode').val().trim();
		var province = $('#province').val().trim();
		var city = $('#city').val().trim();
		var area = $('#area').val().trim();
		var address = $('#address').val().trim();
		var clientname = $('#clientname').val().trim();
		var contact = $('#contact').val().trim();
		var telno = $('#telno').val().trim();
		var remark = $('#remark').val().trim();

		var data = {};
		data.companyname = companyName;
		data.projectname = projectName;
		data.orderid = ordernum;
		data.client_code = clientcode;
		data.province = province;
		data.city = city;
		data.district = area;
		data.address = address;
		data.client_name = clientname;
		data.contact = contact;
		data.telno = telno;
		data.remark = remark;

		$.ajax({
			type : "POST",
			url : "${ctx}/clientinfo/clientinfo.do",
			data : data,
			contentType : "application/x-www-form-urlencoded;charset=utf-8;",
			dataType : "json",
			cache : false,
			success : function(info) {
				if (info.code == 1) {
					alert(info.msg);
					gotourl('${ctx}/clientinfo/list.do?page=${page}');
				} else {
					alert(info.msg);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(XMLHttpRequest.status + textStatus);
			}

		});
	}
</SCRIPT>
<script type="text/javascript">
	jQuery(document).ready(function() {
		var result = "${result}";
		var readdata = "${readdata}";
		var geshi = "${geshi}";
		var iread = true;
		var igeshi = true;
		if (geshi != "" && geshi != null) {
			gotourl('${ctx}/clientinfo/list.do?page=${1}');
			alert(geshi);
		}
		if (result != "" && result != null) {
			if (result == "uploadfail") {
				gotourl('${ctx}/clientinfo/list.do?page=${1}');
				alert('上传失败！');
			}
		}
		if (readdata != "" && readdata != null) {
			gotourl('${ctx}/clientinfo/list.do?page=${1}');
			alert(readdata);
		}
	});
</script>
<script src="${ctx}/js/bootstrap-dialog.min.js"></script>
<script src="${ctx}/js/jquery.ztree.all-3.5.min.js"></script>
<%@ include file="/WEB-INF/views/common/myModal.jsp"%>
<%@ include file="/WEB-INF/views/foot_n.jsp"%>

