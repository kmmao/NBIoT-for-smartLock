<%@page import="com.routon.plcloud.common.decorator.PageCheckboxDecorator"%>
<%@page import="com.routon.plcloud.common.decorator.PageDateTimeDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ include file="/WEB-INF/views/head_n.jsp" %>
<style>
	.redpoint {
	display: inline-block;
	min-width: 10px;
	padding: 3px 7px;
	font-size: 12px;
	font-weight: bold;
	line-height: 1;
	color: #fff;
	text-align: center;
	white-space: nowrap;
	vertical-align: baseline;
	background-color: #FF0000;
	border-radius: 10px;
}

	.greenpoint {
	display: inline-block;
	min-width: 10px;
	padding: 3px 7px;
	font-size: 12px;
	font-weight: bold;
	line-height: 1;
	color: #fff;
	text-align: center;
	white-space: nowrap;
	vertical-align: baseline;
	background-color: #7FFF00;
	border-radius: 10px;
}
</style>
	<link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.css">
	<script src="${ctx}/js/bootstrap-datetimepicker.js"></script>
	<script src="${ctx}/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="${ctx}/js/jquery-easyui/jquery.easyui.min.js"></script>
	<div class="panel panel-default" style="width:1550px;">
  		<div class="panel-heading" style="width:1550px;height:48px;">
  			<h5><strong>NB-IoT设备管理</strong></h5>
  		</div>
  		<div class="panel-body">
  		<div class="navbar-collapse collapse" >
  			<ul class="nav navbar-nav">
  			<c:forEach items="${devices}" var="nbdevices" varStatus="status">
  				<li style="margin-right: 25px;"><img src="${ctx}/images/machine.png" style="cursor:pointer;" onclick='edit(this)' height="80" width="80">
  				<!-- <form role="form"> -->
  				<div>
  					<div class="form-group">
    					<font>地址</font>
    					<font><strong>${nbdevices.address}</strong></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    					<font>房间号</font>
    					<font><strong>${nbdevices.roomnum}</strong></font>
    				</div>
  					<div class="form-group">
    					<font>设备ID</font>
    					<font><strong>${nbdevices.deviceId}</strong></font>
    				</div>
