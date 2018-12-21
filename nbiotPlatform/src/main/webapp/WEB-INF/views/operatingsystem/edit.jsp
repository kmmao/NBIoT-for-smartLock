<%@page import="com.routon.plcloud.common.decorator.PageCheckboxDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@ include file="/WEB-INF/views/head_n.jsp" %>
<link rel="stylesheet" href="${ctx}/css/zTreeStyle.css">
<div class="panel panel-default">
  		<div class="panel-heading">
			<div class="pull-right">
				<a class="btn btn-primary" href="${ctx}/operatingsystem/list.do?page=${page}" role="button">返回</a>
			</div>
			<c:choose>
				<c:when test="${opsystem.id!=null}">
			    	<h5>编辑-<strong>${opsystem.operatingsystemname}</strong></h5>
			   	</c:when>
			    <c:otherwise>
			    	<h5>新增<strong>${opsystem.operatingsystemname}</strong></h5>
			   	</c:otherwise>
			</c:choose>
  		</div>
  		<div class="panel-body">
    		
			<form:form id="opsystemForm" name="opsystemForm" class="form-horizontal" role="form" method="post" enctype="multipart/form-data" >
				<input id="id" name="id" type="hidden" value="${opsystem.id}" >
			  <div class="form-group">
			    <label for="title" class="col-sm-2 control-label">操作系统名称</label>
			    <div class="col-sm-4">
			      <input type="text" class="form-control" id="operatingsystemname" name="operatingsystemname" placeholder="操作系统名称" value="${opsystem.operatingsystemname}">
			    </div>
			  </div>
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			      <button id="savebtn" name="savebtn" type="button" class="btn btn-primary" 
			      		onclick="save('#opsystemForm', 'save.do', g_ctx + '/operatingsystem/list.do?page=${page}')">保存</button>
			    </div>
			  </div>
			</form:form>
    		
  		</div>
</div>

<%@ include file="/WEB-INF/views/common/myModal.jsp" %>
<script src="${ctx}/js/common.js"></script>		
<script src="${ctx}/js/jquery.ztree.all-3.5.min.js"></script>	

<%@ include file="/WEB-INF/views/foot_n.jsp" %>

