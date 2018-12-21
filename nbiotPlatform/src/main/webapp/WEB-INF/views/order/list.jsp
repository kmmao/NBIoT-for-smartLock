<%@ page import="com.routon.plcloud.common.decorator.PageCheckboxDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/head_n.jsp" %>
<%@ include file="/WEB-INF/views/activiti/RuntimeProcess.jsp" %>
<link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.css">
<script src="${ctx}/js/bootstrap-datetimepicker.js"></script>
<script src="${ctx}/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<link rel="stylesheet" href="${ctx}/css/zTreeStyle.css">
	<%-- <input id="hiddenInput" type="hidden" value="${treeNodeTid}">
	<div class="panel panel-default aaa1" style="width: 300px;">
		<div class="panel-heading ">
			<h5 id="grouptitle">选择分组</h5>
		</div>
  		<div class="panel-body">
  			<ul id="treeDemo" class="ztree"></ul>
		</div>nav-tabs 
	</div> --%>
	<div class="modal fade" id="NewOrderModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" aria-hidden="true">
    	<div class="modal-dialog" style="width: 1000px;">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">订单管理->订单详情列表</h4>
            </div>
            
            <div class="modal-body"><!-- nav-tabs标签页风格nav-pills -->
            	<ul class="nav nav-tabs" role="tablist" id="myTabul">  
                    <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">订单详情</a></li>  
                    <li><a href="#profile" data-toggle="tab">子订单列表</a></li>  
                </ul>
             <div class="tab-content">
              <div role="tabpanel" class="tab-pane active" id="home">
            	<form role="form" class="form-inline" id="orderForm" name="orderForm" method='post'>
            	<input id="hiddenInput" type="hidden" value="${treeNodeTid}">
            	<input id="projectNameHidden" type="hidden" value="${project.projectname}">
            	<input id="updateId" name="updateId" type="hidden">
            	<input id="renew" name="renew" type="hidden">
            	<input id="renewverify" name="renewverify" type="hidden">
            	<input id="subordercontactname" name="subordercontactname" type="hidden" value="${company.contactname}">
            	<input id="companyId" name="companyId" type="hidden" value="${company.id}">
            	<input id="projectId" name="projectId" type="hidden" value="${project.id}">
            	<input id="activitiTag" name="activitiTag" type="hidden">
            	<div style="width:100%;text-align:center">
            	  <div class="form-group">
            		<table width="100%">
            			<tr align="left">
            				<td style="padding-top: 5px;"><label id="ordernumLabel" name="ordernumLabel" class="form-inline">订单号：</label></td>
                			<td style="padding-top: 5px;"><input id="orderNum" name="orderNum" disabled="disabled" type="text" class="form-control" style="width: 276px;" /></td>
            			</tr>
            			<tr align="left">
                			<td style="padding-top: 5px;"><label class="form-inline">公司名称：</label></td>
                			<td style="padding-top: 5px;"><input id="companyName" name="companyName" value="${company.companyname}" disabled="disabled" type="text" class="form-control" style="width: 276px;" /></td>
                			<td style="padding-top: 5px;"><label class="form-inline">公司地址：</label></td>
                			<td style="padding-top: 5px;"><input id="companyAddress" name="companyAddress" value="${company.address}" disabled="disabled" type="text" class="form-control" style="width: 276px;" /></td>
                	 	</tr> 
                	 	<tr align="left">
                			<td style="padding-top: 5px;"><label class="form-inline">项目名称：</label></td>
                			<td style="padding-top: 5px; padding-right: 20px;"><input id="projectName1" name="projectName1" value="${project.projectname}" disabled="disabled" type="text" class="form-control" style="width: 276px;" /></td>
                			<td style="padding-top: 5px;"><label class="form-inline">项目地址：</label></td>
                			<td style="padding-top: 5px;"><input type="text" id="projectAddress" name="projectAddress" value="${project.projectadd}" disabled="disabled" class="form-control" style="width: 276px;" /></td>
                	 	</tr>
                	 	<tr align="left">
							<td style="padding-top: 5px;" colspan="1"><label>客户商务人员姓名：</label></td>
							<td style="padding-top: 5px;" colspan="1"><input id="costomerBusinessMan" name="costomerBusinessMan" value="${company.contactname}" type="text" disabled="disabled" class="form-control" style="width: 150px;"/></td>
                			<td style="padding-top: 5px;" colspan="1"><label>客户商务人员电话：</label></td>
                			<td style="padding-top: 5px;" colspan="1"><input id="coustomerBusinessTel" name="coustomerBusinessTel" value="${company.contactphone}" type="text" disabled="disabled" class="form-control" style="width: 150px;"/></td>
                	 	</tr>
                	 	<tr align="left">
							<td style="padding-top: 5px;"><label class="form-inline">客户项目负责人员姓名：</label></td>
							<td style="padding-top: 5px;"><input id="customerProLeader" name="customerProLeader" type="text" value="${project.cusprojectname}" disabled="disabled" class="form-control" style="width: 150px;"/></td>
                			<td style="padding-top: 5px;"><label class="form-inline">客户项目负责人员电话：</label></td>
                			<td style="padding-top: 5px;"><input id="customerProLeaderTel" name="customerProLeaderTel" type="text" value="${project.cusprojectphone}" disabled="disabled" class="form-control" style="width: 150px;"/></td>
                	 	</tr>
                	 	<tr align="left">
							<td style="padding-top: 5px;"><label class="form-inline">拟剩余数量：</label></td>
							<td style="padding-top: 5px;"><input id="remainingNumber" name="remainingNumber" type="text" value="${project.demandquantity}" disabled="disabled" class="form-control" style="width: 150px;"/></td>
                			<td style="padding-top: 5px;"><label class="form-inline">软件ERP编码：</label></td>
                			<td style="padding-top: 5px;"><input id="erpCode" name="softwareerpnumber" type="text" value="${project.softwareerpnumber}" disabled="disabled" class="form-control" style="width: 150px;"/></td>
                			<%-- <td style="padding-top: 5px;">
	                			<select style="width: 150px;" class="form-control" id="erpCode" name="softwareerpnumber" onchange="specieSelChange(this)">
				      			<option value="">―请选择―</option>
				      			<c:forEach var="item" items="${softwareERPcodeList}">
									<option value="${item}">${item}</option>
							    </c:forEach> 
				      			</select>
			      			</td> --%>
                	 	</tr>
                	 	<tr align="left">
							<td style="padding-top: 5px;"><label class="form-inline">产品名称及版本号：</label></td>
							<td style="padding-top: 5px;">
							  <input id="softwaretypeversion" name="softwaretypeversion" type="text" value="${project.softwaretypeversion}" disabled="disabled" class="form-control" style="width: 150px;"/>
							</td>
                			<td style="padding-top: 5px;"><label class="form-inline" id="labelnumber" name="labelnumber" style=" color:red ">需求数量：</label></td>
                			<td style="padding-top: 5px;">
                			  <input id="demandquantity" name="demandquantity" type="text" class="form-control" style="width: 150px;" placeholder="长度不超过8个数字"/>
                			</td>
                	 	</tr>
                	 	<tr align="left">
							<!-- <td style="padding-top: 5px;"><label class="form-inline" style=" color:red ">授权类型：</label></td>
							<td style="padding-top: 5px;"><select style="width: 150px;" class="form-control" id="licensetype" name="licensetype">
			      			<option value="">―请选择―</option>
			      			<option value ="在线">在线</option>
			      			<option value ="离线">离线</option> 
			      		</select></td> -->
                			<td style="padding-top: 5px;"><label class="form-inline">到期提醒天数：</label></td>
                			<td style="padding-top: 5px;">
                			  <input id="reminderdays" name="reminderdays" type="text" class="form-control" style="width: 150px;" placeholder="长度不超过8个数字"/>
                			</td>
                			<td style="padding-top: 5px;"><label class="form-inline" style=" color:red ">绑定方式：</label></td>
							<td style="padding-top: 5px;">
								<select style="width: 150px;" class="form-control" id="bindingmode" name="bindingmode">
					      			<option value="">―请选择―</option>
					      			<option value ="机器码">机器码</option>
					      			<option value ="设备码">设备码</option>
					      			<option value ="有效期">有效期</option>
					      			<!-- <option value ="机器码+有效期">机器码+有效期</option>
					      			<option value ="设备码+有效期">设备码+有效期</option> -->
				      			</select>
			      			</td>
                	 	</tr>
                	 	<!--<tr align="left">
							 <td style="padding-top: 5px;"><label class="form-inline" style=" color:red ">绑定方式：</label></td>
							<td style="padding-top: 5px;"><select style="width: 150px;" class="form-control" id="bindingmode" name="bindingmode">
			      			<option value="">―请选择―</option>
			      			<option value ="机器码">机器码</option>
			      			<option value ="设备码">设备码</option>
			      			<option value ="有效期">有效期</option>
			      			<option value ="机器码+有效期">机器码+有效期</option>
			      			<option value ="设备码+有效期">设备码+有效期</option>
			      		</select></td> 
                	 	</tr>-->
                	 	<tr align="left">
                			<td style="padding-top: 5px;" colspan="4"><label class="form-inline" style=" color:red ">有效期：</label></td>
                	 	</tr>
                	 	<tr align="left">
                	 		<td style="padding-top: 5px;" colspan="4">
                	 			<table width="100%">
                	 				<tr align="left">
                	 					<td><input type="radio" id="radio_1" name="orderActiveStatus" value="1" onclick="fix(this)"/>&nbsp;&nbsp;&nbsp;</td>
                	 					<td><input type="text" id="month_1" name='month_1' class="form-control note_w" style="width: 90px;" disabled="disabled" placeholder="少于4个数"/></td>
                	 					<td>月，自合同生效之日算起</td>
                	 					<td>
                	 						<div class="btn-group" style="margin-top: 5px;margin-bottom: 5px;">
			  									<div class="input-group date form_datetime">
						    						<input size="20" type="text" id="startTimeByContract" name="startTimeByContract" class="form-control note_w" readonly placeholder="请输入合同生效日期" disabled="disabled">
						   						    <span class="input-group-addon">
						    						<span class="glyphicon glyphicon-calendar"></span>
						   							</span>
												</div>
											</div>
										</td>
                	 				</tr>
                	 				<tr align="left">
                	 					<td><input type="radio" id="radio_2" name="orderActiveStatus" value="2" onclick="fix(this)"/></td>
                	 					<td><input type="text" id="month_2" name='month_2' class="form-control note_w" style="width: 90px;" disabled="disabled" placeholder="少于4个数"/></td>
                	 					<td>月，自产品激活之日算起（合同生效三个月自动视为激活）</td>
                	 					<td>
                	 						<div class="btn-group" style="margin-top: 5px;margin-bottom: 5px;">
			  									<div class="input-group date form_datetime">
						    						<input size="20" type="text" id="startTimeByActive" name="startTimeByActive" class="form-control note_w" readonly placeholder="请输入默认产品激活日期" disabled="disabled">
						   						    <span class="input-group-addon">
						    						<span class="glyphicon glyphicon-calendar"></span>
						   							</span>
												</div>
											</div>
                	 					</td>
                	 				</tr>
                	 				<tr align="left">
                	 					<td colspan="1"><input type="radio" id="radio_3" name="orderActiveStatus" value="3" onclick="fix(this)"/></td>
                	 					<td colspan="2">永久有效</td>
                	 					<td colspan="1">
                	 						<div class="btn-group" style="margin-top: 5px;margin-bottom: 5px;">
			  									<div class="input-group date form_datetime">
						    						<input size="20" type="text" id="startDate_createTime" name="startDate_createTime" class="form-control note_w" readonly placeholder="请输入永久有效日期" disabled="disabled">
						   						    <span class="input-group-addon">
						    						<span class="glyphicon glyphicon-calendar"></span>
						   							</span>
												</div>
											</div>
                	 					</td>
                	 				</tr>
                	 			</table>
							</td>
                	 	</tr>
                	 	<tr align="left">
            				<td style="padding-top: 5px;" colspan="4"><label class="form-inline">备注：</label></td>
            			</tr>
            			<!-- <tr align="left">
            				<textarea id="mark" name="mark" rows="2" cols="100">备注</textarea>
            			</tr> -->
            		</table>
            			<textarea id="mark" name="mark" rows="3" cols="100" placeholder="长度不超过512个字符"></textarea>
  				   </div>
  				 </div>
  				<div class="modal-footer">
	                <!-- <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button> -->
	                <button type="button" class="btn btn-default" onclick="JudgesubmitForm()">关闭</button>
	                <button type="button" id="addsub" name="addsub" class="btn btn-primary" onclick="addSubmit()">保存</button>
	            	<%-- <button type="button" class="btn btn-primary" onclick="save('#orderForm', '${base}order/save.do?projectid=${project.id}&companyid=${company.id}', '${base}order/show.do?page=${page}')">保存</button>
	            	</div> --%>
            	</div>
            	</form>
             </div>	
             
             <div role="tabpanel" class="tab-pane" id="profile">
             	<div style="width:100%">
					<%-- <display:table name="${childpageList}" id="curPage" class="table table-striped" sort="external" excludedParams="*"
						requestURI="childordershow.do";text-align:center
						decorator="com.routon.plcloud.common.decorator.PageOrderDecorator" 
						export="false">
						<display:column title="订单号" property="ordernum" sortable="false" style="width:10%;" ></display:column>
						<display:column title="备注" property="remarks" sortable="false" style="width:10%;" ></display:column>
						<display:column title="生成时间" property="createtime" sortable="false" style="width:10%;" decorator="com.routon.plcloud.common.decorator.PageDateTimeDecorator"></display:column>
					</display:table> --%>
					<%-- <table class="table" width="100%" layoutH="138">
						<thead>
							<tr>
								<th width="15%">订单号</th>
								<th width="16%" orderField="createtime">生成日期</th>
								<th width="10%">备注</th>
							</tr>
						</thead>
						<tbody>
						测试：${childpageList}
						 <c:forEach var="childList" items="${childpageList}">
							<tr align="center">
								<td>${childList.ordernum}</td>
								<td>${childList.createtime}</td>
								<td>${childList.remarks}</td>
							</tr>
							</c:forEach>
						</tbody>
					</table> --%>
					<table class="table" id='tabletest'>
				    	<tr>
					        <th align="center">子订单号</th>
					        <th align="center">客户商务人员</th>
					        <th align="center">需求数量</th>
					        <th align="center">生成日期</th>
					        <th align="center">备注</th>
				    	</tr>
 					</table>
            	</div>
            	
				<div class="modal-footer">
	                <button type="button" class="btn btn-default" onclick="ListorderBack()">关闭</button>
            	</div>
             </div>
			</div>
           </div> 
        </div>
    </div>
	</div>
	