<%--     				<div class="form-group">
    					<font>设备名</font>
    					<font><strong>${nbdevices.deviceInfo.name}</strong></font>
    				</div> --%>
    				<div class="form-group">
    					<c:if test="${nbdevices.deviceInfo.status == \"ONLINE\"}">
    						<span class="greenpoint"></span>
    						<font><strong>设备在线</strong></font>
    					</c:if>
    					<c:if test="${nbdevices.deviceInfo.status == \"OFFLINE\"}">
    						<span class="redpoint"></span>
    						<font><strong>设备离线</strong></font>
    					</c:if>
    					<c:if test="${nbdevices.deviceInfo.status == \"INBOX\"}">
    						<span class="redpoint"></span>
    						<font><strong>${nbdevices.deviceInfo.status}</strong></font>
    					</c:if>
    					<c:if test="${nbdevices.deviceInfo.status == \"ABNORMAL\"}">
    						<span class="redpoint"></span>
    						<font><strong>设备不正常</strong></font>
    					</c:if>
    					<c:forEach items="${nbdevices.services}" var="nbinfo">
    							<c:if test="${nbinfo.serviceId == \"LockStatus\"}">
    								<c:if test="${nbinfo.data.LockState == 0}">
    									<font><strong>锁状态未知</strong></font>
    								</c:if>
    								<c:if test="${nbinfo.data.LockState == 1}">
    									<font><strong>已上锁</strong></font>
    								</c:if>
    								<c:if test="${nbinfo.data.LockState == 2}">
    									<font><strong>已解锁</strong></font>
    								</c:if>
    								<font><strong>电量${nbinfo.data.Battery}%</strong></font>
    								<c:if test="${nbinfo.data.Signal < 30}">
    									<font><strong>信号弱</strong></font>
    								</c:if>
    								<c:if test="${nbinfo.data.Signal > 50}">
    									<font><strong>信号强</strong></font>
    								</c:if>
    							</c:if>
    					</c:forEach>
    					<font><strong>电池供电</strong></font>
    				</div>
    				<div class="form-group">
    					<button class="btn btn-default btn-sm" onclick="viewer(this)" >查看授权人员</button>
    				</div>
				<!-- </form> --></div>
  				</li>
  			</c:forEach>
  			</ul>
  		</div>
  		
  		<div class="modal fade" id="NBModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" aria-hidden="true">
  			<div class="modal-dialog" style="width: 450px;">
  				<div class="modal-content">
  					<div class="modal-header">
                		<h6 class="modal-title" id="myModalLabel">指令下发</h6>
            		</div>
            		<div class="modal-body">
            		<label for="title" class="col-sm-3 control-label">指令类型：</label>
            				<select id="command" name="command" class="form-control" style="width:200px;" onchange="specieSelChange(this)">
			      				<option value ="0">下发白名单</option>
								<option value ="1">删除白名单</option>
								<option value ="2">开关门操作</option>
								<option value ="3">远程开锁</option>
								<!-- <option value ="4">清除刷卡记录</option> -->
								<option value ="5">同步设备网络时间</option>
				  			</select>
            		<form id="form00" name="form00" action="${ctx}/nbiot/sendWhiteNames.do"  method="post">
            			<div class="form-group">
            				<label for="title" class="col-sm-4 control-label">设备ID：</label>
            				<input id="deviceId0" name="deviceId" type="text" class="form-control" readonly="readonly" class="wang">
            			</div>
            			<div class="form-group">
            				<label for="title" class="col-sm-4 control-label">下发人员：</label>
            				<select id="personId0" name="personId" class="form-control" onchange="selectRegister(this)">
			      				<option value="1">―请选择下发人员―</option>  
				  			</select>
            			</div>
            			<div class="form-group">
            				<label for="title" class="col-sm-5 control-label">人员关联的卡ID：</label>
            				<input id="card1" name="card1" type="text" class="form-control" placeholder="请输入有效数据" readonly="readonly">
            			</div>
            			<div class="form-group">
            				<label for="title" class="col-sm-5 control-label">人员权限取值：</label>
            				<select id="authority" name="authority" class="form-control">
			      				<option value ="1">100%</option>
								<option value ="2">50%</option>
				  			</select>
            			</div>
            			<div class="form-group">
            				<label for="title" class="col-sm-5 control-label">验证方式：</label>
            				<select id="check" name="check" class="form-control">
			      				<option value ="volvo">任意验证</option>
				  			</select>
            			</div>
            			<div class="form-group">
            				<label for="title" class="col-sm-5 control-label">白名单有效期：</label>
            				<input id="timebegin" name="timebegin" type="text" class="form-control" placeholder="请输入有效起始时间"> &nbsp;-
  							<input id="timeend" name="timeend" type="text" class="form-control" placeholder="请输入有效结束时间">
            			</div>
            			<div class="form-group">
            				<label for="title" class="col-sm-5 control-label">白名单类型：</label>
            				<select id="whiteNames" name="whiteNames" class="form-control">
								<option value ="1">卡1</option>
								<option value ="2">修改人员基础信息</option>
								<option value ="3">蓝牙信息</option>
				 			</select>
            			</div>
            			</form>
            			<form id="form01" name="form01" action="${ctx}/nbiot/delWhiteNames.do"  method="post">
            				<div class="form-group">
            					<label for="title" class="col-sm-4 control-label">设备ID：</label>
            					<input id="deviceId1" name="deviceId" type="text" class="form-control" readonly="readonly">
            				</div>
            				<div class="form-group">
            					<label for="title" class="col-sm-4 control-label">下发人员：</label>
            					<select id="personId1" name="personId" class="form-control" onchange="selectRegister(this)">
			      					<option value="1">―请选择下发人员―</option>  
				  				</select>
            				</div>
            				<div class="form-group">
  								<input id="r1_1" type="radio" value="1" name="delsel">删除所有白名单
								<input id="r1_2" type="radio" value="0" name="delsel">删除单个白名单
  							</div>
            			</form>
            			<form id="form02" name="form02" action="${ctx}/nbiot/openAndClose.do"  method="post">
            				<div class="form-group">
            					<label for="title" class="col-sm-4 control-label">设备ID：</label>
            					<input id="deviceId2" name="deviceId" type="text" class="form-control" readonly="readonly">
            				</div>
            				<div class="form-group">
            					<label for="title" class="col-sm-4 control-label">下发人员：</label>
            					<select id="personId2" name="personId" class="form-control" onchange="selectRegister(this)">
			      					<option value="1">―请选择下发人员―</option>  
				  				</select>
            				</div>
            				<div class="form-group">
            					<label for="title" class="col-sm-4 control-label">手机唯一标识:</label>
  								<input id="phone" name="phone" type="text" class="form-control" placeholder="请输入手机唯一标识">
  							</div>
  							<div class="form-group">
  								<input id="r2_1" type="radio" value="1" name="operaCode">开
								<input id="r2_2" type="radio" value="0" name="operaCode">关
  							</div>
            			</form>
            			<form id="form03" name="form03" action="${ctx}/nbiot/lock.do"  method="post">
            				<div class="form-group">
            					<label for="title" class="col-sm-4 control-label">设备ID：</label>
            					<input id="deviceId3" name="deviceId" type="text" class="form-control" readonly="readonly">
            				</div>
            				<div class="form-group">
  								<input id="r1_3" type="radio" value="1" name="lockStatus">开
								<input id="r2_4" type="radio" value="0" name="lockStatus">关
  							</div>
            			</form>
            			<form id="form05" name="form05" action="${ctx}/nbiot/synTime.do"  method="post">
            				<div class="form-group">
            					<label for="title" class="col-sm-4 control-label">设备ID：</label>
            					<input id="deviceId5" name="deviceId" type="text" class="form-control" readonly="readonly">
            				</div>
            			</form>
            			<div id="commandDiv2" style="margin-bottom: 10px;font-size:15px"><font id="commandFont2" color="red"></font></div>
            		</div>
            <div class="modal-footer">
                <button id="closeButton" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" id="send0" name="send" class="btn btn-primary" onclick="commandSubmit(0)">下发</button>
                <button type="button" id="send1" name="send" class="btn btn-primary" onclick="commandSubmit(1)">下发</button>
                <button type="button" id="send2" name="send" class="btn btn-primary" onclick="commandSubmit(2)">下发</button>
                <button type="button" id="send3" name="send" class="btn btn-primary" onclick="commandSubmit(3)">下发</button>
                <button type="button" id="send4" name="send" class="btn btn-primary" onclick="commandSubmit(5)">下发</button>
            </div>
  				</div>
  			</div>
  		</div>
  		
 		<div class="modal fade" id="taskInfoView" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" aria-hidden="true">
 			<div class="modal-dialog" style="width: 500px;">
 				<div class="modal-content">
 					<div class="modal-header">
                		<h4 class="modal-title" id="checkLable">授权人员查看</h4>
            		</div>
            	<div class="modal-body">
            		<div id="authorlist"></div>
            	</div>
            	<div class="modal-footer">
            		<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            	</div>
 			</div>
 		  </div>
 		</div>
 		
 		<div class="modal fade" id="registerPhoto" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" aria-hidden="true">
 			<div class="modal-dialog" style="width: 250px;">
 				<div class="modal-content">
 					<div class="modal-header">
                		<h4 class="modal-title" id="checkLable">新注册人员</h4>
            		</div>
            	<div class="modal-body">
       				<div id="registerDiv" style="text-align:center;">
            			<img id= "curPhoto" src="" height="130" width="89" />
            			<div id="registerInfo"></div>
            		</div>
            	</div>
            	<div class="modal-footer">
            		<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            	</div>
 			</div>
 		  </div>
 		</div>
 		
 		
  		
