<%@page import="com.routon.plcloud.common.decorator.PageCheckboxDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@ include file="/WEB-INF/views/head_n.jsp" %>

	<div class="panel panel-default">
  		<div class="panel-heading">
  			<div class="btn-group">
  			<form class="form-inline" role="form" id="queryform" name="queryform" action="${ctx}/operatingsystem/list.do"  method="post">  		
  			<div class="btn-group">
  				<input id="operatingsystemname" name="operatingsystemname" type="text" class="form-control" placeholder="请输入系统名称" value="${operatingsystemname}">
  				
  			</div>
  			<div class="btn-group">
  				<button type="submit" class="btn btn-primary" >查询</button>
  			</div>
  			</form>
  			</div>
  			
			<div class="pull-right">
				<c:if test="${(!empty userPrivilege['40000401'])}">
				<div class="btn-group">
  					<button type="button" class="btn btn-primary" onclick="gotourl('${ctx}/operatingsystem/add.do?page=${page}')">新增</button>
  				</div>
  				</c:if>
  				<c:if test="${(!empty userPrivilege['40000402'])}">
  				<div class="btn-group">
  					<button type="button" class="btn btn-primary" onclick="edit()">编辑</button>
  					
  				</div>
  				</c:if>
  				<c:if test="${(!empty userPrivilege['40000403'])}">
  				<div class="btn-group">
  					<button type="button" class="btn btn-danger" onclick="deleteOperatingsystem()">删除</button>
  				</div>
  				</c:if>
  			</div> 
  		</div>
  		
		<!-- table -->
		<display:table name="requestScope.pageList" id="curPage" class="table table-striped" sort="external"
			requestURI="list.do"
			decorator="com.routon.plcloud.common.decorator.PageDecorator"
			export="false">
			<display:column property="id" title="<%=PageCheckboxDecorator.getTitle(pageContext)%>" decorator="com.routon.plcloud.common.decorator.PageCheckboxDecorator" media="html" style="width:2%;"/>
			<display:column title="ID"  property="id"  sortable="true"  style="width:5%;" />
			<display:column title="系统名称" sortable="true"  property="operatingsystemname" style="width:15%;">
			</display:column>
			<display:column title="创建时间"  property="createTime"  sortable="true"  style="width:20%;" decorator="com.routon.plcloud.common.decorator.PageDateTimeDecorator"></display:column>
			<display:column title="修改时间"  property="modityTime"  sortable="true"  style="width:20%;" decorator="com.routon.plcloud.common.decorator.PageDateTimeDecorator"></display:column>
		</display:table>
 		
	</div>	
	<%@ include file="/WEB-INF/views/common/pagination.jsp" %>
	
	
	
<script src="${ctx}/js/common.js"></script>
<SCRIPT type="text/javascript">
function edit(){
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
	gotourl('${ctx}/operatingsystem/edit.do?page=${page}&id='+selectedIds);
}
function deleteOperatingsystem(){
	var selectedIds = getCheckedRowValue("");
	if(selectedIds == ""){
		alert("至少选择一个删除!");
		return false;
	}
	del(selectedIds,'${ctx}/operatingsystem/delete.do', g_ctx + '/operatingsystem/list.do?page=${page}')
}
</SCRIPT>	
 	
<%@ include file="/WEB-INF/views/foot_n.jsp" %>

