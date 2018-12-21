<%@page import="com.routon.plcloud.common.decorator.PageCheckboxDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@ include file="/WEB-INF/views/head_n.jsp" %>
<link rel="stylesheet" href="${ctx}/css/zTreeStyle.css">
<div class="panel panel-default">
  		<div class="panel-heading">
			<div class="pull-right">
				<a class="btn btn-primary" href="${ctx}/company/list.do?page=${page}" role="button">返回</a>
			</div>
			<c:choose>
				<c:when test="${company.id!=null}">
			    	<h5>编辑-<strong>${company.companyname}</strong></h5>
			   	</c:when>
			    <c:otherwise>
			    	<h5>新增</h5>
			   	</c:otherwise>
			</c:choose>
  		</div>
  		<div class="panel-body">
    		
			<form:form id="companyForm" name="companyForm" class="form-horizontal" role="form" method="post" enctype="multipart/form-data" >
				<input id="id" name="id" type="hidden" value="${company.id}" >
<%-- 				<input id="menuIds" name="menuIds" type="hidden" value="${role.menuIds}" > --%>
					<input id="cityname" name="cityname" type="hidden" value="${company.city}"/>
					<input id="province_aaa" name="province_aaa" type="hidden" value="${company.province}"/>
					<input id="areaname" name="areaname" type="hidden" value="${company.area}"/>
					<input id="city_aaa" name="city_aaa" type="hidden" value="${company.city}"/>
<!-- 			  <div class="form-group">		    			     -->
		    <c:choose>
				<c:when test="${company.id!=null}">
				<div class="form-group">
					<label for="title" class="col-sm-2 control-label" style = "color:red;"> 公司注册名称</label>
			    	<div class="col-sm-4">
			    	 <input type="text" class="form-control" id="companyregname" name="companyregname" placeholder="长度不能超过20个字符" value="${company.companyregname}" Readonly >
			   		</div>	
			   	</div>
			   	<div class="form-group">
			   	 <label for="title" class="col-sm-2 control-label" style = "color:red;">公司名称</label>
			    	<div class="col-sm-4">
			    	 <input type="text" class="form-control" id="companyname" name="companyname" placeholder="长度不能超过20个字符" value="${company.companyname}" onkeyup="this.value=this.value.replace(/[^\uFF00-\uFFFF\u4e00-\u9fa5\w\@\&\-]/g,'')" >
			   	 </div>	
			   	 </div>	    	
			   	</c:when>
			    <c:otherwise>
			    	<div class="form-group">
			  		  <label for="title" class="col-sm-2 control-label" style = "color:red;">公司注册名称</label>
			    	<div class="col-sm-4">
			    	 <input type="text" class="form-control" id="companyregname" name="companyregname" placeholder="长度不能超过20个字符" value="${company.companyname}" onkeyup="this.value=this.value.replace(/[^\uFF00-\uFFFF\u4e00-\u9fa5\w\@\&\-]/g,'')">
			   	 </div>
			   	  </div>
			   	</c:otherwise>
			</c:choose>		   
<!-- 			  </div> -->
			  
<!-- 			  <div class="form-group"> -->
<!-- 			    <label for="title" class="col-sm-2 control-label" style = "color:red;">公司名称</label> -->
<!-- 			    <div class="col-sm-4"> -->
<%-- 			      <input type="text" class="form-control" id="companyname" name="companyname" placeholder="长度不能超过20个字符" value="${company.companyname}"> --%>
<!-- 			    </div> -->
<!-- 			  </div> -->
			  
			   <div class="form-group">
			    <label for="title" class="col-sm-2 control-label" style = "color:red;">法人代表</label>
			    <div class="col-sm-4">
			      <input type="text" class="form-control" id="companyrep" name="companyrep" placeholder="长度不能超过25个字符" value="${company.companyrep}" onkeyup="this.value=this.value.replace(/[^\uFF00-\uFFFF\u4e00-\u9fa5\w\@\&\-]/g,'')">
			    </div>
			  </div>
			  
			  <div class="form-group">
			    <label for="title" class="col-sm-2 control-label">英文名称</label>
			    <div class="col-sm-4">
			      <input type="text" class="form-control" id="englishname" name="englishname" placeholder="长度不能超过120个字符" value="${company.englishname}">
			    </div>
			  </div>
			  
			  <div class="form-group">
			    <label for="title" class="col-sm-2 control-label">名称拼音</label>
			    <div class="col-sm-4">
			      <input type="text" class="form-control" id="namespell" name="namespell" placeholder="长度不能超过120个字符" value="${company.namespell}">
			    </div>
			  </div>
			  