<%--   		  <div class="btn-group">
  		  	<div style="margin-bottom: 20px;">
  			请选择下发指令类型：<div class="btn-group">
			    <div class="col-sm-4">
			      <select id="command" name="command" class="form-control" style="width:200px;" onchange="specieSelChange(this)">
			      	<option value ="0">下发白名单</option>
					<option value ="1">删除白名单</option>
					<option value ="2">开关门操作</option>
					<option value ="3">远程开锁</option>
					<!-- <option value ="4">清除刷卡记录</option> -->
					<option value ="5">同步设备网络时间</option>
				  </select>
			     </div>
			</div>
			</div>
  			<form class="form-inline" role="form0" id="form0" name="form0" action="${ctx}/nbiot/sendWhiteNames.do"  method="post">	  		
  			<strong>下发白名单：</strong><br>
  			<div class="btn-group">
  					设备ID：<input id="deviceId1" name="deviceId" type="text" class="form-control"  value="8ab1c637-882b-4e28-b089-4585c6289776" readonly="readonly">
  				 下发人员:<select id="personId3" name="personId" class="form-control" onchange="selectRegister(this)">
			      	<option value="1">―请选择下发人员―</option>  
				  </select>
  		    </div>
  			<div class="btn-group">
  				人员关联的卡ID：<input id="card1" name="card1" type="text" class="form-control" placeholder="请输入有效数据" readonly="readonly">
			</div>
			<div class="btn-group">
			  人员权限取值:<select id="authority" name="authority" class="form-control">
			      	<option value ="1">100%</option>
					<option value ="2">50%</option>
				  </select>
			</div>
			<div class="btn-group">
			 验证方式：
				 <select id="check" name="check" class="form-control">
			      	<option value ="volvo">任意验证</option>
				  </select>
			</div><br>
			<div style="margin-top: 20px;">
			<div class="btn-group">
  				请输入白名单有效期：<input id="timebegin" name="timebegin" type="text" class="form-control" placeholder="请输入有效起始时间"> &nbsp;-
  				<input id="timeend" name="timeend" type="text" class="form-control" placeholder="请输入有效结束时间">
  			</div>
  			<div class="btn-group">
			      白名单类型:<select id="whiteNames" name="whiteNames" class="form-control">
							<option value ="1">卡1</option>
							<option value ="2">修改人员基础信息</option>
							<option value ="3">蓝牙信息</option>
				  </select>
			</div>
  		    <div class="btn-group">
  				<button type="submit" class="btn btn-primary" >下发</button>
  			</div>
  			</div>
  			</form>
  			<form class="form-inline" role="form" id="form1" name="form1" action="${ctx}/nbiot/delWhiteNames.do"  method="post">  		
  			<strong>删除白名单：</strong><br><div class="btn-group">
  				设备ID：<input id="deviceId2" name="deviceId" type="text" class="form-control" value="8ab1c637-882b-4e28-b089-4585c6289776" readonly="readonly">
  			</div>
  			<div class="btn-group">
  				<!-- <input id="personId1" name="personId" type="text" class="form-control" placeholder="请输入人员ID"> -->
  			下发人员:<select id="personId1" name="personId" class="form-control" onchange="selectRegister(this)">
			      	<option value="1">―请选择下发人员―</option>  
				  </select>
  			</div>
  		    <div class="btn-group">
  				<button type="submit" class="btn btn-primary" name="delsel" value="1">删除所有白名单</button>
  			</div>
  			<div class="btn-group">
  				<button type="submit" class="btn btn-primary" name="delsel" value="0" >删除单个白名单</button>
  			</div>
  			</form>
  			<form class="form-inline" role="form" id="form2" name="form2" action="${ctx}/nbiot/openAndClose.do"  method="post">  		
  			<strong>开关门操作：</strong><br><div class="btn-group">
  				设备ID：<input id="deviceId3" name="deviceId" type="text" class="form-control" value="8ab1c637-882b-4e28-b089-4585c6289776" readonly="readonly">
  			</div>
  			<div class="btn-group">
  				<!-- <input id="personId2" name="personId" type="text" class="form-control" placeholder="请输入人员ID"> -->
  				下发人员:<select id="personId2" name="personId" class="form-control" onchange="selectRegister(this)">
			      	<option value="1">―请选择下发人员―</option>  
				  </select>
  			</div>
  			<div class="btn-group">
  				手机唯一标识:<input id="phone" name="phone" type="text" class="form-control" placeholder="请输入手机唯一标识">
  			</div>
  			<div class="btn-group">
  				<input id="r1_1" type="radio" value="1" name="operaCode">开
				<input id="r2_2" type="radio" value="0" name="operaCode">关
  			</div>
  			<div class="btn-group">
  				<button type="submit" class="btn btn-primary" >下发</button>
  			</div>
  			</form>
  			<form class="form-inline" role="form" id="form3" name="form3" action="${ctx}/nbiot/lock.do"  method="post">  		
  			<strong>远程开锁：</strong><br><div class="btn-group">
  				设备ID：<input id="deviceId4" name="deviceId" type="text" class="form-control" value="8ab1c637-882b-4e28-b089-4585c6289776" readonly="readonly">
  			</div>
  			<div class="btn-group">
  				<input id="r1_3" type="radio" value="1" name="lockStatus">开
				<input id="r2_4" type="radio" value="0" name="lockStatus">关
  			</div>
  			<div class="btn-group">
  				<button type="submit" class="btn btn-primary" >下发</button>
  			</div>
  			</form>
  			<form class="form-inline" role="form" id="form4" name="form4" action="${ctx}/nbiot/clearRecord.do"  method="post">  		
  			<strong>清除刷卡记录：</strong><br><div class="btn-group">
  				<input id="deviceId5" name="deviceId" type="text" class="form-control" value="8ab1c637-882b-4e28-b089-4585c6289776" readonly="readonly">
  			</div>
  			<div class="btn-group">
  				<button type="submit" class="btn btn-primary" >下发</button>
  			</div>
  			</form>
  			<form class="form-inline" role="form" id="form5" name="form5" action="${ctx}/nbiot/synTime.do"  method="post">  		
  			<strong>同步设备网络时间：</strong><br><div class="btn-group">
  				设备ID：<input id="deviceId6" name="deviceId" type="text" class="form-control" value="8ab1c637-882b-4e28-b089-4585c6289776" readonly="readonly">
  			</div>
  			<div class="btn-group">
  				<button type="submit" class="btn btn-primary" >下发</button>
  			</div>
  			</form>
  		  </div> --%>
  		  <div id="commandDiv" style="margin-bottom: 10px;font-size:15px"><font id="commandFont" color="red"></font></div>
  		</div>
  		
  	</div>
  	<div class="panel panel-default">
  		<div class="panel-heading" style="width:1528px;height:48px;">
  			<h5><strong>设备信息状态</strong></h5>
  		</div>
  		<div class="panel-body" style="height: 530px;">
  			<!-- <button id="openReceive" type="button" class="btn btn-primary" style="margin-bottom: 5px;">开启指令接收</button></br> -->
  			<div id="infoDiv" style="margin-bottom: 10px;font-size:15px"><font id="infoFont" color="red"></font></div>
  			<div id="commandDiv" style="display:none">
  				平台下发指令响应日志：<div id="responstext" align=left style='color: #000000;border: solid 2px black; width: 1480px; height: 220px; overflow: scroll; 
					scrollbar-face-color: #889B9F;
					scrollbar-shadow-color: #3D5054;
					scrollbar-highlight-color: #C3D6DA;
					scrollbar-3dlight-color: #3D5054;
					scrollbar-darkshadow-color: #85989C;
					scrollbar-track-color: #95A6AA;
					scrollbar-arrow-color: #FFD6DA;'>
				</div>设备事件上报指令日志：
				<div id="eventtext" align=left style='color: #000000;border: solid 2px black; width: 1480px; height: 220px; overflow: scroll; 
					scrollbar-face-color: #889B9F;
					scrollbar-shadow-color: #3D5054;
					scrollbar-highlight-color: #C3D6DA;
					scrollbar-3dlight-color: #3D5054;
					scrollbar-darkshadow-color: #85989C;
					scrollbar-track-color: #95A6AA;
					scrollbar-arrow-color: #FFD6DA;'>
				</div>
				
  			</div>
  			<div>
  				设备信息上报（设备信息20分钟上报一次，也可以通过按门锁的开关门按钮立即上报）：<table border="1" id="taskInfoTable" width="100%">
  					<tr style='font-weight: bolder;'><td>设备ID</td><td>电量</td><td>信号强弱</td><td>门状态</td><td>锁状态</td><td>信息上报时间</td></tr>
  				</table><br>
  				刷卡记录：<table border="1" id="taskInfoTable2" width="100%">
  					<tr style='font-weight: bolder;'><td>设备ID</td><td>卡ID</td><td>权限</td><td>验证方式</td><td>是否在有效时间段内</td><td>门状态</td><td>锁状态</td><td>刷卡时间</td><td>操作类型</td></tr>
  				</table>
  			</div>
  		</div>
  	</div>
