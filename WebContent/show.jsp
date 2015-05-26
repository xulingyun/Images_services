<%@page import="org.xulingyun.util.HibernateUtil"%>
<%@ page language="java" import="java.util.*,java.net.URL,java.sql.*,org.xulingyun.service.dao.PicText,org.hibernate.Session"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	String baseImagePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ "/weixinImage/";
	int id = Integer.parseInt(request.getParameter("id"));
	Session s = HibernateUtil.getSessionFactory().getCurrentSession();
	s.beginTransaction();
	PicText pt = (PicText)s.get(PicText.class, id);
	s.getTransaction().commit();
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Hello World</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
</head>
<body>
<p><b><font size="6.0em"><%=pt.getTitle() %></font></b></p>
<hr style="border:1 dashed #987cb9" width="100%" color=#987cb9 SIZE=1>
<p><i><font size="4.0em"><%=pt.getDate() %></font></i></p>
<img width="100%" src="<%=baseImagePath+pt.getCover() %>"/>
<p><font size="4.0em">
<%=pt.getContent() %>
</font>
</p>
</body>
</html>