<!-- 			  <div class="form-group"> -->
<!-- 			    <label for="title" class="col-sm-2 control-label" style = "color:red;">纳税人识别号</label> -->
<!-- 			    <div class="col-sm-4"> -->
<%-- 			      <input type="text" class="form-control" id="identifynum" name="identifynum" placeholder="长度不能超过25个字符" value="${company.identifynum}"> --%>
<!-- 			    </div> -->
<!-- 			  </div> -->
			  
<!-- 			  <div class="form-group"> -->
<!-- 			    <label for="title" class="col-sm-2 control-label" style = "color:red;">工商注册号</label> -->
<!-- 			    <div class="col-sm-4"> -->
<%-- 			      <input type="text" class="form-control" id="regnum" name="regnum" placeholder="长度不能超过25个字符" value="${company.regnum}"> --%>
<!-- 			    </div> -->
<!-- 			  </div> -->
			 
<!-- 			 用组织机构代码代替统一社会信用代码  -->
			  <div class="form-group">
			    <label for="title" class="col-sm-2 control-label" style = "color:red;">统一社会信用代码</label>
			    <div class="col-sm-4">
			      <input type="text" class="form-control" id="organizationcode" name="organizationcode" placeholder="18位数字或字母" value="${company.organizationcode}">
			    </div>
			  </div>
			  
			  <div class="form-group">
			    <label for="title" class="col-sm-2 control-label">行业</label>
			    <div class="col-sm-4">
			      <input type="text" class="form-control" id="trade" name="trade" placeholder="长度不能超过12个字符" value="${company.trade}">
			    </div>
			  </div>
			  
		
			  
<!-- 			    <div class="form-group"> -->
<!-- 			  		<label for="title" class="col-sm-2 control-label" style = "color:red;">状态</label> -->
<!-- 			  		<div class="col-sm-4"> -->
<!-- 			  		 <select id="status" name="status" class="form-control" >			     -->
<%-- 			      		<option value="有效" <c:if test="${company.status=='有效'}">selected="selected"</c:if> >有效</option> --%>
<%-- 						<option value="无效" <c:if test="${company.status=='无效'}">selected="selected"</c:if> >无效</option> --%>
<!-- 					 </select> -->
<!-- 				   </div> -->
<!-- 			    </div> -->
			  
			  <div class="form-group">
			    <label for="title" class="col-sm-2 control-label" style = "color:red;">联系人姓名（客户商务）</label>
			    <div class="col-sm-4">
			      <input type="text" class="form-control" id="contactname" name="contactname" placeholder="长度不能超过25个字符" value="${company.contactname}" onkeyup="this.value=this.value.replace(/[^\uFF00-\uFFFF\u4e00-\u9fa5\w\@\&\-]/g,'')">
			    </div>
			  </div>
			  
			   <div class="form-group">
			    <label for="title" class="col-sm-2 control-label" style = "color:red;">联系人手机</label>
			    <div class="col-sm-4">
			      <input type="text" class="form-control" id="contactphone" name="contactphone" placeholder="11位数字" value="${company.contactphone}">
			    </div>
			  </div>
			  
			  <div class="form-group">
			    <label for="title" class="col-sm-2 control-label">联系人固定电话</label>
			    <div class="col-sm-4">
			      <input type="text" class="form-control" id="contactfixedphone" name="contactfixedphone" placeholder="长度不能超过12个字符" value="${company.contactfixedphone}" onkeyup="this.value=this.value.replace(/[^\uFF00-\uFFFF\u4e00-\u9fa5\w\@\&\-]/g,'')">
			    </div>
			  </div>
			  
			  <div class="form-group">
			    <label for="title" class="col-sm-2 control-label">电子邮件</label>
			    <div class="col-sm-4">
			      <input type="text" class="form-control" id="email" name="email" placeholder="长度不能超过50个字符" value="${company.email}">
			    </div>
			  </div>
			  
			  <div class="form-group">
			    <label for="title" class="col-sm-2 control-label" style = "color:red;">我方销售人员</label>
			    <div class="col-sm-4">
				    <select id="salename" name="salename" class="form-control"  >
				      <option value="">--请选择公司销售人员姓名--</option> 
