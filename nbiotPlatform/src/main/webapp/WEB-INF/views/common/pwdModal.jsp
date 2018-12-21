<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<script src="${ctx}/js/common.js"></script>
<script src="${ctx}/js/jquery.form.js"></script>
<script type="text/javascript">
<!--


$('#changepwdmodal').on('hide.bs.modal', function (e) {
	$("#oldPwd").val("");
	$("#newPwd").val("");
	$("#newPwdConfirm").val("");
});

function openChangePwdModal(){
	$("#oldPwd").val("");
	$("#newPwd").val("");
	$("#newPwdConfirm").val("");
	$("#changepwdmodal").modal('show');
	
}


function pwdformSave(formId, saveUrl, jumpUrl){
	
	this.form = $(formId);
	this.saveUrl = saveUrl;
	this.jumpUrl = jumpUrl;
	
	this.showRequest = function(formData, jqForm, options) { 	   
	    return true; 
	};
	
	this.showResponse = function(responseText, statusText, xhr, $form)  { 
		var info = responseText;
		console.log(info);
		if (info.code == 1) {
			console.log('info.code == 1');	
			alert('密码修改成功,请重新用新密码登录!');
			document.location.href = jumpUrl;
			
		}
		else {
			alert(info.msg);
			$("#savebtn").attr("disabled", false);
			
		}
		
		//$('#myModal').modal('show');
	};
	
	this.form.ajaxForm({ 
			target:        	'#output1',
		    beforeSubmit:  	this.showRequest,
		    success:       	this.showResponse,
		 
			url:       		this.saveUrl,
		    type:      		'post',
		    dataType:  		'json',
		    contentType: 	"application/x-www-form-urlencoded;charset=utf-8;" 		
	});	
	
	this.submit = function(){
		this.form.submit();	
	};
	
};

function pwdsave(form, saveUrl, jumpUrl) {
	var fs = new pwdformSave(form, saveUrl, jumpUrl);
	$("#savebtn").attr("disabled", true);
	fs.submit();
}

//-->

function CheckIntensity(pwd) {
    var Mcolor, Wcolor, Scolor, Color_Html;    
    var m = 0;   
    //匹配数字
    if (/\d+/.test(pwd)) {
      m++;
    };
    //匹配字母
    if (/[A-Za-z]+/.test(pwd)) {     
      m++;
    };
    //匹配除数字字母外的特殊符号
    if (/[^0-9a-zA-Z]+/.test(pwd)) {      
      m++;
    };
     
    if (pwd.length <= 6) { m = 1; }
    if (pwd.length <= 0) { m = 0; }    
    switch (m) {
      case 1:
        Wcolor = "pwd pwd_Weak_c";
        Mcolor = "pwd pwd_c";
        Scolor = "pwd pwd_c pwd_c_r";
        Color_Html = "弱";
        break;
      case 2:
        Wcolor = "pwd pwd_Medium_c";
        Mcolor = "pwd pwd_Medium_c";
        Scolor = "pwd pwd_c pwd_c_r";
        Color_Html = "中";
        break;
      case 3:
        Wcolor = "pwd pwd_Strong_c";
        Mcolor = "pwd pwd_Strong_c";
        Scolor = "pwd pwd_Strong_c pwd_Strong_c_r";
        Color_Html = "强";
        break;
      default:
        Wcolor = "pwd pwd_c";
        Mcolor = "pwd pwd_c pwd_f";
        Scolor = "pwd pwd_c pwd_c_r";
        Color_Html = "无";
        break;
    }
    document.getElementById('pwd_Weak').className = Wcolor;
    document.getElementById('pwd_Medium').className = Mcolor;
    document.getElementById('pwd_Strong').className = Scolor;
    document.getElementById('pwd_Medium').innerHTML = Color_Html;
  }  

function newpwdsave(){
	var newPwd = $("#newPwd").val();
	var m = 0;   
    //匹配数字
    if (/\d+/.test(newPwd)) {
      m++;
    };
    //匹配字母
    if (/[A-Za-z]+/.test(newPwd)) {     
      m++;
    };
    //匹配除数字字母外的特殊符号
    if (/[^0-9a-zA-Z]+/.test(newPwd)) {      
      m++;
    };
    var len = newPwd.length;
    if (len <= 6) { m = 1; }
    if (len <= 0) { m = 0; }
    
    var userId = $("#userId").val();
	var loginName = $("#loginName").val();
	var oldPwd = $("#oldPwd").val();
	var newPwd = $("#newPwd").val();
	var newPwdConfirm = $("#newPwdConfirm").val();
	var data = {};
	data.userId = userId;
	data.loginName = loginName;
	data.oldPwd = oldPwd;
	data.newPwd = newPwd;
	data.newPwdConfirm = newPwdConfirm; 
	
    if(m>1){
    	var rolename = $("#rolename").val();
    	if(m<3){
    		if(rolename == '超级管理员'){
    			alert("管理员的密码强度必须设置为强");
        		document.getElementById('newPwd').value="";
            	document.getElementById('newPwdConfirm').value="";
            	var newPwdInput = document.getElementById("newPwd");
            	newPwdInput.focus();
            	return false;
    		}else{
    			//save('#pwdForm', g_ctx + '/changepwd.do', g_ctx + '/logout.do');
    			ajax(data);
    		}
    	}else{
    		//save('#pwdForm', g_ctx + '/changepwd.do', g_ctx + '/logout.do');
    		ajax(data);
    	}
    }else{
    	alert("您的密码强度较弱，请至少输入强度为中密码");
    	document.getElementById('newPwd').value="";
    	document.getElementById('newPwdConfirm').value="";
    	var newPwdInput = document.getElementById("newPwd");
    	newPwdInput.focus();
    	return false;
    }
}

