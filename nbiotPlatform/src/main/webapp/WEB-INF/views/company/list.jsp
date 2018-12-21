<%@page import="com.routon.plcloud.common.decorator.PageCheckboxDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@ include file="/WEB-INF/views/head_n.jsp" %>
<!-- 		<div class="panel panel-default container" style="width: 1560px; margin-bottom: 20px;"> -->
		<div class="container" style=" width: 1560px;">
		<div class="panel panel-default" style=" width: 1530px; margin-left: -15px;">
  		<div class="panel-heading" style="width:1528px;height:48px;">
  			<div class="btn-group">
<%--   			<form class="form-inline" role="form" id="queryform" name="queryform" action="${ctx}/company/list.do"  method="post">  		 --%>
<!--   			<div class="btn-group"> -->
<%--   				<input id="companyname" name="companyname" type="text" class="form-control" placeholder="请输入公司名称" value="${companyname}"> --%>
  				
<!--   			</div> -->
<!--   			<div class="btn-group"> -->
<!--   				<button type="submit" class="btn btn-primary" >查询</button> -->
<!--   			</div> -->
<!--   			</form> -->
 				<h5>  订单    > 公司管理 </h5>
  			</div>
			<div class="pull-right">
				<c:if test="${(!empty userPrivilege['90000101'])}">
				<div class="btn-group">
  					<button type="button" class="btn btn-primary" onclick="gotourl('${ctx}/company/add.do?page=${page}')">新增</button>
  				</div>
  				</c:if>
<%--   				<c:if test="${(!empty userPrivilege['90000102'])}"> --%>
<!--   				<div class="btn-group"> -->
<!--   					<button type="button" class="btn btn-primary" onclick="edit()">编辑</button> -->
  					
<!--   				</div> -->
<%--   				</c:if> --%>
  				<c:if test="${(!empty userPrivilege['90000103'])}">
  				<div class="btn-group">
  					<button type="button" class="btn btn-danger" onclick="deleteCompany()">删除</button>
  				</div>
  				</c:if>
  			</div> 
  		</div>
  		
  		
  		<div class="panel-body" style ="height : 64px;">
  			<div class="btn-group" style ="height : 34px;">
  			<form class="form-inline" role="form" id="queryform" name="queryform" action="${ctx}/company/list.do"  method="post">  
  				<div class="btn-group">
  				<input id="companyname" name="companyname" type="text" class="form-control" placeholder="请输入公司名称" value="${companyname}" onkeyup="this.value=this.value.replace(/[^\uFF00-\uFFFF\u4e00-\u9fa5\w\@\&\-]/g,'')">
  				
	  			</div>
	  			<div class="btn-group">
	  				<button type="submit" class="btn btn-primary" >查询</button>
	  			</div>
  			</form>
  			</div>
  		</div>
  		
		<!-- table -->
		<display:table name="requestScope.pageList" id="curPage" class="table table-striped" sort="external"
			requestURI="list.do"
			decorator="com.routon.plcloud.common.decorator.PageCompanyDecorator"
			export="false" >
			<display:column property="id" title="<%=PageCheckboxDecorator.getTitle(pageContext)%>" decorator="com.routon.plcloud.common.decorator.PageCheckboxDecorator" media="html" style="width:2%;"/>
			<display:column title="ID"  property="id"  sortable="true"  style="width:5%;" />
			<display:column title="公司名称" sortable="true"  property="companyname" style="width:10%;"></display:column>
			<display:column title="公司地址"  property="address"  sortable="true"  style="width:15%;" />
			<display:column title="统一社会信用代码"  property="organizationcode"  sortable="true"  style="width:10%;" />
			<display:column title="客户公司联系人"  property="contactname"  sortable="true"  style="width:8%;" />
			<display:column title="联系人电话"  property="contactphone"  sortable="true"  style="width:10%;" />
			<display:column title="我方销售人员"  property="realename"  sortable="false"  style="width:10%;" />	
			<display:column title="创建时间"  property="createtime"  sortable="true"  style="width:15%;" decorator="com.routon.plcloud.common.decorator.PageDateTimeDecorator"></display:column>
			<display:column title="修改时间"  property="moditytime"  sortable="true"  style="width:15%;" decorator="com.routon.plcloud.common.decorator.PageDateTimeDecorator"></display:column>
		</display:table>
 		
	</div>	
	
	<%@ include file="/WEB-INF/views/common/pagination.jsp" %>
</div>

<script src="${ctx}/js/common.js"></script>
<SCRIPT type="text/javascript">

function editCompany(obj){
		var companyId = $(obj).parents('tr').children('td').eq(1).text();
		edit(companyId);
}


function edit(companyId){
	
	var selectedIds = companyId;
	
// 	var selectedIds = getCheckedRowValue("");
// 	if(selectedIds==""){
// 		alert("请选择一个进行编辑");
// 		return false;
// 	}
// 	var selectedId = selectedIds.split(",");
// 	if (selectedId.length != 1) {
// 		alert("请选择一个进行编辑");
// 		return false;
// 	}
	
	/* var cityname;
	var addressname;
	$.ajax({
		url:'${base}company/companyshow.do',
		type:'post',
		data:{id : selectedIds},
		async:false,
		success:function(data){
			alert("data="+data);
			cityname = data.obj;
			alert("cityname="+cityname);
			addressname = data.obj1;
			alert("addressname="+addressname);
		}
	}); */ 
	gotourl('${ctx}/company/edit.do?page=${page}&id='+selectedIds); 
}
function deleteCompany(){
	var  num = getCheckedRowValue("");
	var  selectedIds=""; 
	//将字符串以空格分开
	var strArr = num.split(' ');
	for(var i=0,len=strArr.length;i<len;i++){
        if(!isNaN(parseInt(strArr[i]))){
        	if(selectedIds == "") {
        		selectedIds += strArr[i];
        	}
        	else{
        		selectedIds += ",";
        		selectedIds += strArr[i];
        	}
        }
    }	
// 	var selectedIds = getCheckedRowValue("");
	if(selectedIds == ""){
		alert("至少选择一个删除");
		return false;
	}
	del(selectedIds,'${ctx}/company/delete.do', g_ctx + '/company/list.do?page=${1}')
}


</SCRIPT>	
 	
<%@ include file="/WEB-INF/views/foot_n.jsp" %>
<%@ include file="/WEB-INF/views/common/myModal.jsp" %>