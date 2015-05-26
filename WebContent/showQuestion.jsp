<%@page import="org.xulingyun.util.HibernateUtil"%>
<%@ page language="java" import="java.util.*,java.net.URL,java.sql.*,org.xulingyun.service.dao.PicText,org.hibernate.Session"
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
<title>问题</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<style type="text/css">
.name {color:#ccc;border:solid 1px #999;padding:3px 0px 0px 5px;}
.btns { width:145px; height:40px; background:url("btn1.png") no-repeat right top; color:#000;border: 0px; }
</style>
</head>
<body>
<form method="post" action="questionServlet">
<table class="editTable">
<tr><td><input class="name" size="34" name="name" type="text" onfocus="if(this.value=='您的昵称') {this.value='';}this.style.color='#333';" onblur="if(this.value=='') {this.value='您的昵称';this.style.color='#ccc';}" value="您的昵称" maxlength="50"/></td></tr>
<tr><td><textarea id="texts" class="question" name="question" rows="21" cols="36"  onfocus="if(document.getElementById('texts').value=='您的问题') {document.getElementById('texts').value='';}document.getElementById('texts').style.color='#333';" onblur="if(document.getElementById('texts').value=='') {document.getElementById('texts').value='您的问题';document.getElementById('texts').style.color='#ccc';}">您的问题</textarea></td></tr>
<tr><td><div><input type="submit" class="btns" value=""></div></td></tr>
</table>
</form>
</body>
</html>