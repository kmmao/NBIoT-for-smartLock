<%@page import="com.routon.plcloud.common.decorator.PageCheckboxDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@ include file="/WEB-INF/views/head_n.jsp" %>
<div class="panel panel-default">
  		<div class="panel-heading" style="width:1528px;height:48px;">
  			<h5><strong>访客记录管理</strong></h5>
  		</div>
  		<div class="panel-body" style="padding-top: 0px;padding-left: 0px;padding-right: 0px;padding-bottom: 0px;">
		<display:table name="${pageList}" id="curPage" class="table table-striped" sort="external"
			requestURI="visitrecordlist.do"
			decorator="com.routon.plcloud.common.decorator.PageDecorator"
			export="false">
			<display:column property="face_id" title="<%=PageCheckboxDecorator.getTitle(pageContext)%>" decorator="com.routon.plcloud.common.decorator.PageCheckboxDecorator" media="html" style="width:2%;"/>
			<display:column title="人员ID"  property="face_id"  sortable="true"  style="width:5%;" />
			<display:column title="姓名"  property="name"  sortable="true"  style="width:5%;" />
			<display:column title="房间号" sortable="true"  property="roomnum" style="width:15%;"></display:column>
			<display:column title="是否在有效时间内"  sortProperty="validtime"  sortable="true"  style="width:20%;">
				<c:choose>
					<c:when test="${curPage.validtime == \"00\"}">
					不在时间段内
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${curPage.validtime == \"01\"}">
					在时间段内
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${curPage.validtime == \"FF\"}">
					为无效值
					</c:when>
				</c:choose>
			</display:column>

			<display:column title="门状态"  sortProperty="doorstate"  sortable="true"  style="width:10%;">
				<c:choose>
					<c:when test="${curPage.doorstate == \"01\"}">
					开门
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${curPage.doorstate == \"02\"}">
					关门
					</c:when>
				</c:choose>		
			</display:column>		
			<display:column title="锁状态"  sortProperty="lockstate"  sortable="true"  style="width:10%;">
			<c:choose>
					<c:when test="${curPage.lockstate == \"01\"}">
					门锁开
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${curPage.lockstate == \"02\"}">
					门锁关
					</c:when>
				</c:choose>	
			
			</display:column>
			<display:column title="访问时间"  property="visit_time"  sortable="true"  style="width:10%;"></display:column>	
			<display:column title="操作类型"  sortProperty="operationtype"  sortable="true"  style="width:10%;">
			<c:choose>
					<c:when test="${curPage.operationtype == \"01\"}">
					刷脸
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${curPage.operationtype == \"02\"}">
					身份证
					</c:when>
				</c:choose>	
				<c:choose>
					<c:when test="${curPage.operationtype == \"03\"}">
					刷M1卡
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${curPage.operationtype == \"04\"}">
					身份证+脸
					</c:when>
				</c:choose>	
				<c:choose>
					<c:when test="${curPage.operationtype == \"05\"}">
					IC卡+脸
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${curPage.operationtype == \"06\"}">
					刷NFC卡
					</c:when>
				</c:choose>	
				<c:choose>
					<c:when test="${curPage.operationtype == \"07\"}">
					指纹
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${curPage.operationtype == \"08\"}">
					蓝牙开门记录
					</c:when>
				</c:choose>	
					<c:choose>
					<c:when test="${curPage.operationtype == \"0A\"}">
					特殊事件
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${curPage.operationtype == \"10\"}">
					无效M1卡
					</c:when>
				</c:choose>
					<c:choose>
					<c:when test="${curPage.operationtype == \"11\"}">
					无效NFC卡
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${curPage.operationtype == \"12\"}">
					无效身份证
					</c:when>
				</c:choose>
					<c:choose>
					<c:when test="${curPage.operationtype == \"14\"}">
					无效指纹
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${curPage.operationtype == \"30\"}">
					钥匙开门
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${curPage.operationtype == \"34\"}">
					远程开锁
					</c:when>
				</c:choose>
			
			
			</display:column>
		</display:table>
		</div>
		<%@ include file="/WEB-INF/views/common/pagination.jsp" %>
</div>
	
<script src="${ctx}/js/common.js"></script>