<!-- 	<div class="panel panel-default container" style="width: 1560px; margin-bottom: 20px;"> -->
		<div class="container" style=" width: 1560px;">
		<div class="panel panel-default" style=" width: 1530px; margin-left: -15px;">
  		<div class="panel-heading">
  			<div class="pull-right">
  				<c:if test="${(!empty userPrivilege['90000301'])}">
					<div class="btn-group" >         
	  					<button id= "newBtn" type="button" class="btn btn-primary"  onclick="addOrder()">新建订单</button>
	  				</div>
	  			</c:if>
	  			<c:if test="${(!empty userPrivilege['90000306'])}">
					<div class="btn-group" >         
	  					<button id= "newBtn" type="button" class="btn btn-primary" onclick="renewOrder()">订单续订</button>
	  				</div>
	  			</c:if>
	  			<c:if test="${(!empty userPrivilege['90000307'])}">
					<div class="btn-group" >         
	  					<button id= "newBtn" type="button" class="btn btn-primary" onclick="retreatOrder()">订单退订</button>
	  				</div>
	  			</c:if>
	  			
	  			<!-- <div class="btn-group" >         
	  					<button id= "init" type="button" class="btn btn-primary"  onclick="initActivitiDB()">流程基础数据初始化</button>
	  			</div> -->
	  			