<%-- 				      <c:choose> --%>
<%-- 						      <c:when test="${company.salename != null}"> --%>
<%-- 									<option value="${company.salename}">${company.salename}</option> --%>
<%-- 							  </c:when> --%>
<%-- 					  </c:choose> --%>

<%-- <option value="${item1.project}" <c:if test="${user.project==item1.project}">selected="selected"</c:if>>${item1.project}</option> --%>

						       
							  <c:forEach var="item" items="${users}">
								<option value="${item.key}" <c:if test="${company.salename==item.key}">selected="selected"</c:if> >${item.value}</option>
							  </c:forEach> 
					  </select>
			   
			    </div>
			  </div>
			  
					  
				      

			  <div class="form-group">
			    <label for="title" class="col-sm-2 control-label">国家（地区）</label>
			    <div class="col-sm-4">
			    	<input type="text" class="form-control" id="country" name="country" placeholder="长度不能超过25个字符" value="${'中华人民共和国'}" readonly>
			    </div>
			  </div>
			  
			   <div class="form-group">
			    <label for="title" class="col-sm-2 control-label">省（州）</label>
			    <div class="col-sm-4">
			      <%-- <input type="text" class="form-control" id="province" name="province" placeholder="长度不能超过25个字符" value="${company.province}"> --%>
			    <%-- <input type="text" class="form-control" id="province" name="province" placeholder="长度不能超过25个字符" value="${company.province}"> --%>
			    <!-- <select class="form-control" id="s_province" name="s_province"></select> -->
			    	<%--<select id="province" name="province" class="form-control" onchange="onSelectChange(this,'city');">
			    		 <option value="${company.province}" <c:if test="${company.province != null}">selected="selected"</c:if> >${company.province}</option> --%>
			    		<%-- <option value="${company.province}" ${province=="${company.province}"?'selected':''}>${company.province}</option> --%>
						<%-- <option value="" >${company.province}</option>  
			    	</select>--%>
			    	
			    	<select id="province" name="province" class="form-control"  onchange="onSelectChange(this,'city');">
				      <%-- <option value="">--请选择省--</option> 
							  <c:forEach var="item" items="${provinces}">
								<option value="${item.key}" <c:if test="${company.province==item.key}">selected="selected"</c:if> >${item.value}</option>
							  </c:forEach> --%> 
					      <option value="">―请选择省―</option>
				      	  <c:forEach var="item" items="${provinces}">
								<option value="${item.key}" <c:if test="${company.province==item.key}">selected="selected"</c:if>>${item.value}</option>
						  </c:forEach>
					</select>
					  
			    </div>
			  </div>
			  
			  <div class="form-group">
			    <label for="title" class="col-sm-2 control-label">市（县）</label>
			    <div class="col-sm-4">
			      <%-- <input type="text" class="form-control" id="city" name="city" placeholder="长度不能超过25个字符" value="${company.city}"> --%>
			      <!-- <select class="form-control" id="s_city" name="s_city" ></select> -->
			      <select id="city" name="city" class="form-control" onchange="onSelectChange(this,'area');">
			      	<option value="">―请选择市―</option>
			      </select>
			    </div>
			  </div>
			  
			  <div class="form-group">
			    <label for="title" class="col-sm-2 control-label" >区（镇）</label>
			    <div class="col-sm-4">
			      <%-- <input type="text" class="form-control" id="address" name="address" placeholder="长度不能超过250个字符" value="${company.address}"> --%>
			      <select name="area" id="area" class="form-control">
			      	<option value="">―请选择区―</option>
			      </select>
			    </div>
			  </div>
			  
			  <div class="form-group">
			    <label for="title" class="col-sm-2 control-label" style = "color :red;">地址</label>
			    <div class="col-sm-4">
			      <input type="text" class="form-control" id="address" name="address" placeholder="长度不能超过250个字符" value="${company.address}" onkeyup="this.value=this.value.replace(/[^\uFF00-\uFFFF\u4e00-\u9fa5\w\@\&\-]/g,'')"> 
			    </div>
			  </div>
			  
			  <div class="form-group">
			    <label for="title" class="col-sm-2 control-label">邮政编码</label>
			    <div class="col-sm-4">
			      <input type="text" class="form-control" id="zipcode" name="zipcode" placeholder="长度不能超过25个字符" value="${company.zipcode}" onkeyup="this.value=this.value.replace(/[^\uFF00-\uFFFF\u4e00-\u9fa5\w\@\&\-]/g,'')">
			    </div>
			  </div>
			  
			  
			  
