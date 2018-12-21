<%@page import="com.routon.plcloud.common.decorator.PageCheckboxDecorator"%>
<%@page import="com.routon.plcloud.common.decorator.PageLinkDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ include file="/WEB-INF/views/head_n.jsp" %>
<link rel="stylesheet" href="${ctx}/css/zTreeStyle.css">
	<p5><strong>服务端接收到的消息：</strong></p5><button type="button" onclick="openListner()">开启端口监听</button></br></br>
	<textarea rows="40" cols="70"></textarea>
<script>
	function openListner(){
		//alert("my name is wangxiwei");
		$.ajax({
			url:'${base}wifi/openListner.do',
			type:'post',
			data:{},
			async:false,
			success:function(data){
				if(data == "success"){
					alert("任务已通过");
				}
			}
		});
	}
</script>
<script src="${ctx}/js/jquery.ztree.all-3.5.min.js"></script>
<script src="${ctx}/js/jquery.ztree.exhide-3.5.min.js"></script>
<%@ include file="/WEB-INF/views/common/myModal.jsp" %>
<%@ include file="/WEB-INF/views/foot_n.jsp" %>