<script>

	function webSocketConnect(){
		// 指定websocket路径
		var url = 'ws://61.183.225.85:8082/websocket/1';
		//var url = 'ws://172.16.42.134:8080/websocket/1';
        var websocket = new WebSocket(url);
        console.log("初始化websorket：" + url);
        //打开事件
        websocket.onopen = function() {
            //alert("Socket 已打开");
            console.log("Socket 已打开");
            document.getElementById('infoFont').innerHTML='Socket已连接';
        };
        websocket.onmessage = function(event) {
            var receivedMessage = event.data;
            console.log("收到服务器端推送的数据：" + receivedMessage);
            var currentTime = dateFtt("yyyy-MM-dd hh:mm:ss", new Date());
            if(receivedMessage != 'connection opend'){
            	var messageNode = eval('(' + event.data + ')');
            	var messageType = messageNode.notifyType;
            	if(messageType == undefined){
            		$("#responstext").append("【"+ currentTime +"】" + receivedMessage + "<br/>-----------------------------------------------------------<br/>");
            		var messageResult = messageNode.result;
            		/*var resultNode = eval('(' + messageResult + ')');
            		var resultCode = resulNode.resultCode;
            		var resultDetail = resultNode.resultDetail;
            		var resultDetailNode = eval('(' + resultDetail + ')');
            		var resultLast = resultDetailNode.result;*/
            		var resultCode = messageNode.result.resultCode;
            		if(resultCode == 'DELIVERED'){
            			alert("命令已下达...  ");
            			document.getElementById('commandFont').innerHTML='';
            			document.getElementById('commandFont2').innerHTML='';
            			$("button").attr('disabled',false);
            			document.getElementsByName('send').removeAttr("disabled");
            		}
            		if(resultCode == 'SUCCESSFUL'){
                		var resultDetail = messageNode.result.resultDetail.result;
            			var commandHead = resultDetail.substring(0,2);
            			var commandResult;
            			var commandOpe;
            			if(resultDetail.length == 8){
            				commandResult = resultDetail.substring(6);
            			} else if(resultDetail.length == 10){
            				commandResult = resultDetail.substring(8);
            				commandOpe = resultDetail.substring(6,8);
            			}
            			if(commandHead == '51'){
            				if(commandResult == '00'){
            					alert('下发白名单成功！');
            				} else if(commandResult == '01'){
            					alert('下发白名单失败！');
            				}
            			}
            			if(commandHead == '52'){
            				if(commandResult == '00'){
            					alert('删除白名单成功！');
            				} else if(commandResult == '01'){
            					alert('删除白名单失败！');
            				}
            			}
            			if(commandHead == '53'){
            				if(commandResult == '00' && commandOpe == '01'){
            					alert('开锁成功！');
            				} else if(commandResult == '00' && commandOpe == '02'){
            					alert('关锁成功！');
            				} else if(commandResult == '01' && commandOpe == '01'){
            					alert('开锁失败！');
            				} else if(commandResult == '01' && commandOpe == '02'){
            					alert('关锁失败！');
            				}
            			}
            			if(commandHead == '62'){
            				if(commandResult == '00'){
            					alert('同步当前时间成功！');
            				} else if(commandResult == '01'){
            					alert('同步当前时间失败！');
            				}
            			}
            		}
            		if(resultCode == 'TIMEOUT'){
            			alert('下发超时!');
            			$("button").attr('disabled',false);
            			document.getElementById('commandFont').innerHTML='';
            			document.getElementById('commandFont2').innerHTML='';
            		}
            		if(resultCode == 'FAILED'){
            			alert('下发失败!');
            			$("button").attr('disabled',false);
            			document.getElementById('commandFont').innerHTML='';
            			document.getElementById('commandFont2').innerHTML='';
            		}
            	} else{
            		$("#eventtext").append("【"+ currentTime +"】" + receivedMessage + "<br/>-----------------------------------------------------------<br/>");
            	}
            	if(messageType == 'deviceDatasChanged'   || messageType == 'deviceDataChanged'){
            		//alert('ok');
            		var deviceid = messageNode.deviceId;
            		var battery = messageNode.services[0].data.Battery;
            		var signal = messageNode.services[0].data.Signal;
            		var doorstate = messageNode.services[0].data.DoorState;
            		var lockState = messageNode.services[0].data.LockState;
            		var door;
            		var lock;
            		if(doorstate == '0'){
            			door = '未知';
            		} else if(doorstate == '1'){
            			door = '门已上锁';
            		} else if(doorstate == '2'){
            			door = '门已解锁';
            		}
            		if(lockState == '0'){
            			lock = '未知';
            		} else if(lockState == '1'){
            			lock = '已上锁';
            		} else if(lockState == '2'){
            			lock = '已解锁';
            		}
        			var tableObj = document.getElementById('taskInfoTable');
        			var rows = tableObj.rows.length ;
            		if(messageNode.services[0].serviceType == 'LockStatus'){
                    	if (rows == 1) {
             	            $("#taskInfoTable").append("<tr><td>"+deviceid+
                       		   	   "</td><td>"+battery+
                                      "</td><td>"+signal+
                                      "</td><td>"+door+
                                      "</td><td>"+lock+
                                      "</td><td>"+currentTime+
                                      "</td></tr>")
                      		} else {
                    			$('#taskInfoTable tr').each(function(i){
                      	      		var deviceidinTable = $(this).children('td').eq(0).text();
                      	      		if(deviceidinTable == deviceid){
                      	      			$(this).children('td').eq(1)[0].innerHTML = battery;
                      	      			$(this).children('td').eq(2)[0].innerHTML = signal;
                      	      			$(this).children('td').eq(3)[0].innerHTML = door;
                      	      			$(this).children('td').eq(4)[0].innerHTML = lock;
                      	      			$(this).children('td').eq(5)[0].innerHTML = currentTime;
                      	      			/* var tb = document.getElementById('taskInfoTable');
                      	      	 		var td = tb.rows[0].cells[0];
                      	      			td.innerHTML = '222'; */
                      	      		}
                      	   	  		
                      	    	})
                      		}
                    	//收到锁具上传的心跳后，自动同步网络时间
                    	/* $.ajax({ 
                			type        : "POST",
                			url         : "${base}nbiot/synTime.do",
                			data        :  {deviceId : deviceid},
                			contentType : "application/x-www-form-urlencoded;charset=utf-8;",
                			dataType    : "json",
                			cache		: false,
                			success: function(info) {
                				alert(deviceid + "的时间自动同步成功");
                				console.log(deviceid + "的时间自动同步成功");
                			}
                		}); */
                    	
                      } else if(messageNode.services[0].serviceType == 'LockEvent'){
                    	  var verifydata = messageNode.services[0].data.lockEventData;
                    	  var cardid = verifydata.substring(0,20);
                    	  var tempdata = verifydata.substring(20);
                    	  var authority = tempdata.substring(0,2);
                    	  var authorityTag;
                    	  if(authority == '00' || authority == 'FF'){
                    		  authorityTag = '无效值';
                    	  } else if(authority == '01'){
                    		  authorityTag = '全权限';
                    	  } else if(authority == '02'){
                    		  authorityTag = '50%权限';
                    	  } else if(authority == '03'){
                    		  authorityTag = '0%权限';
                    	  }
                    	  
                    	  var verify = tempdata.substring(2,4);
                    	  var verifyTag;
                    	  if(verify == '01'){
                    		  verifyTag = '任意验证';
                    	  } else if(verify == '02'){
                    		  verifyTag = '卡';
                    	  } else if(verify == '03'){
                    		  verifyTag = '密码+指纹';
                    	  } else if(verify == 'FF'){
                    		  verifyTag = '无效值';
                    	  }
                    	  
                    	  var inRightTime = tempdata.substring(4,6);
                    	  var inRightTimeTag;
                    	  if(inRightTime == '01'){
                    		  inRightTimeTag = '有效时间内';
                    	  } else if(inRightTime == '00'){
                    		  inRightTimeTag = '无效时间内';
                    	  } else if(inRightTime == 'FF'){
                    		  inRightTimeTag = '无效值';
                    	  }
                    	  
                    	  var doorLockState = tempdata.substring(6,8);
                    	  var doorLockStateTag;
/*                     	  var doorStatus = doorLockState.substring(0,1);
                    	  var lockStatus = doorLockState.substring(1);
                    	  var doorStatusTag;
                    	  var lockStatusTag; */
                    	  if(doorLockState == '01'){
                    		  doorLockStateTag = "已上锁";
                    	  } else if(doorLockState == '02'){
                    		  doorLockStateTag = "已解锁";
                    	  } else{
                    		  doorLockStateTag = "未知";
                    	  }
                    	  
                    	  var time = tempdata.substring(8,22);
                    	  var year = time.substring(10,14);
                    	  var arr = new Array(year.substring(0,2), year.substring(2,4)).reverse();
                    	  var yearRes = parseInt(arr[0] + arr[1],16);
                    	  var month = parseInt(time.substring(8,10), 16);
                    	  var day = parseInt(time.substring(6,8), 16);
                    	  var hour = parseInt(time.substring(4,6), 16);
                    	  var minute = parseInt(time.substring(2,4), 16);
                    	  var sec = parseInt(time.substring(0,2), 16);
                    	  var mashineTime = yearRes+'年'+month+'月'+day+'日'+hour+'时'+minute+'分'+sec+'秒';
                    	  
                    	  var manufactureStatus = tempdata.substring(22,24);
                    	  var manufactureStatusTag;
                    	  if(manufactureStatus == '01'){
                    		  manufactureStatusTag = "刷脸";
                    	  } else if(manufactureStatus == '02'){
                    		  manufactureStatusTag = "刷身份证";
                    	  } else if(manufactureStatus == '03'){
                    		  manufactureStatusTag = "刷IC卡";
                    	  } else if(manufactureStatus == '04'){
                    		  manufactureStatusTag = "身份证+脸";
                    	  } else if(manufactureStatus == '05'){
                    		  manufactureStatusTag = "IC卡+脸";
                    	  } else if(manufactureStatus == '10'){
                    		  manufactureStatusTag = "无效用户";
                    	  } else{
                    		  manufactureStatusTag = "未知";
                    	  }
                    	  if (true) {
                    			if(manufactureStatus == '01'){
                    				$("#taskInfoTable2").append("<tr bgcolor=\"#CAE1FF\"><td>"+deviceid+
                                		   	   "</td><td>"+cardid+
                                            "</td><td>"+authorityTag+
                                            "</td><td>"+verifyTag+
                                               "</td><td>"+inRightTimeTag+
                                               "</td><td>"+doorLockStateTag+
                                               "</td><td>"+doorLockStateTag+
                                               "</td><td>"+mashineTime+
                                               "</td><td>"+manufactureStatusTag+"</td></tr>")
                    			} else if(manufactureStatus == '02'){
                    				$("#taskInfoTable2").append("<tr bgcolor=\"#C9C9C9\"><td>"+deviceid+
                             		   	   "</td><td>"+cardid+
                                         "</td><td>"+authorityTag+
                                         "</td><td>"+verifyTag+
                                            "</td><td>"+inRightTimeTag+
                                            "</td><td>"+doorLockStateTag+
                                            "</td><td>"+doorLockStateTag+
                                            "</td><td>"+mashineTime+
                                            "</td><td>"+manufactureStatusTag+"</td></tr>")
                    			} else{
                    				$("#taskInfoTable2").append("<tr><td>"+deviceid+
                             		   	   "</td><td>"+cardid+
                                         "</td><td>"+authorityTag+
                                         "</td><td>"+verifyTag+
                                            "</td><td>"+inRightTimeTag+
                                            "</td><td>"+doorLockStateTag+
                                            "</td><td>"+doorLockStateTag+
                                            "</td><td>"+mashineTime+
                                            "</td><td>"+manufactureStatusTag+"</td></tr>")
                    			}
                      		} else {
                    			$('#taskInfoTable2 tr').each(function(i){
                      	      		var deviceidinTable = $(this).children('td').eq(0).text();
                      	      		if(deviceidinTable == deviceid){
                      	      			$(this).children('td').eq(5)[0].innerHTML = currentTime;
                      	      			$(this).children('td').eq(6)[0].innerHTML = cardid;
                      	      			/* var tb = document.getElementById('taskInfoTable');
                      	      	 		var td = tb.rows[0].cells[0];
                      	      			td.innerHTML = '222'; */
                      	      		}
                      	   	  		
                      	    	})
                      		}
                  		$.ajax({ 
                			type        : "POST",
                			url         : "${base}nbiot/queryRegisterInfo.do",
                			data        :  {},
                			contentType : "application/x-www-form-urlencoded;charset=utf-8;",
                			dataType    : "json",
                			cache		: false,
                			success: function(info) {

                			}
                		});
                      }
            	   }
            	if(messageType == 'uploadPersonInfo'){
            		$('#registerPhoto').modal('show');
            		var name = messageNode.name;
            		var cardno = messageNode.cardno;
            		var deviceid = messageNode.deviceId;
            		var photoPath = "../nbiot/downloadFile.do?id=" + cardno;
            		$('#curPhoto').attr("src", photoPath);
            		$('#registerInfo').html("<span><strong>"+ name +"</strong></sapn></br>在终端：" + deviceid + "上注册");
            	}
            } else{
            	$("#responstext").append("【"+ currentTime +"】" + receivedMessage + "<br/>-----------------------------------------------------------<br/>");
                $("#eventtext").append("【"+ currentTime +"】" + receivedMessage + "<br/>-----------------------------------------------------------<br/>"); 
            }
        };
        //关闭事件
        websocket.onclose = function() {
            alert("Socket已关闭");
            document.getElementById('infoFont').innerHTML='Socket已断开，请重新刷新页面！';
        };
        //发生了错误事件
        websocket.onerror = function() {
            alert("发生了错误");
            document.getElementById('infoFont').innerHTML='Socket连接发生了错误！';
        }
	};
	
	function dateFtt(fmt,date)   
	{
	  var o = {   
	    "M+" : date.getMonth()+1,
	    "d+" : date.getDate(),
	    "h+" : date.getHours(),
	    "m+" : date.getMinutes(),
	    "s+" : date.getSeconds(),
	    "q+" : Math.floor((date.getMonth()+3)/3),
	    "S"  : date.getMilliseconds()
	  };   
	  if(/(y+)/.test(fmt))   
	    fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
	  for(var k in o)   
	    if(new RegExp("("+ k +")").test(fmt))   
	  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
	  return fmt;   
	}
	
	function specieSelChange(obj){
		//alert(obj);
		var id = $(obj).val();
		if(id == 0){
			$("#form00").show();
			$("#form01").hide();
			$("#form02").hide();
			$("#form03").hide();
			$("#form05").hide();
			$("#send0").show();
			$("#send1").hide();
			$("#send2").hide();
			$("#send3").hide();
			$("#send4").hide();
		} else if(id == 1){
			$("#form00").hide();
			$("#form01").show();
			$("#form02").hide();
			$("#form03").hide();
			$("#form05").hide();
			$("#send0").hide();
			$("#send1").show();
			$("#send2").hide();
			$("#send3").hide();
			$("#send4").hide();
		} else if(id == 2){
			$("#form00").hide();
			$("#form01").hide();
			$("#form02").show();
			$("#form03").hide();
			$("#form05").hide();
			$("#send0").hide();
			$("#send1").hide();
			$("#send2").show();
			$("#send3").hide();
			$("#send4").hide();
		} else if(id == 3){
			$("#form00").hide();
			$("#form01").hide();;
			$("#form02").hide();
			$("#form03").show();
			$("#form05").hide();
			$("#send0").hide();
			$("#send1").hide();
			$("#send2").hide();
			$("#send3").show();
			$("#send4").hide();
		} else if(id == 4){
			$("#form00").hide();
			$("#form01").hide();
			$("#form02").hide();
			$("#form03").hide();
			$("#form05").hide();
		} else if(id == 5){
			$("#form00").hide();
			$("#form01").hide();
			$("#form02").hide();
			$("#form03").hide();
			$("#form05").show();
			$("#send0").hide();
			$("#send1").hide();
			$("#send2").hide();
			$("#send3").hide();
			$("#send4").show();
		}
	}
	
    /*function clock() {
        
		$('#wxw').html(new Date());
	} */
	
	function selectRegister(selBox) {
		var data = selBox.value;
		$.ajax({ 
			type        : "POST",
			url         : "${base}nbiot/queryRegisterInfobyFaceid.do",
			data        :  {personid : data},
			contentType : "application/x-www-form-urlencoded;charset=utf-8;",
			cache		: false,
			success: function(data) {
				//alert("wangxiwei");
				$("#card1").val(data);
			}
		});
	}
	