<!-- 			  <div class="form-group"> -->
<!-- 			    <label for="url" class="col-sm-2 control-label">权限菜单</label> -->
<!-- 			    <div class="col-sm-4"> -->
<!-- 			    	<div class="input-group"> -->
<%-- 			    		 <input type="text" class="form-control" id="menuNames" name="menuNames" readonly placeholder="请选择权限菜单" value="${role.menuNames}" > --%>
<!-- 					      <span class="input-group-btn"> -->
<!-- 					        <button class="btn btn-default" type="button" data-toggle="modal" data-target="#mytree">选择<span class="caret"></span></button> -->
<!-- 					      </span> -->
<!-- 			    	</div> -->
<!-- 			    </div> -->
<!-- 			  </div> -->

<!-- 			 <div class="form-group"> -->
<!-- 			    <label for="url" class="col-sm-2 control-label">备注</label> -->
<!-- 			    <div class="col-sm-5"> -->
<%-- 			      <textarea id="remark" name="remark" class="form-control" rows="5">${role.remark}</textarea> --%>
<!-- 			    </div> -->
<!-- 			  </div> -->
			  
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
<%-- 			       <button id="savebtn" name="savebtn" type="button" class="btn btn-default"  href="${ctx}/company/list.do?page=${page}" >关闭</button> --%>
			    	 
			    
			     <c:choose>
			     	<c:when test="${editpri==0}">
				      <button id="savebtn" name="savebtn" type="button" class="btn btn-primary" 
				      		onclick="saveCompany()" disabled>保存</button>
				     </c:when>
				     <c:otherwise>
				     <button id="savebtn" name="savebtn" type="button" class="btn btn-primary" 
				      		onclick="saveCompany()" >保存</button>
				     </c:otherwise>
			     </c:choose>
			    </div>
			  </div>
			  
			</form:form>
    		
  		</div>
</div>

