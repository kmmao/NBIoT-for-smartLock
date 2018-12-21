<%@page import="com.routon.plcloud.common.decorator.PageCheckboxDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@ include file="/WEB-INF/views/head_n.jsp" %>
<div class="panel panel-default">
  		<div class="panel-heading" style="width:1528px;height:48px;">
  			<h5><strong>指令日志管理</strong></h5>
  		</div>
  		<div class="panel-body" style="padding-top: 0px;padding-left: 0px;padding-right: 0px;padding-bottom: 0px;">
		<display:table name="${pageList}" id="curPage" class="table table-striped" sort="external"
			requestURI="commandloglist.do"
			decorator="com.routon.plcloud.common.decorator.PageDecorator"
			export="false">
			<display:column property="id" title="<%=PageCheckboxDecorator.getTitle(pageContext)%>" decorator="com.routon.plcloud.common.decorator.PageCheckboxDecorator" media="html" style="width:2%;"/>
			<display:column title="ID"  property="id"  sortable="true"  style="width:5%;" />
			<display:column title="姓名"  property="name"  sortable="true"  style="width:5%;" />
			<display:column title="设备id" sortable="true"  property="deviceid" style="width:15%;"></display:column>
			<display:column title="指令类型"  sortProperty="commandtype"  sortable="true"  style="width:20%;">
			<c:choose>
					<c:when test="${curPage.commandtype == 1}">
					下发白名单
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${curPage.commandtype == 2}">
					删除白名单
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${curPage.commandtype == 3}">
					开关门操作
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${curPage.commandtype == 4}">
					远程开锁
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${curPage.commandtype == 5}">
					同步设备网络时间
					</c:when>
				</c:choose>
			
			
			
			
			
			</display:column>
			<display:column title="指令状态"  sortProperty="commandstate"  sortable="true"  style="width:10%;">
			<c:choose>
					<c:when test="${curPage.commandstate == \"send\"}">
					已发送
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${curPage.commandstate == \"timeout\"}">
					超时
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${curPage.commandstate == \"delivered\"}">
					命令已下达
					</c:when>
				</c:choose>
			<c:choose>
					<c:when test="${curPage.commandstate == \"success\"}">
					指令下发成功
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${curPage.commandstate == \"failed\"}">
					下发失败
					</c:when>
				</c:choose>
			
	
			</display:column>		
			<display:column title="指令id"  property="commandid"  sortable="true"  style="width:10%;">

			
			</display:column>
			<display:column title="指令下发时间"  property="commandtime"  sortable="true"  style="width:10%;"></display:column>	
			<display:column title="指令内容"  property="commandstr"  sortable="true"  style="width:10%;">

			</display:column>
		</display:table>
		</div>
		<%@ include file="/WEB-INF/views/common/pagination.jsp" %>
</div>
	
<script src="${ctx}/js/common.js"></script>