/* 	$("#personId3").bind('change',function(){
		alert("wangxiwei");
    }) */
	
	$("form").ajaxForm(function(data){    
        //alert("下发成功！");
		document.getElementById('commandFont').innerHTML='命令发送中...请稍候';
		$("button").attr('disabled',true);
    });
    
	function edit(obj){
		$('#NBModal').modal('show');
		var deviceid = $(obj).next()[0].childNodes[3].children[1].innerText;
		//document.getElementsByName("deviceId").html(deviceid);
		$("input[name='deviceId']").val(deviceid);
	}
	
	function commandSubmit(obj){
		$("#form0"+obj).submit();
		document.getElementById('commandFont2').innerHTML='命令发送中...请稍候';
	}
	
	function viewer(obj){
		var deviceid = $(obj).parent().parent()[0].childNodes[3].children[1].innerText;;
		$('#taskInfoView').modal('show');
 	    $.ajax({
			url:'${base}nbiot/queryPersonInfobyDevice.do',
			type:'post',
			data:{  deviceid : deviceid },
			async:false,
			success:function(data){
				var whitestr = data.whiteStr;
				var managerstr = data.managerStr;
				$('#taskInfoView').modal('show');
				$("#authorlist").html("<span>白名单：" + whitestr +"</br>管理人员：" + managerstr + "</>");
			}
		});
	}