<!-- <div class="modal fade" id="mytree" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"> -->
<!--   <div class="modal-dialog"> -->
<!--     <div class="modal-content" style="width: 300px;"> -->
<!--       <div class="modal-header"> -->
<!--         <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button> -->
<!--         <h4 class="modal-title" id="myModalLabel">选择菜单</h4> -->
<!--       </div> -->
<!--       <div class="modal-body"> -->
<!--         <ul id="treeDemo" class="ztree"></ul> -->
<!--       </div> -->
<!--       <div class="modal-footer"> -->
<!--         <button type="button" class="btn btn-primary" onclick="select()">选择</button> -->
<!--       </div> -->
<!--     </div> -->
<!--   </div> -->
<!-- </div> -->

<%@ include file="/WEB-INF/views/common/myModal.jsp" %>
<script src="${ctx}/js/common.js"></script>	
<script src="${ctx}/js/jquery.ztree.all-3.5.min.js"></script>
<%-- <script src="${ctx}/js/jquery-1.7.min.js"></script> 
<script src="${ctx}/js/json-minified.js"></script>	--%>
<%-- <script class="resources library" src="${ctx}/js/area.js" type="text/javascript"></script> --%>
<script type="text/javascript">
function onSelectChange(obj,toSelId){
	setSelect(obj.value,toSelId);
}

function setSelect(fromSelVal,toSelId){
	/* document.getElementById(toSelId).innerHTML="";  */
	$.ajax({  
		url:'${base}company/provinceshow.do',
		type:'post',
		data:{"code":fromSelVal,"grade":toSelId},
		async:false,
	    success: function(data){
	    
	    var dist=document.getElementById(toSelId);
	    var result = eval("("+data+")");
	    
	    if(toSelId == 'city'){
	    	dist.innerHTML="";
	    	dist.options.add(new Option('--请选择市--',''));
	    	document.getElementById('area').innerHTML=""; 
	    	document.getElementById('area').options.add(new Option('--请选择区--',''));
	    	for(var o in result) {
	    		dist.options.add(new Option(result[o],o));
		    }
	    }else if(toSelId == 'area'){
	    	dist.innerHTML="";
	    	dist.options.add(new Option('--请选择区--',''));
	    	for(var o in result) {
	    		dist.options.add(new Option(result[o],o));
		    }
	    }
	  }, 
	});
}

	jQuery(document).ready(function() {
		var province_aaa = $("#province_aaa").val();
		setSelect(province_aaa, 'city');
		var cityName = $("#cityname").val();
		$("#city option[value='"+cityName + "']").attr("selected", true);
		
		var city_aaa = $("#city_aaa").val();
		setSelect(city_aaa, 'area');
		var areaName = $("#areaname").val();
		$("#area option[value='"+areaName + "']").attr("selected", true);
	});
	
</script>
				    
<script>

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

