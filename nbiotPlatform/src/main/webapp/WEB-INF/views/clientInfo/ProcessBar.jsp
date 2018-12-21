<%@page import="com.routon.plcloud.common.decorator.PageCheckboxDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ include file="/WEB-INF/views/head_n.jsp" %>
<style type="text/css">
.bar{
    display: inline-block;
    background: #90bf46;
    color: white;
    font-weight: bold;
    padding: 0 5px;
    text-align: right;
    border-radius: 5px;
    border-right: 1px solid #999;
}
</style>
<script src="${ctx}/js/jquery-easyui/jquery.easyui.min.js"></script>
	<div style="margin:100px 0;"></div>
    <div id="p" class="easyui-progressbar bar" style="width: 0x;"></div>
<script>
var timerId;
$(function(){
//每隔0.5秒自动调用方法，实现进度条的实时更新
//timerId=window.setInterval(getForm,500);

var percentage = 0;
var interval = setInterval(function(){
	if(percentage < 100){
		percentage+=10;
		$('#p').progressbar('setValue',percentage);
		$('#p').css('width', percentage);
	}
    if(data >= 100){
        window.clearInterval(interval);
       }
	},500);
});
/* function getForm(){
  //使用JQuery从后台获取JSON格式的数据
  $.ajax({
   type:"post",//请求方式
   url:"${base}terminal/showbar.do",//发送请求地址
   timeout:30000,//超时时间：30秒
   dataType:"json",//设置返回数据的格式
   //请求成功后的回调函数 data为json格式
   success:function(data){
    if(data >= 100){
     window.clearInterval(timerId);
    }
    $('#p').progressbar('setValue',data);
   },
   //请求出错的处理
   error:function(){
    window.clearInterval(timerId);
    alert("请求出错");
   }
  });
} */
</script>
<%@ include file="/WEB-INF/views/foot_n.jsp" %>