</script>
<script>
	$(document).ready(function(){
		$("#form01").hide();
		$("#form02").hide();
		$("#form03").hide();
		$("#form05").hide();
		$("#send0").show();
		$("#send1").hide();
		$("#send2").hide();
		$("#send3").hide();
		$("#send4").hide();
		//访问页面就启动websorket
		webSocketConnect();
		//定时刷新设备状态
		//var interval=self.setInterval("clock()",5000);
		$.ajax({ 
			type        : "POST",
			url         : "${base}nbiot/queryRegisterInfo.do",
			data        :  {},
			contentType : "application/x-www-form-urlencoded;charset=utf-8;",
			dataType    : "json",
			cache		: false,
			success: function(info) {
				var data = info;
				//$("#personId3").html("");
	            $.each(data, function(){
	                $("#personId0").append("<option value=\""+this.face_id+"\">"+this.name+"</option>");
	                $("#personId1").append("<option value=\""+this.face_id+"\">"+this.name+"</option>");
	                $("#personId2").append("<option value=\""+this.face_id+"\">"+this.name+"</option>");
	            });
			}
		});
	});	
</script>	
<script src="${ctx}/js/common.js"></script>
<script src="${ctx}/js/bootstrap-dialog.min.js"></script>
<script src="${ctx}/js/fileinput.min.js"></script>
<%@ include file="/WEB-INF/views/foot_n.jsp" %>