<%-- 	  			<c:choose> --%>
<%--    						<c:when test="${(!empty userPrivilege['90000302'])}">  --%>
<!--    							<button id= "editBtn" type="button" class="btn btn-primary" value="edit" onclick="detailOrder(this)">编辑订单</button>    -->
<%--    						</c:when> --%>
<%--    						<c:otherwise>  --%>
<%--    							<c:if test="${(!empty userPrivilege['90000300'])}"> --%>
<!-- 		  						<button id= "detailBtn" type="button" class="btn btn-primary" value="query" onclick="detailOrder(this)">订单详情</button> -->
<%-- 		  			  		</c:if>  --%>
<%--    						</c:otherwise> --%>
<%-- 				</c:choose> --%>
					 
	  			<%-- <c:if test="${(!empty userPrivilege['90000302'])}">
	  				<div class="btn-group" >
	  					<button id= "editBtn" type="button" class="btn btn-primary" value="edit" onclick="detailOrder(this)">编辑订单</button>
	  				</div>
	  			</c:if>
	  			
	  			<c:if test="${(!empty userPrivilege['90000306'])}">
	  				<div class="btn-group" >
	  					<button id= "detailBtn" type="button" class="btn btn-primary" value="query" onclick="detailOrder(this)">订单详情</button>
	  				</div>
	  			</c:if> --%>
	  			
	  			<%-- <c:if test="${(!empty userPrivilege['90000303'])}">
	  				<div class="btn-group" >
	  					<button id= "orderAuthBtn" type="button" class="btn btn-primary"  onclick="auditOrder()">订单审核</button>
	  				</div>
	  			</c:if> --%>
	  			<c:if test="${(!empty userPrivilege['90000304'])}">
	  				<div class="btn-group" >
	  					<button id= "closeBtn" type="button" class="btn btn-danger" onclick="closeOrder()">强制停止</button>
	  				</div>
	  			</c:if>
	  			<c:if test="${(!empty userPrivilege['90000308'])}">
	  				<div class="btn-group" >
	  					<button id= "closeBtn" type="button" class="btn btn-danger" onclick="openOrder()">订单开启</button>
	  				</div>
	  			</c:if>
  			</div> 
  			 <h5>  订单    > 订单管理 </h5>
  		</div>
  		<div class="panel-body">
			<div class="panel panel-default col-sm-2" style=" overflow-y:auto; overflow-x:auto;width:249px; height:534px; ">
  			
  			<!--  ***** 	订单管理模块ztree搜索                  ****-->
			<form class="form-inline" style="width:220px;" role="form" id="queryform" name="queryform" action="${ctx}/order/show.do"  method="post">
				 <div class="btn-group"  >
				  <select id="requirementtype" name="requirementtype" class="form-control "  >			    
			      		<option value="1" selected="selected" >公司</option>
						<option value="2"  >项目</option>
				  </select>
				
  				  <input style= "width:83px;float:right" id="searchname" name="searchname" type="text" class="form-control" placeholder="查询信息" value="${searchname}" onkeyup="this.value=this.value.replace(/[^\uFF00-\uFFFF\u4e00-\u9fa5\w\@\&\-]/g,'')">			
  			    </div>
  			    <div class="btn-group" >
  				   <button type="submit" class="btn btn-primary" >查询</button>
  			    </div>
  			</form>	
  			
				<ul id="treeDemo" class="ztree"></ul>
			</div>
			
			<!-- <div class="panel panel-default col-sm-2" style=" overflow-y:auto; overflow-x:auto;width:240px; height:534px; ">
				<ul id="treeDemo" class="ztree"></ul>
			</div> -->
			<div class="panel panel-default col-sm-10">
			    <form class="form-inline" role="form" id="queryform" name="queryform" action="${ctx}/order/show.do"  method="post">  
			    <input id="hiddenInput" type="hidden" value="${treeNodeTid}">
			    <input id="tmpcompanyid" type="hidden" value="${tmpcompanyid}">
			    <input id="tmpprojectid" type="hidden" value="${tmpprojectid}">
			    
					<c:if test="${(!empty userPrivilege['90000300'])}">
					<div class="btn-group">
	  				   <input size="20" type="text" id="ordernum" name="ordernum" value="${ordernum}" placeholder="请输入订单号" style="width: 226px;padding-bottom: 0px;padding-top: 0px;height: 34px;" onkeyup="this.value=this.value.replace(/[^\uFF00-\uFFFF\u4e00-\u9fa5\w\@\&\-]/g,'')">			
	  			    </div>
	  			    
	  			    <div class="btn-group">
	  			    	<div class="col-sm-4">
						  <select style="width: 150px;" class="form-control" id="status" name="status">
					      	<option value="4">―订单状态―</option>
					      	<option value ="1" <c:if test="${stau==1}" >selected="selected"</c:if>>启用</option>
					      	<option value ="2" <c:if test="${stau==2}" >selected="selected"</c:if>>结束</option>
					      	<option value ="3" <c:if test="${stau==3}" >selected="selected"</c:if>>强制停止</option>
				      	   </select>
			    		</div>
	  			    </div>
	  			    
	  			    <div class="btn-group">
	  				   <button id= "queryBtn" type="submit" class="btn btn-primary">查询</button>
	  				   <!-- <input type="button" id="queryBtn" value="查询" class="btn btn-primary" onclick="queryBtnSubmit()"> -->
	  			    </div>
	  			    </c:if>
  			    </form>	 
  			    
				<display:table name="${pageList}" id="curPage" class="table table-striped" sort="external" excludedParams="*"
					requestURI="show.do"
					decorator="com.routon.plcloud.common.decorator.PageOrderDecorator" 
					export="false">
					<display:column property="id" title="<%=PageCheckboxDecorator.getTitle(pageContext)%>" decorator="com.routon.plcloud.common.decorator.PageCheckboxDecorator"  style="width:2%;"/>
					<display:column title="ID"  property="id"  sortable="true"  style="width:5%;" />
					<display:column title="订单号" property="ordernum" sortable="true" style="width:5%;" ></display:column>
					<%-- <display:column title="软件ERP编码" property="softwareerpnumber" sortable="true" style="width:5%;" ></display:column>
					<display:column title="软件名称及版本号" property="softwaretypeversion" sortable="true" style="width:5%;" ></display:column> --%>
					<display:column title="需求数量" property="demandquantity" sortable="true" style="width:5%;" ></display:column>
					<display:column title="已发数量" property="authorizedNum" sortable="false" style="width:5%;" ></display:column>
					<display:column title="备注" property="remarks" sortable="false" style="width:10%;" ></display:column>
					<%--<display:column title="在线数量"  sortable="true" style="width:5%;" ></display:column>
					<display:column title="离线数量"  sortable="true" style="width:5%;" ></display:column>
					 <display:column title="授权类型"  property="licensetype" sortable="true" style="width:5%;" ></display:column> --%>
					<display:column title="生成时间" property="createtime" sortable="true" style="width:10%;" decorator="com.routon.plcloud.common.decorator.PageDateTimeDecorator"></display:column>
					<display:column title="修改时间" property="moditytime" sortable="true" style="width:10%;" decorator="com.routon.plcloud.common.decorator.PageDateTimeDecorator"></display:column>
					<display:column title="下单次数" property="renewCount" sortable="false" style="width:5%;" ></display:column>
					<display:column title="订单状态" sortable="true"  sortProperty="status" style="width:5%;">
						<c:choose>
							<c:when test="${curPage.status == 1}">
								启用
							</c:when>
						</c:choose>
						<c:choose>
							<c:when test="${curPage.status == 0}">
								强制停止
							</c:when>
						</c:choose>
						<c:choose>
							<c:when test="${curPage.status == 2}">
								结束
							</c:when>
						</c:choose>
					</display:column>
					<display:column title="审核状态" sortable="true"  sortProperty="verify" style="width:5%;">
						<c:choose>
							<c:when test="${curPage.verify == 1}">
								已审核
							</c:when>
						</c:choose>
						<c:choose>
							<c:when test="${curPage.verify == 0}">
								未审核
							</c:when>
						</c:choose>
						<c:choose>
							<c:when test="${curPage.verify == 2}">
								续订审核中
							</c:when>
						</c:choose>
						<c:choose>
							<c:when test="${curPage.verify == 3}">
								退订审核中
							</c:when>
						</c:choose>
						<c:choose>
							<c:when test="${curPage.verify == 4}">
								开启审核中
							</c:when>
						</c:choose>
					</display:column>
				</display:table>
			</div>
			<%@ include file="/WEB-INF/views/common/paginationSpecial.jsp" %><%-- ?claimed_page=${i} --%>
	 		<select id="select_page_4" name="select_page_4" onchange="javascript:window.location.href=this.options[this.selectedIndex].value;" style="display:none">
	 				<option value="show.do?pageOrder=1&projectid=${project.id}&companyid=${company.id}" selected="selected">1</option>
	 				<c:forEach var="i" begin="2" end="${maxpage}">
						<option value="show.do?pageOrder=${i}&projectid=${project.id}&companyid=${company.id}">${i}</option>
					</c:forEach>
	 		</select>   
 		</div>
	</div>
