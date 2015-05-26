<%@ page language="java" import="java.util.*,java.net.URL,java.sql.*"
	pageEncoding="UTF-8"%>
<%@page
	import="org.xulingyun.service.dao.BeforeParent,org.hibernate.Session"%>
<%@page import="org.xulingyun.util.HibernateUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no" />
<title>孕育周期</title>
<script src="./js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="./jquery.mobile-1.4.4/jquery.mobile-1.4.4.min.js"></script>
<link rel="stylesheet" href="./jquery.mobile-1.4.4/jquery.mobile-1.4.4.1.css">
<style type="text/css">
       img { width: 100%; height:auto;}
</style>
</head>
<body>
	<%
			double day = Double.parseDouble(request.getParameter("day"));
			int week = (int)Math.ceil(day/7);
			int w = ((int)day)/7;
			int d = ((int)day)%7;
			if(week<1){
				week=1;
			}else if(week>41){
				week=40;
			}
			Session s = null;
			String sql = "from BeforeParent where week=?";
			s = HibernateUtil.getSessionFactory().getCurrentSession();
			s.beginTransaction();
			@SuppressWarnings("unchecked")
			List<BeforeParent> list = (List<BeforeParent>) s.createQuery(sql).setString(0, week+"").list();
			s.getTransaction().commit();
			request.setAttribute("list", list);
			//for (int i = 0; i < list.size(); i++) {
	%>
	<img src="./images/yun.jpg" />
	<div style="display: block;left: 20px;top: 50px;position: absolute;"><h2>孕<%=week %>周</h2></div>
	<div style="display: block;left: 20px;top: 110px;position: absolute;">您怀孕第<%=(int)day %>天（<%=w %>周+<%=d %>天）</div>
	<c:forEach items="${list}" var="list_one">
		<div data-role="content" style="top: 0px;">
			<div data-role="collapsible-set">
				<div>
					<h3><img src="./images/firstsay.png" /></h3>
					<p style="font-size: 15px;font-weight: 100;">${list_one.getOutline()}</p>
				</div>
				<div data-theme="b" data-role="collapsible" data-collapsed="false" class="ui-nosvg ui-nodisc-icon" data-collapsed-icon="read" data-expanded-icon="read">
					<h3 align="center">本周孕妈妈必读</h3>
					${list_one.getMamiRead()}
				</div>
				<div data-theme="b" data-role="collapsible" class="ui-nosvg ui-nodisc-icon" data-collapsed-icon="know" data-expanded-icon="know">
					<h1>准爸准妈要知道</h1>
					${list_one.getParentKnow()}
				</div>
				<div data-theme="b" data-role="collapsible" class="ui-nosvg ui-nodisc-icon" data-collapsed-icon="focus" data-expanded-icon="focus">
					<h1>本周特别关注</h1>
					${list_one.getFocus()}
				</div>
				<div data-theme="b" data-role="collapsible" class="ui-nosvg ui-nodisc-icon" data-collapsed-icon="eat" data-expanded-icon="eat">
					<h1>饮食要点</h1>
					${list_one.getBook()}
					<img src="/weixinImage/beforeparent/${list_one.getPic()}"/>
					${list_one.getCookbook()}
				</div>
				<div data-theme="b" data-role="collapsible" class="ui-nosvg ui-nodisc-icon" data-collapsed-icon="music" data-expanded-icon="music">
					<h1>本周胎教</h1>
					${list_one.getTaegyo()}
				</div>
			</div>
		</div>
	</c:forEach>
</body>
</html>