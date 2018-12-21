<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>添加位置信息</title>
</head>
<body>
<form id="addposition" name="add" action="" method="post">
	<table>
		<tr>
			<td>设备id：</td><td><input type="text" name="deviceid"></td>
		</tr>
		<tr>
			<td>地址：</td><td><input type="text" name="addr"></td>
		</tr>
		<tr>
			<td>房间号：</td><td><input type="text" name="roomnum"></td>
		</tr>
		<tr>
			<td>业主姓名：</td><td><input type="text" name="name"></td>
		</tr>
		<tr>
			<td>手机号：</td><td><input type="text" name="phone"></td>
		</tr>
		<tr>
			<td>管辖机构：</td><td><input type="text" name="jurisdiction"></td>
		</tr>
		<tr>
			<td>管辖机构联系方式：</td><td><input type="text" name="jurisdictioncon"></td>
		</tr>
		<tr>
			<td><input type="submit" name="新增"></td>
		</tr>
	</table>
</form>
</body>
</html>