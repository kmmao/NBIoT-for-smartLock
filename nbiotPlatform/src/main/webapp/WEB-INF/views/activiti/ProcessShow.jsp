<%@page import="com.routon.plcloud.common.decorator.PageCheckboxDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<script src="${ctx}/js/jquery-easyui/jquery.easyui.min.js"></script>
	<table id = "table1" width="100%" class="need-border">
		<thead>
			<tr>
				<th>流程ID</th>
				<th>DeploymentId</th>
				<th>名称</th>
				<th>KEY</th>
				<th>版本号</th>
				<th>XML</th>
				<th>图片</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageList}" var="process">
				<tr>
					<td class='process-id'>${process.id }</td>
					<td>${process.deploymentId }</td>
					<td class='process-name'>${process.name }</td>
					<td>${process.key }</td>
					<td>${process.version }</td>
					<td><a target="_blank" href="${ctx}/activiti/getProcessImageAndXml.do?processDefinitionId=${process.id}&resourceType=xml">${process.resourceName }</a></td>
					<td><a target="_blank" href="${ctx}/activiti/getProcessImageAndXml.do?processDefinitionId=${process.id}&resourceType=image">${process.diagramResourceName }</a></td>
					<td><button id="startup" onclick="startProcess(this)">启动</button></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
<script>
	function startProcess(obj){
		//var $ele = $(this);
		var id = $(obj).parents('tr').find('.process-id').text()
		$.ajax({
			url:'${base}activiti/startPrpces.do',
			type:'post',
			data:{ processDefinitionId : id },
			async:false,
			success:function(data){
				if(data == "success"){
					alert("流程已启动");
				}
			}
		});
	}
</script>
<%@ include file="/WEB-INF/views/foot_n.jsp" %>