</div>
	<SCRIPT type="text/javascript">

		var setting = {
			check: {
				enable: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
	        callback: {
	            beforeClick: function(treeId, treeNode) {
	                if (treeNode.isParent) {
	                    //zTree.expandNode(treeNode);
	                    //return false;
	                	var id = treeNode.id;
	                	var url = '${base}order/show.do?companyid=' + id;
		                document.location.href = url;
	                } else {
	                    //demoIframe.attr("src",treeNode.file + ".html");
	                    var id = treeNode.id;
	                    var url = '${base}order/show.do?projectid=' + id + '&treeNodeTid='+ treeNode.tId + '&companyid=' + treeNode.pid;
	                    document.location.href = url;
	                }
	            }
	        }
		};

		var zNodes =${menuTreeBeans};
		
		
		var zTree;
		$(document).ready(function(){
			zTree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
			
			//var checkedNodetid = $('#hiddenInput').val();
			//$("#" + checkedNodetid + "_a").attr('class', 'curSelectedNode');
			
			
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			var tmpcompanyid = $('#tmpcompanyid').val();
			var tmpprojectid = $('#tmpprojectid').val();
			var node = null;
			if(tmpcompanyid!=""){
				 node = treeObj.getNodeByParam("id", tmpcompanyid, null);
			}
			if(tmpprojectid!=""){
				 node = treeObj.getNodeByParam("id", tmpprojectid, null);
			}
			if(node !=null){
				$("#" + node.tId + "_a").attr('class', 'curSelectedNode');
			}
			
		});
		
		/* function select(){
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
			
		} */
		
		
		function JudgesubmitForm(){
			var change = 0;
		    var form = document.getElementById('orderForm');
		    for (var i = 0; i < form.length; i++) {
		        var element = form.elements[i];
		        var type = element.type;
		        if (type == "checkbox" || type == "radio") {
		 
		            if (element.checked != element.defaultChecked) {
		               change = 1;
		            }
		        }
		        if (type == "hidden" || type == "password" || type == "text" || type == "textarea") {
		 
		            if (element.value != element.defaultValue) {
		                change = 1;
		            }
		        }
		 
		        /* if (type == "select-one" || type == "select-multiple") {
		 
		            for (var j = 0; j < element.options.length; j++) {
		 
		                if (element.options[j].selected != element.options[j].defaultSelected) {
		                	change = 1;
		                }
		            }
		        } */
		        /* if (type == "file") {
		            if (element.value.length != 0) {
		                Filechange = false;
		                change = 1;
		            }
		        } */
		    }
		    if(change == 1){
		    	if(confirm("您输入的信息未保存,确认退出吗?")){
		    		gotourl('${ctx}/order/show.do?page=${page}');
		    	}
		    }else{
		    		gotourl('${ctx}/order/show.do?page=${page}');
		    }
		}
		
		function ListorderBack(){
			gotourl('${ctx}/order/show.do?page=${page}');
		}
		
		/* function queryBtnSubmit(){
			var companyid = $('#companyId').val();
			var projectid = $('#projectId').val();
			var status = $('#status').val();
			if(companyid == null && projectid == null){
				//没有点击ztree
				var url = '${base}order/show.do?status='+status;
			    document.location.href = url;
			}else if(companyid !=null && projectid == null){
				//点击ztree公司节点
				var url = '${base}order/show.do?companyid=' + companyid+ '&status='+ status;
				document.location.href = url;
			}else{
				//点击ztree项目节点
				var url = '${base}order/show.do?companyid=' + companyid + '&projectid='+ projectid+ '&status='+ status;
				document.location.href = url;
			}
		} */ 
		
		function addOrder(){
			var checkedNodetid = $('#hiddenInput').val();
			$('#activitiTag').val('true');
			if(checkedNodetid == '' || checkedNodetid == null) {
				alert("请选择一个项目");
				return false;
			}
			
			$('#orderNum').attr('type','hidden');
			document.getElementById("ordernumLabel").style.display = "none";
			
			$('#myTabul').hide();
			//$('#myTabul li:eq(0)').hide();
			//$('#myTabul li:eq(1)').hide();
			//$('#myTabul li:eq(1)').addClass("disabled");
			//$('a[href="#profile"]').on('show.bs.tab', function(e) {
		  		//e.preventDefault();
			//});
			
			$('#NewOrderModal').modal('show');
		}
		
		//订单续订
		function renewOrder(){
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
			//var selectedIds = getCheckedRowValue("");
			if(selectedIds==""){
				alert("请选择一个订单续订");
				return false;
			}
			var selectedId = selectedIds.split(",");
			if (selectedId.length != 1) {
				alert("请选择一个订单续订");
				return false;
			}
			
			$('#myTabul').hide();
			//续订时需求数量改为续订数量
			var label=document.getElementById("labelnumber"); 
			label.innerText="续订数量：";
			//************* added by wangxiwei in 20180620, do not delete
			$('#activitiTag').val('true');
			//*************
			/* $('#erpCode').attr('disabled','disabled');
			$('#softwaretypeversion').attr('disabled','disabled');
			$('#reminderdays').attr('disabled','disabled');
			$('#bindingmode').attr('disabled','disabled');
			$('#radio_1').attr('disabled','disabled');
			$('#startTimeByContract').attr('disabled','disabled');
			$('#radio_2').attr('disabled','disabled');
			$('#startTimeByActive').attr('disabled','disabled');
			$('#radio_3').attr('disabled','disabled');
			$('#startDate_createTime').attr('disabled','disabled');
			$('#mark').attr('disabled','disabled'); */
			//续订订单只可编辑数量
			changeDisable();
			//为点保存的时候区分续订和编辑
			var renew = "xuding";
			$("#renew").val(renew);
			
			//$('#NewOrderModal').modal('show'); 
			
			$.ajax({
				url:'${base}order/ordershow.do',
				type:'post',
				data:{id : selectedIds},
				async:false,
				success:function(data){
					var company = data.obj;
					var project = data.obj1;
					var order = data.obj2;
					
					$("#subordercontactname").val(company.contactname);
					
					$("#updateId").val(order.id);
					$("#orderNum").val(order.ordernum);
					$("#companyName").val(company.companyname);
					$("#companyAddress").val(company.address);
					$("#projectName1").val(project.projectname);
					$("#projectAddress").val(project.projectadd);
					$("#costomerBusinessMan").val(company.contactname);
					$("#coustomerBusinessTel").val(company.contactphone);
					$("#customerProLeader").val(project.cusprojectname);
					$("#customerProLeaderTel").val(project.cusprojectphone);
					$("#remainingNumber").val(project.demandquantity);
					document.getElementById("erpCode").value = project.softwareerpnumber;
					$("#softwaretypeversion").val(project.softwaretypeversion);
					//$("#demandquantity").val(order.demandquantity);
					$("#reminderdays").val(order.reminderdays);
					document.getElementById("bindingmode").value = order.bindingmode;
					
					var month=order.month;
					var endtime=order.endtime;
					var tacitstarttime=order.tacitstarttime;
					
					if(month != null){
						if(endtime != null){
							$("#month_1").val(month);
							$("#startTimeByContract").val(order.starttime);
							document.getElementById("radio_1").checked = "cheched";
						}else{
							$("#month_2").val(month);
							$("#startTimeByActive").val(tacitstarttime);
							document.getElementById("radio_2").checked = "cheched";
						}
					}else{
						$("#startDate_createTime").val(endtime);
						document.getElementById("radio_3").checked = "cheched";
					}
					
					$("#mark").val(order.remarks);
					
					var verify = order.verify;
					var stau = order.status;
					if(verify == 0 || verify == 2 || verify == 3 || verify == 4){//verify=1已审核，判断审核是否通过
						alert("请选择已审核状态的订单来续订");
						gotourl('${ctx}/order/show.do?page=${page}');
					}else if(stau == 0){//stau=1启用，=2结束，=0强制停止，判断状态是否为强制停止
						alert("该订单已被强制停止，请启用审核通过后续订");
						gotourl('${ctx}/order/show.do?page=${page}');
					}else{
						$('#NewOrderModal').modal('show');
					}
				}
			});
		}
		
		//订单退订
		function retreatOrder(){
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
			if(selectedIds==""){
				alert("请选择一个订单退订");
				return false;
			}
			var selectedId = selectedIds.split(",");
			if (selectedId.length != 1) {
				alert("请选择一个订单退订");
				return false;
			}
			
			$('#myTabul').hide();
			//退订时需求数量改为退订数量
			var label=document.getElementById("labelnumber"); 
			label.innerText="退订数量：";
			//退订订单只可编辑数量
			changeDisable();
			//为点保存的时候区分续订、退订和编辑
			var renew = "tuiding";
			$("#renew").val(renew);
			
			$.ajax({
				url:'${base}order/ordershow.do',
				type:'post',
				data:{id : selectedIds},
				async:false,
				success:function(data){
					var company = data.obj;
					var project = data.obj1;
					var order = data.obj2;
					
					$("#subordercontactname").val(company.contactname);
					$("#updateId").val(order.id);
					$("#orderNum").val(order.ordernum);
					$("#companyName").val(company.companyname);
					$("#companyAddress").val(company.address);
					$("#projectName1").val(project.projectname);
					$("#projectAddress").val(project.projectadd);
					$("#costomerBusinessMan").val(company.contactname);
					$("#coustomerBusinessTel").val(company.contactphone);
					$("#customerProLeader").val(project.cusprojectname);
					$("#customerProLeaderTel").val(project.cusprojectphone);
					$("#remainingNumber").val(project.demandquantity);
					document.getElementById("erpCode").value = project.softwareerpnumber;
					$("#softwaretypeversion").val(project.softwaretypeversion);
					$("#reminderdays").val(order.reminderdays);
					document.getElementById("bindingmode").value = order.bindingmode;
					
					var month=order.month;
					var endtime=order.endtime;
					var tacitstarttime=order.tacitstarttime;
					
					if(month != null){
						if(endtime != null){
							$("#month_1").val(month);
							$("#startTimeByContract").val(order.starttime);
							document.getElementById("radio_1").checked = "cheched";
						}else{
							$("#month_2").val(month);
							$("#startTimeByActive").val(tacitstarttime);
							document.getElementById("radio_2").checked = "cheched";
						}
					}else{
						$("#startDate_createTime").val(endtime);
						document.getElementById("radio_3").checked = "cheched";
					}
					
					$("#mark").val(order.remarks);
					
					var verify = order.verify;
					var stau = order.status;
					if(verify == 0 || verify == 2 || verify == 3 || verify == 4){//verify=1已审核，判断审核是否通过
						alert("请选择已审核状态的订单来退订");
						gotourl('${ctx}/order/show.do?page=${page}');
					}else if(stau == 0){//stau=1启用，=2结束，=0强制停止，判断状态是否为强制停止
						alert("该订单已被强制停止，请启用审核通过后退订");
						gotourl('${ctx}/order/show.do?page=${page}');
					}else if(stau == 2){
						alert("该订单已下发授权完毕，不可退订");
						gotourl('${ctx}/order/show.do?page=${page}');
					}else{
						$('#NewOrderModal').modal('show');
					}
				}
			});
		}
		
		function changeDisable(){
			//$('#erpCode').attr('disabled','disabled');
			//$('#softwaretypeversion').attr('disabled','disabled');
			$('#reminderdays').attr('disabled','disabled');
			$('#bindingmode').attr('disabled','disabled');
			$('#radio_1').attr('disabled','disabled');
			$('#month_1').attr('disabled','disabled');
			$('#startTimeByContract').attr('disabled','disabled');
			$('#radio_2').attr('disabled','disabled');
			$('#month_2').attr('disabled','disabled');
			$('#startTimeByActive').attr('disabled','disabled');
			$('#radio_3').attr('disabled','disabled');
			$('#startDate_createTime').attr('disabled','disabled');
			//$('#mark').attr('disabled','disabled');	
		}
		
		function editOrder(obj){
			var orderId = $(obj).parents('tr').children('td').eq(1).text();
			detailOrder(orderId);
		}
		
		// obj .etc - modified by wangxiwei in 20180323
		function detailOrder(obj){
			var result = obj.value;
			if(result == 'query'){
				var selectedIds = getCheckedRowValue("");
				if(selectedIds==""){
					alert("请选择一个进行查看");
					return false;
				}
				var selectedId = selectedIds.split(",");
				if (selectedId.length != 1) {
					alert("请选择一个进行查看");
					return false;
				}
				$('#addsub').attr('disabled','disabled');
			}else if(result == 'edit'){
				var selectedIds = getCheckedRowValue("");
				if(selectedIds==""){
					alert("请选择一个进行编辑");
					return false;
				}
				var selectedId = selectedIds.split(",");
				if (selectedId.length != 1) {
					alert("请选择一个进行编辑");
					return false;
				}
			}else{
// 				if(obj != null){
// 					var orderNum = obj;
// 				}
				if(obj.length > 5){
					var orderNum = obj;
				}
				else{
					var selectedIds = obj;
// 					$('#activitiTag').val('false');
// 					var selectedIds = getCheckedRowValue("");
// 					if(selectedIds==""){
// 						alert("请选择一个订单进行编辑");
// 						return false;
// 					}
// 					var selectedId = selectedIds.split(",");
// 					if(selectedId.length != 1){
// 						alert("请选择一个订单进行编辑");
// 						return false;
// 					}
				}
			}
			
			/* if(obj != null){
				var orderNum = obj;
			}else{
				$('#activitiTag').val('false');
				var selectedIds = getCheckedRowValue("");
				if(selectedIds==""){
					alert("请选择一个订单进行编辑");
					return false;
				}
				var selectedId = selectedIds.split(",");
				if(selectedId.length != 1){
					alert("请选择一个订单进行编辑");
					return false;
				}
			} */
			
			$.ajax({
				url:'${base}order/ordershow.do',
				type:'post',
				data:{id : selectedIds, orderNum : orderNum},
				async:false,
				success:function(data){
					var company = data.obj;
					var project = data.obj1;
					var order = data.obj2;
					
					/* var verify = order.verify
					if(verify > 0){
						alert("该订单已审核通过，不能再修改了");
					}else{
						$('#NewOrderModal').modal('show');
					} */
					
					var verify = order.verify
					//根据审核状态区分编辑续订和退订订单
					$("#renewverify").val(verify);
			
					$("#updateId").val(order.id);
					$("#orderNum").val(order.ordernum);
					$("#companyName").val(company.companyname);
					$("#companyAddress").val(company.address);
					$("#projectName1").val(project.projectname);
					$("#projectAddress").val(project.projectadd);
					$("#costomerBusinessMan").val(company.contactname);
					$("#coustomerBusinessTel").val(company.contactphone);
					$("#customerProLeader").val(project.cusprojectname);
					$("#customerProLeaderTel").val(project.cusprojectphone);
					$("#remainingNumber").val(project.demandquantity);
					document.getElementById("erpCode").value = project.softwareerpnumber;
					$("#softwaretypeversion").val(project.softwaretypeversion);
					
					if(verify == 2){
						var label=document.getElementById("labelnumber"); 
						label.innerText="续订数量：";
						$("#demandquantity").val(order.suborderRenewnum);
					}else if(verify == 3){
						var label=document.getElementById("labelnumber"); 
						label.innerText="退订数量：";
						$("#demandquantity").val(order.suborderRenewnum);
					}else{
						$("#demandquantity").val(order.demandquantity);
					}
					
					/* document.getElementById("licensetype").value = order.licensetype; */
					$("#reminderdays").val(order.reminderdays);
					document.getElementById("bindingmode").value = order.bindingmode;
					
					var month=order.month;
					var endtime=order.endtime;
					var tacitstarttime=order.tacitstarttime;
					
					/* if(tacitstarttime != null || tacitstarttime != ''){
						$("#month_2").val(order.month);
						$("#startTimeByActive").val(order.tacitstarttime);
						
						$('#radio_2').parent().parent().find('.note_w').removeAttr('disabled');
						$('#radio_1').parent().parent().find(".note_w").attr('disabled','disabled');
						$('#radio_3').parent().parent().find(".note_w").attr('disabled','disabled');
						document.getElementById("radio_2").checked = "cheched";
					} */
					
					if(month != null){
						if(endtime != null){
							$("#month_1").val(month);
							$("#startTimeByContract").val(order.starttime);
							
							$('#radio_1').parent().parent().find('.note_w').removeAttr('disabled');
							$('#radio_2').parent().parent().find(".note_w").attr('disabled','disabled');
							$('#radio_3').parent().parent().find(".note_w").attr('disabled','disabled');
							document.getElementById("radio_1").checked = "cheched";
						}else{
							$("#month_2").val(month);
							$("#startTimeByActive").val(tacitstarttime);
							
							$('#radio_2').parent().parent().find('.note_w').removeAttr('disabled');
							$('#radio_1').parent().parent().find(".note_w").attr('disabled','disabled');
							$('#radio_3').parent().parent().find(".note_w").attr('disabled','disabled');
							document.getElementById("radio_2").checked = "cheched";
						}
					}else{
						$("#startDate_createTime").val(endtime);
						$('#radio_3').parent().parent().find('.note_w').removeAttr('disabled');
						$('#radio_1').parent().parent().find(".note_w").attr('disabled','disabled');
						$('#radio_2').parent().parent().find(".note_w").attr('disabled','disabled');
						document.getElementById("radio_3").checked = "cheched";
					}
					
					/* if(month == null || month == ''){
						$("#startDate_createTime").val(order.endtime);
						$('#radio_3').parent().parent().find('.note_w').removeAttr('disabled');
						$('#radio_1').parent().parent().find(".note_w").attr('disabled','disabled');
						$('#radio_2').parent().parent().find(".note_w").attr('disabled','disabled');
						document.getElementById("radio_3").checked = "cheched";
					} */
					
					$("#mark").val(order.remarks);
					
					//判断审核流程到了哪一步,只在新建订单/续订订单/提交订单审核流程可编辑订单
					var activityStatu = data.activityStatu;
					//alert("审核流程到了："+activityStatu);
					//判断是否有编辑权限
					//没有编辑权限
					if(data.code == 0){
						changeDisable();
						$('#demandquantity').attr('disabled','disabled');
						$('#mark').attr('disabled','disabled');
						$('#addsub').attr('disabled','disabled');
					}
					//有编辑权限
					else{
						//有编辑权限时，审核过程中只有新建订单和续订订单可编辑，只在新建订单、续订订单、提交订单申请时可编辑订单。
						//有编辑权限时，审核通过不可编辑
						if(verify == 1 || verify == 3 || verify == 4){//订单已审核、退订审核中、开启审核中，不可编辑
							//alert("订单审核通过，只能查看不可编辑");
							changeDisable();
							$('#demandquantity').attr('disabled','disabled');
							$('#mark').attr('disabled','disabled');
							$('#addsub').attr('disabled','disabled');
						}else if(verify == 2){//续订审核中，可编辑
							if(activityStatu == "续订订单" || activityStatu == "提交订单申请"){
								changeDisable();
							}else{
								changeDisable();
								$('#demandquantity').attr('disabled','disabled');
								$('#mark').attr('disabled','disabled');
								$('#addsub').attr('disabled','disabled');
							}
						}else{
							if(activityStatu == "新建订单" || activityStatu == "提交订单申请"){
								
							}else{
								changeDisable();
								$('#demandquantity').attr('disabled','disabled');
								$('#mark').attr('disabled','disabled');
								$('#addsub').attr('disabled','disabled');
							}
						}
					}
				}
			
			});
			$('#NewOrderModal').modal('show');
			
			
			//点击子订单效果$('#myTabul li:eq(1)').hide();
			$('#myTabul li:eq(1)').click(function (e) {
  				//切换子订单前先看table有无数据，若有先清空
  				$("#tabletest tr:not(:first)").empty("");  
  				$.ajax({
  					url:'${base}order/childordershow.do',
  					type:'post',
  					data:{id : selectedIds},
  					async:false,
  					cache: false,
  					success:function(data){
  						var result = eval("("+data+")");
  						//alert("ordernum="+result[0].ordernum);
  						for(i in result){
			                var tr;
			                tr='<td>'+result[i].subordernum+'</td>'+'<td>'+result[i].contactname+'</td>'+'<td>'+result[i].renewnum+'</td>'+'<td>'+result[i].createtimeStr+'</td>'+'<td>'+result[i].remark+'</td>';
			                $("#tabletest").append('<tr>'+tr+'</tr>');
		           		}
  					}
  				});	
			})
		}
		
		//检查字符串实际长度
		/* function Reallength(str){
			var realLength = 0, len = str.length, charCode = -1;  
		    for ( var i = 0; i < len; i++) {  
		        charCode = str.charCodeAt(i);  
		        if (charCode >= 0 && charCode <= 128)  
		            realLength += 1;  
		        else  
		            realLength += 2;  
		    }  
		    return realLength; 
		} */
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
		
		function addSubmit(){
			//需求数量检测
			var demandquantity = $("#demandquantity").val().trim();
			var demandquantitylength = 8;
			var checkdemandquantity = /^\+?[1-9][0-9]*$/; 
			//var len = demandquantity.length;
			if(demandquantity == ""){
				alert("请输入需求数量");
				var demandquantityInput = document.getElementById("demandquantity");
				demandquantityInput.focus();
				return false;
			}else if(!checkdemandquantity.test(demandquantity)){
				alert("您输入的需求数量格式不对，请重新输入");
				var demandquantityInput = document.getElementById("demandquantity");
				demandquantityInput.focus();
				return false;
			}else if(Reallength(demandquantity) > demandquantitylength){
				alert("您输入的需求数量长度超过"+demandquantitylength+ "，请重新输入");
				var demandquantityInput = document.getElementById("demandquantity");
				demandquantityInput.focus();
				return false;
			}
			
			//授权类型检测
			/* var licensetype = $("#licensetype").val();
			if(licensetype == ""){
				alert("请选择一个《授权类型》!");
				var licensetypeInput = document.getElementById("licensetype");
				licensetypeInput.focus();
				return false;
			} */
			
			//到期天数提醒检测
			var reminderdays = $("#reminderdays").val().trim();
			var reminderdayslength = 8;
			var checkreminderdays = /^\+?[1-9][0-9]*$/; 
			var len = reminderdays.length;
			if(reminderdays != ""){
				if(!checkreminderdays.test(reminderdays)){
					alert("您输入的到期提醒天数格式不对，请重新输入");
					var reminderdaysInput = document.getElementById("reminderdays");
					reminderdaysInput.focus();
					return false;
				}else if(Reallength(reminderdays) > reminderdayslength){
					alert("您输入的到期提醒天数长度超过"+reminderdayslength+"，请重新输入");
					var reminderdaysInput = document.getElementById("reminderdays");
					reminderdaysInput.focus();
					return false;
				}
			}
			
			//绑定方式检测
			var bindingmode = $("#bindingmode").val();
			if(bindingmode == ""){
				alert("请选择一个绑定方式");
				var bindingmodeInput = document.getElementById("bindingmode");
				bindingmodeInput.focus();
				return false;
			}
			
			var startTimeByContract = $("#startTimeByContract").val();
			var startTimeByActive = $("#startTimeByActive").val();
		    var startDate_createTime = $("#startDate_createTime").val();
		    var data = {};
		    /* var softwareerpnumber = $("#erpCode").val();
		    var softwaretypeversion = $("#softwaretypeversion").val();
		    data.softwareerpnumber = softwareerpnumber;
			data.softwaretypeversion = softwaretypeversion; */
			data.demandquantity = demandquantity;
			var licensetype = $("#licensetype").val();
			data.licensetype = licensetype;
			data.reminderdays = reminderdays;
			data.bindingmode = bindingmode;
		    
			var selectedIds = getCheckedRowValue("");
			data.id = selectedIds;
			
			//有效期检测
			var val=$('input:radio[name="orderActiveStatus"]:checked').val();
			data.orderActiveStatus = val;
			if(val == 1 ){
				var month1 = $("#month_1").val().trim();
				var month1length = 4;
				var checkmonth1 = /^\+?[1-9][0-9]*$/; 
				//var len = month1.length;
				if(month1 != ""){
					if(!checkmonth1.test(month1)){
						alert("您输入的月份格式不对，请重新输入");
						var month1Input = document.getElementById("month_1");
						month1Input.focus();
						return false;
					}else if(Reallength(month1) > month1length){
						//alert("您输入的月份超长，请重新输入");
						alert("您输入的月份长度超过"+month1length+"，请重新输入");
						var month1Input = document.getElementById("month_1");
						month1Input.focus();
						return false;
					}
				}else{
					alert("请输入月份");
					var month1Input = document.getElementById("month_1");
					month1Input.focus();
					return false;
				}
				
				if(startTimeByContract == ""){
					alert("请输入合同生效日期");
					var startTimeByContractInput = document.getElementById("startTimeByContract");
					startTimeByContractInput.focus();
					return false;
				}
				data.month = month1;
				data.starttime = startTimeByContract;
			}else if(val == 2){
				var month2 = $("#month_2").val().trim();
				var month2length = 4;
				var checkmonth2 = /^\+?[1-9][0-9]*$/; 
				//var len = month2.length;
				if(month2 != ""){
					if(!checkmonth2.test(month2)){
						alert("您输入的月份格式不对，请重新输入");
						var month2Input = document.getElementById("month_2");
						month2Input.focus();
						return false;
					}else if(Reallength(month2) > month2length){
						//alert("您输入的月份超长，请重新输入");
						alert("您输入的月份长度超过"+month2length+"，请重新输入");
						var month2Input = document.getElementById("month_2");
						month2Input.focus();
						return false;
					}
				}else{
					alert("请输入月份");
					var month2Input = document.getElementById("month_2");
					month2Input.focus();
					return false;
				}
				
				if(startTimeByActive == ""){
					alert("请输入产品激活日期");
					var startTimeByActiveInput = document.getElementById("startTimeByActive");
					startTimeByActiveInput.focus();
					return false;
				}
				data.month = month2;
				data.tacitstarttime = startTimeByActive;
			}else if(val == 3){
				if(startDate_createTime == ""){
					alert("请输入永久有效日期");
					var startDate_createTimeInput = document.getElementById("startDate_createTime");
					startDate_createTimeInput.focus();
					return false;
				}
				data.endtime = startDate_createTime;
			}else{
				 alert("请选择一种有效期");
				 return false;
			}
			
			var orderid = $('#updateId').val();
			data.id = orderid;
			var companyid = $('#companyId').val();
			data.companyid = companyid;
			var projectid = $('#projectId').val();
			data.projectid = projectid;
			var remarks = $("#mark").val();
			if(Reallength(remarks)>=512){
				alert("您输入的补充说明长度超过"+512+",请重新输入");
				var remarkInput = document.getElementById("mark");
				remarkInput.focus();
				return false;
			}
			data.remarks = remarks; 
			
			//added by wangxiwei in 20180313
			var companyName = $('#companyName').val();
			var projectName = $('#projectName1').val();
			var demandquantity = $('#demandquantity').val();
			var remark = $('#mark').val();
			var activitiTag = $('#activitiTag').val();
			var companyId = $('#companyId').val();
			data.companyName = companyName;
			data.projectName = projectName;
			data.demandquantity = demandquantity;
			data.remark = remark;
			data.companyId = companyId;
			//------------------------------
			
			//子订单标识-》区分续订和退订
			var renew = $("#renew").val();
			//获取客户商务人员姓名
			var subordercontact = $("#subordercontactname").val();
			//提交时区分是否为子订单编辑
			var renewverify = $("#renewverify").val();
			
			if(renew!=null&&renew!=""){//新增续订或退订子订单
				$.ajax({ 
					type        : "POST",
					url         : "${ctx}/order/subordersave.do?renew=" + renew+"&subordercontact="+subordercontact,
					data        : data,
					contentType : "application/x-www-form-urlencoded;charset=utf-8;",
					dataType    : "json",
					cache		: false,	
					success: function(info) {
						if (info.code == 1) {
							if(renew=="xuding"){
								$('#myDlgContent').text('订单续订成功!');
							}else{
								$('#myDlgContent').text('订单退订成功!');
							}
							
							$('#myModal').on('hidden.bs.modal', function (e) {
								gotourl('${ctx}/order/show.do?page=${page}');
							});
							$('#myModal').modal('show');
						}
						else if (info.code == 0) {
							alert(info.msg);
							gotourl('${ctx}/order/show.do?page=${page}');
						}
						else if (info.code == -1) {
							alert("订单续订/退订异常");
							gotourl('${ctx}/order/show.do?page=${page}');
						}					
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {    
						alert(XMLHttpRequest.status + textStatus);    
					} 
					
				});
			}else if(renewverify!=null&&renewverify!=""&&renewverify==2){//编辑续订子订单，退订子订单不可编辑
				$.ajax({ 
					type        : "POST",
					url         : "${ctx}/order/subordersave.do?renewverify=" + renewverify,
					data        : data,
					contentType : "application/x-www-form-urlencoded;charset=utf-8;",
					dataType    : "json",
					cache		: false,	
					success: function(info) {
						if (info.code == 1) {
							$('#myDlgContent').text('续订订单编辑成功!');
							$('#myModal').on('hidden.bs.modal', function (e) {
								gotourl('${ctx}/order/show.do?page=${page}');
							});
							$('#myModal').modal('show');
						}
						else if (info.code == 0) {
							alert(info.msg);
							gotourl('${ctx}/order/show.do?page=${page}');
						}
						else if (info.code == -1) {
							alert("续订订单编辑异常");
							gotourl('${ctx}/order/show.do?page=${page}');
						}					
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {    
						alert(XMLHttpRequest.status + textStatus);    
					} 
					
				});
			}else{//新增订单
				$.ajax({ 
					type        : "POST",
					url         : "${ctx}/order/save.do?activitiTag=" + activitiTag,
					data        : data,
					contentType : "application/x-www-form-urlencoded;charset=utf-8;",
					dataType    : "json",
					cache		: false,	
					success: function(info) {
						if (info.code == 1) {
							$('#myDlgContent').text('订单保存成功!');
							$('#myModal').on('hidden.bs.modal', function (e) {
								gotourl('${ctx}/order/show.do?page=${page}');
							});
							$('#myModal').modal('show');
						}
						else if (info.code == 0) {
							alert(info.msg);
							gotourl('${ctx}/order/show.do?page=${page}');
						}
						else if (info.code == -1) {
							alert("订单保存异常");
							gotourl('${ctx}/order/show.do?page=${page}');
						}					
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {    
						alert(XMLHttpRequest.status + textStatus);    
					} 
					
				});
			}

			/* $.ajax({ 
				type        : "POST",
				url         : "${ctx}/order/save.do?activitiTag=" + activitiTag+ "&renew="+renew+"&subordercontact="+subordercontact,
				data        : data,
				contentType : "application/x-www-form-urlencoded;charset=utf-8;",
				dataType    : "json",
				cache		: false,	
				success: function(info) {
					if (info.code == 1) {
						$('#myDlgContent').text('保存成功!');
						$('#myModal').on('hidden.bs.modal', function (e) {
							gotourl('${ctx}/order/show.do?page=${page}');
						});
						$('#myModal').modal('show');
					}
					else if (info.code == 0) {
						alert(info.msg);
					}
					else if (info.code == -1) {
						alert("新增订单异常");
					}					
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {    
					alert(XMLHttpRequest.status + textStatus);    
				} 
				
			}); */
	}
		
		function closeOrder(){
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
			
			//var selectedIds = getCheckedRowValue("");
			if(selectedIds == ""){
				alert("请选择一个订单停止");
				return false;
			}
			var selectedId = selectedIds.split(",");
			if (selectedId.length != 1) {
				alert("请选择一个订单停止");
				return false;
			}
			
			var objs = document.getElementsByName('checkRow_');
			var orderStau = null;
			var orderVervify = null;
			for (var i = 0; i < objs.length; i = i + 1) {
				if (objs[i].checked) {
					//获取订单状态
					orderStau = $(objs[i]).parents('td').parents('tr').children('td').eq(8).text();
					//获取审核状态
					orderVervify = $(objs[i]).parents('td').parents('tr').children('td').eq(9).text();
					
					if(orderVervify.match("未审核")||orderVervify.match("续订审核中")||orderVervify.match("退订审核中")||orderVervify.match("开启审核中")){
						alert("请选择已审核状态下的订单来强制停止");
						gotourl('${ctx}/order/show.do?page=${page}'); 
						return false;
					}
					if(orderStau.match("强制停止") || orderStau.match("结束")){
						alert("请选择启用状态下的订单来强制停止");
						gotourl('${ctx}/order/show.do?page=${page}');
						return false;
					}
				}
			}
			
			if(confirm("强制停止订单会导致授权下发终止，确认强制停止此订单吗？")){
				//var querydata ={};
				//querydata.id = selectedIds;
				$.ajax({
					type        : "POST",
					url         : "${ctx}/order/closeOrder.do",
					//data        : querydata,
					data        : {id : selectedIds},
					contentType : "application/x-www-form-urlencoded;charset=utf-8;",
					dataType    : "json",
					cache		: false,
					success: function(info){
						if (info.code == 1) {
							//gotourl('${ctx}/order/show.do?page=${1}');
							$('#myDlgContent').text('停止订单成功!');
							$('#myModal').on('hidden.bs.modal', function (e) {
								gotourl('${ctx}/order/show.do?page=${1}');
							});
							$('#myModal').modal('show');
						}
						else if (info.code == 0) {
							alert(info.msg);
						}
						else if (info.code == -1) {
							alert("停止订单异常");
						}	
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {    
						alert(XMLHttpRequest.status + textStatus);    
					} 	
				});
			}
		}
		
		function openOrder(){
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
			
			if(selectedIds == ""){
				alert("请选择一个订单开启");
				return false;
			}
			var selectedId = selectedIds.split(",");
			if (selectedId.length != 1) {
				alert("请选择一个订单开启");
				return false;
			}
			
			var objs = document.getElementsByName('checkRow_');
			var orderStau = null;
			var orderVervify = null;
			for (var i = 0; i < objs.length; i = i + 1) {
				if (objs[i].checked) {
					//获取订单状态
					orderStau = $(objs[i]).parents('td').parents('tr').children('td').eq(8).text();
					//获取审核状态
					orderVervify = $(objs[i]).parents('td').parents('tr').children('td').eq(9).text();
					
					if(orderVervify.match("开启审核中")){
						alert("订单开启审核中,请按流程走完");
						gotourl('${ctx}/order/show.do?page=${page}'); 
						return false;
					}
					if(orderStau.match("启用") || orderStau.match("结束")){
						alert("请选择强制停止状态下的订单来开启");
						gotourl('${ctx}/order/show.do?page=${page}');
						return false;
					}
				}
			}
			
			if(confirm("确认开启此订单吗？")){
				$.ajax({
					type        : "POST",
					url         : "${ctx}/order/openOrder.do",
					data        : {id : selectedIds},
					contentType : "application/x-www-form-urlencoded;charset=utf-8;",
					dataType    : "json",
					cache		: false,
					success: function(info){
						if (info.code == 1) {
							$('#myDlgContent').text('开启订单成功!');
							$('#myModal').on('hidden.bs.modal', function (e) {
								gotourl('${ctx}/order/show.do?page=${1}');
							});
							$('#myModal').modal('show');
						}
						else if (info.code == 0) {
							alert(info.msg);
						}
						else if (info.code == -1) {
							alert("开启订单异常");
						}	
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {    
						alert(XMLHttpRequest.status + textStatus);    
					} 	
				});
			}
		}
		
		function initActivitiDB(){
			$.ajax({
				type        : "POST",
				url         : "${ctx}/order/initActivitiDb.do",
				contentType : "application/x-www-form-urlencoded;charset=utf-8;",
				dataType    : "json",
				cache		: false,
				success: function(info){
					alert("info");
				}
			});
		}
		
		/* $(document).ready(function() {
 			var currentStatus = $("#currentStatus").val();
			if(currentStatus != null){
				$("#" + currentStatus).click();
			}  
			var checkedNodetid = $('#hiddenInput').val();
			$("#" + checkedNodetid + "_a").attr('class', 'curSelectedNode');
			
			
			/* if(checkedNodetid == '' || checkedNodetid == null){
				return false;
			} else {
				$("#newBtn").removeAttr("disabled");
			}
			
 		    if(!$("input[type='checkbox']").is(':checked'))  
		    {  
		    	
		    	$("#newBtn").attr("disabled", "disabled");
				$("#detailBtn").attr("disabled", "disabled");
				$("#orderAuthBtn").attr("disabled", "disabled");
				$("#closeBtn").attr("disabled", "disabled");
		    } else{  
		    	$("#newBtn").removeAttr("disabled");
				$("#detailBtn").removeAttr("disabled");
				$("#orderAuthBtn").removeAttr("disabled");
				$("#closeBtn").removeAttr("disabled");
		    }   

		}); */
		
		/* $(document).ready(function() {
			var selectedId = getCheckedRowValue("");
	
			alert(selectedId);
			
			if(selectedId == '' || selectedId == null){
				return false;
			} else {
				$("#detailBtn").removeAttr("disabled");
				$("#orderAuthBtn").removeAttr("disabled");
				$("#closeBtn").removeAttr("disabled");
			}
			
		}); */
		
		function specieSelChange(selBox) {
			var data = selBox.value;
			$.ajax({ 
				type        : "POST",
				url         : "${base}order/querySoftwareNameByERPcode.do",
				data        :  {erpCode : data},
				contentType : "application/x-www-form-urlencoded;charset=utf-8;",
				scriptCharset: 'utf-8',
				success: function(info) {
		            $('#softwaretypeversion').val(info);
				}
			});
		}
		
		function fix(bel){
			var result = bel.value;
			if(result == '1'){
				$('#radio_1').parent().parent().find('.note_w').removeAttr('disabled');
				$('#radio_2').parent().parent().find(".note_w").attr('disabled','disabled');
				$('#radio_3').parent().parent().find(".note_w").attr('disabled','disabled'); 
				
				$("#month_1").removeAttr('disabled');
				$("#month_2").val("");
				$("#startTimeByActive").val("");
				$("#startDate_createTime").val("");
			} else if(result == '2'){
				$('#radio_2').parent().parent().find('.note_w').removeAttr('disabled');
				$('#radio_1').parent().parent().find(".note_w").attr('disabled','disabled');
				$('#radio_3').parent().parent().find(".note_w").attr('disabled','disabled'); 
				
				$("#month_2").removeAttr('disabled');
				$("#month_1").val("");
				$("#startTimeByContract").val("");
				$("#startDate_createTime").val("");
			} else if(result == '3'){
				$('#radio_3').parent().parent().find('.note_w').removeAttr('disabled');
				$('#radio_1').parent().parent().find(".note_w").attr('disabled','disabled');
				$('#radio_2').parent().parent().find(".note_w").attr('disabled','disabled'); 
				
				$("#month_1").val("");
				$("#startTimeByContract").val("");
				$("#month_2").val("");
				$("#startTimeByActive").val("");
				
				$("#startDate_createTime").val('2099-12-31');
			}
		}
</SCRIPT>
<script>
$(".form_datetime").datetimepicker({
	/* format: "yyyy-mm-dd hh:ii:ss", */
    minView: "month",
    format: "yyyy-mm-dd", 
    autoclose: true,
    todayBtn: true,
   	clearBtn:true,
    language:'zh-CN',
    pickerPosition: "bottom-left"
});
</script>
<script src="${ctx}/js/common.js"></script>
<script src="${ctx}/js/jquery.ztree.all-3.5.min.js"></script>
<%@ include file="/WEB-INF/views/common/myModal.jsp" %>	
<%@ include file="/WEB-INF/views/foot_n.jsp" %>