function ajax(data){
	$.ajax({ 
		type        : "POST", 
		url         : "${ctx}/changepwd.do",
		data        : data,
		contentType : "application/x-www-form-urlencoded;charset=utf-8;",
		dataType    : "json",
		cache		: false,
		success: function(info) {
			if (info.code == 1) {
				alert("密码修改成功，请重新登录");
				gotourl('${base}logout.do');						
			}else{
				alert(info.msg);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {    
			alert(XMLHttpRequest.status + textStatus);    
		} 
		
	});
} 
</script>

<style type="text/css">
.pwd{width:50px;height:20px;line-height:14px;padding-top:2px;} 
.pwd_f{color:#BBBBBB;} 
.pwd_c{background-color:#F3F3F3;border-top:1px solid #D0D0D0;border-bottom:1px solid #D0D0D0;border-left:1px solid #D0D0D0;} 
.pwd_Weak_c{background-color:#FF4545;border-top:1px solid #BB2B2B;border-bottom:1px solid #BB2B2B;border-left:1px solid #BB2B2B;} 
.pwd_Medium_c{background-color:#FFD35E;border-top:1px solid #E9AE10;border-bottom:1px solid #E9AE10;border-left:1px solid #E9AE10;} 
.pwd_Strong_c{background-color:#3ABB1C;border-top:1px solid #267A12;border-bottom:1px solid #267A12;border-left:1px solid #267A12;} 
.pwd_c_r{border-right:1px solid #D0D0D0;} 
.pwd_Weak_c_r{border-right:1px solid #BB2B2B;} 
.pwd_Medium_c_r{border-right:1px solid #E9AE10;} 
.pwd_Strong_c_r{border-right:1px solid #267A12;}


</style>

<div class="modal fade" id="changepwdmodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog"  >
    <div class="modal-content" >
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">修改密码</h4>
      </div>
      <div class="modal-body" >	
      		<form:form id="pwdForm" name="pwdForm" class="form-horizontal" role="form" method="post" enctype="multipart/form-data" >
				<input id="userId" name="userId" type="hidden" value="${userProfile.currentUserId}" >
				<input id="loginName" name="loginName" type="hidden" value="${userProfile.currentUserLoginName}" >
				<input id="rolename" name="rolename" type="hidden" value="${rolename}" >
			  <div class="form-group">
			    <label for="title" class="col-sm-3 control-label">旧密码</label>
			    <div class="col-sm-4">
			      <input type="password" class="form-control" id="oldPwd" name="oldPwd" placeholder="旧密码" value="">
			    </div>
			  </div>
			  </br>
			 <div class="form-group">
			    <label for="title" class="col-sm-3 control-label">新密码</label>
			    <div class="col-sm-4" style="float:left">
			      <!-- <input type="password" class="form-control" id="newPwd" name="newPwd" placeholder="新密码" value=""> -->
			      <input type="password" class="form-control" id="newPwd" name="newPwd" placeholder="新密码" value="" onKeyUp="CheckIntensity(this.value)">
			      <table border="0" cellpadding="0" cellspacing="0">
					<tr align="center">
				  	 <td id="pwd_Weak" class="pwd pwd_c"> </td>
				  	 <td id="pwd_Medium" class="pwd pwd_c pwd_f"></td>
				  	 <td id="pwd_Strong" class="pwd pwd_c pwd_c_r"> </td>
				    </tr>
				  </table>            
			       <!-- 密码包括数字、字母、特殊符号三类。</br>弱：密码串包含其中一种或长度小于等于6。</br>中：密码串包含其中两种且长度大于6。</br>强：密码串包含其中三种且长度大于6。 -->
			    </div>
			    <div style="float:right"> 密码包括数字、字母、特殊符号三类。</br>弱：密码含其中一种或长度小于等于6。</br>中：密码含其中两种且长度大于6。</br>强：密码含其中三种且长度大于6。</div>
			  </div>
			 
			  <div class="form-group">
			    <label for="title" class="col-sm-3 control-label">新密码确认</label>
			    <div class="col-sm-4">
			      <input type="password" class="form-control" id="newPwdConfirm" name="newPwdConfirm" placeholder="新密码确认" value="">
			    </div>
			  </div>
			</form:form>
      </div>
      <div class="modal-footer">
      	<!-- <button id="savebtn" type="button" class="btn btn-primary" onclick="pwdsave('#pwdForm', g_ctx + '/changepwd.do', g_ctx + '/logout.do')">保存</button> -->
      	<button id="savebtn" type="button" class="btn btn-primary" onclick="newpwdsave()">保存</button>
      </div>
    </div>
  </div>
</div> 