function saveCompany(){
	
	if($("#companyname").val()!=null){
		var companyname = $("#companyname").val().trim();
		var companynameinfo = "公司名称";
		var companynamelength = 64;
		var companynameflag = checkRedvalue(companyname,companynameinfo,companynamelength);
		if(companynameflag == false){
			var companynameInput = document.getElementById("companyname");
			companynameInput.focus();
			return companynameflag;
		}
	}
	var companyregname = $("#companyregname").val().trim();
	var companyregnameinfo = "公司注册名称";
	var companyregnamelength = 64;
	var companyregnameflag = checkRedvalue(companyregname,companyregnameinfo,companyregnamelength);
	if(companyregnameflag == false){
		var companyregnameInput = document.getElementById("companyregname");
		companyregnameInput.focus();
		return companyregnameflag;
	}
	
	var companyrep = $("#companyrep").val().trim();
	var companyrepinfo = "法人代表";
	var companyreplength = 25;
	var companyrepflag = checkRedvalue(companyrep,companyrepinfo,companyreplength);
	if(companyrepflag == false){
		var companyrepInput = document.getElementById("companyrep");
		companyrepInput.focus();
		return companyrepflag;
	}
	
	var englishname = $("#englishname").val().trim();
	var englishnameinfo = "英文名称";
	var englishnamelength = 120;
	var englishnameflag = checkvalue(englishname,englishnameinfo,englishnamelength);
	if(englishnameflag == false){
		var englishnameInput = document.getElementById("englishname");
		englishnameInput.focus();
		return englishnameflag;
	}
	
	var namespell = $("#namespell").val().trim();
	var namespellinfo = "名称拼音";
	var namespelllength = 120;
	var namespellflag = checkvalue(namespell,namespellinfo,namespelllength);
	if(namespellflag == false){
		var namespellInput = document.getElementById("namespell");
		namespellInput.focus();
		return namespellflag;
	}
	
// 	var identifynum = $("#identifynum").val().trim();
// 	var identifynuminfo = "纳税人识别号";
// 	var identifynumlength = 25;
// 	var identifynumflag = checkRedvalue(identifynum,identifynuminfo,identifynumlength);
// 	if(identifynumflag == false){
// 		var identifynumInput = document.getElementById("identifynum");
// 		identifynumInput.focus();
// 		return identifynumflag;
// 	}
	
// 	var regnum = $("#regnum").val().trim();
// 	var regnuminfo = "工商注册号";
// 	var regnumlength = 25;
// 	var regnumflag = checkRedvalue(regnum,regnuminfo,regnumlength);
// 	if(regnumflag == false){
// 		var regnumInput = document.getElementById("regnum");
// 		regnumInput.focus();
// 		return regnumflag;
// 	}
	
	var organizationcode = $("#organizationcode").val().trim();
	var checkorganizationcode = /^[0-9A-Z]{18}$/;
	if(organizationcode == ""){
		alert("请输入统一社会信用代码");
		var organizationcodeInput = document.getElementById("organizationcode");
		organizationcodeInput.focus();
		return false;
	}
	else if(!checkorganizationcode.test(organizationcode)){
		alert("您输入的统一社会信用代码格式不对");
		var organizationcodeInput = document.getElementById("organizationcode");
		organizationcodeInput.focus();
		return false;
	}
	
	
// 	var organizationcodeinfo = "组织机构代码";
// 	var organizationcodelength = 25;
// 	var organizationcodeflag = checkRedvalue(organizationcode,organizationcodeinfo,organizationcodelength);
// 	if(organizationcodeflag == false){
// 		var organizationcodeInput = document.getElementById("organizationcode");
// 		organizationcodeInput.focus();
// 		return organizationcodeflag;
// 	}
	
	var trade = $("#trade").val().trim();
	var tradeinfo = "行业";
	var tradelength = 25;
	var tradeflag = checkvalue(trade,tradeinfo,tradelength);
	if(tradeflag == false){
		var tradeInput = document.getElementById("trade");
		tradeInput.focus();
		return tradeflag;
	}
	
	var contactname = $("#contactname").val().trim();
	var contactnameinfo = "联系人姓名（客户商务）";
	var contactnamelength = 25;
	var contactnameflag = checkRedvalue(contactname,contactnameinfo,contactnamelength);
	if(contactnameflag == false){
		var contactnameInput = document.getElementById("contactname");
		contactnameInput.focus();
		return contactnameflag;
	}
		
	var contactphone = $("#contactphone").val().trim();
	var checkcontactphone = /^1[34578]\d{9}$/; 
	var len = contactphone.length;
	if(contactphone == ""){
		alert("请输入手机号码");
		var contactphoneInput = document.getElementById("contactphone");
		contactphoneInput.focus();
		return false;
	}
	else if(!checkcontactphone.test(contactphone)){
		alert("您输入的手机号码格式不对");
		var contactphoneInput = document.getElementById("contactphone");
		contactphoneInput.focus();
		return false;
	}
	
	var contactfixedphone = $("#contactfixedphone").val().trim();
	var contactfixedphoneinfo = "联系人固定电话";
	var contactfixedphonelength = 12;
	var contactfixedphoneflag = checkvalue(contactfixedphone,contactfixedphoneinfo,contactfixedphonelength);
	if(contactfixedphoneflag == false){
		var contactfixedphoneInput = document.getElementById("contactfixedphone");
		contactfixedphoneInput.focus();
		return contactfixedphoneflag;
	}
	
	var email = $("#email").val().trim();
	var emailinfo = "电子邮件";
	var emaillength = 100;
	var emailflag = checkvalue(email,emailinfo,emaillength);
	if(emailflag == false){
		var emailInput = document.getElementById("email");
		emailInput.focus();
		return emailflag;
	}
	
	var salename = $("#salename").val();
	if(salename == ""){
		alert("请选择一个销售人员");
		var salenameInput = document.getElementById("salename");
		salenameInput.focus();
		return false;
	}
	
	var country = $("#country").val().trim();
	var countryinfo = "国家（地区）";
	var countrylength = 50;
	var countryflag = checkvalue(country,countryinfo,countrylength);
	if(countryflag == false){
		var countryInput = document.getElementById("country");
		countryInput.focus();
		return countryflag;
	}
	
// 	var province = $("#province").val().trim();
// 	var provinceinfo = "省（州）";
// 	var provincelength = 25;
// 	var provinceflag = checkvalue(province,provinceinfo,provincelength);
// 	if(provinceflag == false){
// 		var provinceInput = document.getElementById("province");
// 		provinceInput.focus();
// 		return provinceflag;
// 	}
	
// 	var city = $("#city").val().trim();
// 	var cityinfo = "市（县）";
// 	var citylength = 25;
// 	var cityflag = checkvalue(city,cityinfo,citylength);
// 	if(cityflag == false){
// 		var cityInput = document.getElementById("city");
// 		cityInput.focus();
// 		return cityflag;
// 	}
	
	var address = $("#address").val().trim();
	var addressinfo = "地址";
	var addresslength = 500;
	var addressflag = checkRedvalue(address,addressinfo,addresslength);
	if(addressflag == false){
		var addressInput = document.getElementById("address");
		addressInput.focus();
		return addressflag;
	}
	
	var zipcode = $("#zipcode").val().trim();
	var zipcodeinfo = "邮政编码";
	var zipcodelength = 50;
	var zipcodeflag = checkvalue(zipcode,zipcodeinfo,zipcodelength);
	if(zipcodeflag == false){
		var zipcodeInput = document.getElementById("zipcode");
		zipcodeInput.focus();
		return zipcodeflag;
	}
	
	
// 	save('#productForm', '${base}user/add.do', '${base}user/show.do?page=${page}');
	save('#companyForm', '${base}company/save.do', '${base}company/list.do?page=${page}');
}

</script>

<SCRIPT type="text/javascript">
// 		<!--
// 		var setting = {
// 			check: {
// 				enable: true
// 			},
// 			data: {
// 				simpleData: {
// 					enable: true
// 				}
// 			}
// 		};

// 		var zNodes =${menuTreeBeans};
		
		
// 		var zTree;
// 		$(document).ready(function(){
// 			zTree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
			
			
// 		});
		
// 		function select(){
// 			var nodes = zTree.getCheckedNodes(true);
// 			var ids = "";
// 			var menuNames = "";
// 			for(var i=0;i<nodes.length;i++){
// 				if(ids==""){
// 					ids = nodes[i].id;
// 					menuNames = nodes[i].name;
// 				}else {
// 					ids +=",";
// 					ids +=nodes[i].id;
					
// 					menuNames +=",";
// 					menuNames +=nodes[i].name;
// 				}
				
// 			}
			
// 			$("#menuIds").val(ids);
// 			$("#menuNames").val(menuNames);
// 			$('#mytree').modal('hide')
// 		}
// 		//-->
	</SCRIPT>	
<%@ include file="/WEB-INF/views/common/myModal.jsp" %>
<%@ include file="/WEB-INF/views/foot_n.jsp" %>

