<%@ page language="java" import="java.util.*,java.net.URL,java.sql.*"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>修改资料</title>
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.5/themes/icon.css">
<script type="text/javascript" src="jquery-easyui-1.3.5/jquery.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
<form method="post" action="/test/MyData">  
	<table style="width: 100%;height: 60%;" border="5px" class="editTable">
	<tr>
	<td style="width: 20%;"><font size="10px" data-options="required:true" >手机号码：</font></td>
	<td><input type="text" name="telephone" style="width: 80%;height: 100%;"></td>
	</tr>
	<tr>
	<td style="width: 20%;"><font size="10px">QQ：</font></td>
	<td><input type="text" name="qq" style="width: 80%;height: 100%;"></td>
	</tr>
	<tr>
	<td style="width: 20%;"><font size="10px">地址：</font></td>
	<td><input type="text" name="address" style="width: 80%;height: 100%;"></td>
	</tr>
	<tr>
	<td><input type="submit" style="height: 100%;font-size: 300%;" value="提交修改"></td>
	</tr>
	</table> 
</form>
</body>
</html>