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
       img { width: 100%; }
</style>
</head>
<body style="background-color: #f7f8f3;">
	<%
			double day = Double.parseDouble(request.getParameter("day"));
			String name = request.getParameter("name");
			if(name.equals("")||name==null){
				name = "";
			}
			int intday = (int)day;
			int year = (int)Math.floor(day/365);
			int m = ((int)day)%365/30;
			String str = "";
			if(year>=3){
				if(year==6){
					str = "5岁10-12个月";
				}else if(m>=0&&m<3){
					str = year+"岁1-3个月";
				}else if(m>=3&&m<6){
					str = year+"岁4-6个月";
				}else if(m>=6&&m<9){
					str = year+"岁7-9个月";
				}else if(m>=9&&m<12){
					str = year+"岁10-12个月";
				}
			}else{
				str = year+"岁"+(m+1)+"个月";
			}
			Session s = null;
			String sql = "from BeforeParent where week=?";
			s = HibernateUtil.getSessionFactory().getCurrentSession();
			s.beginTransaction();
			@SuppressWarnings("unchecked")
			List<BeforeParent> list = (List<BeforeParent>) s.createQuery(sql).setString(0, str).list();
			s.getTransaction().commit();
			request.setAttribute("list", list);
			//for (int i = 0; i < list.size(); i++) {
	%>
	<img src="./images/baby1_6.jpg" />
	<div style="display: block;left: 20px;top: 60px;position: absolute;"><h2><%=str %></h2></div>
	<c:forEach items="${list}" var="list_one">
		<div id="page">
			<div data-role="content">
				<div data-role="collapsible-set">
					<div>
						<h3><img src="./images/firstsay.png" /></h3>
						<p style="font-size: 15px;font-weight: 100;">${list_one.getOutline()}</p>
					</div>
					<div>
						<p>
					</div>
					<div data-role="collapsible" data-collapsed="false" data-theme="b" class="ui-nosvg ui-nodisc-icon" data-collapsed-icon="read1" data-expanded-icon="read1">
						<h3 align="center">本周妈妈必知</h3>
						${list_one.getMamiRead()}
					</div>
					<div data-role="collapsible" data-theme="b" class="ui-nosvg ui-nodisc-icon" data-collapsed-icon="know1" data-expanded-icon="know1">
						<h1>宝宝发育指标</h1>
						${list_one.getParentKnow()}
					</div>
					<div data-role="collapsible" data-theme="b" class="ui-nosvg ui-nodisc-icon" data-collapsed-icon="focus1" data-expanded-icon="focus1">
						<h1>育儿宝典</h1>
						${list_one.getFocus()}
					</div>
					<div data-role="collapsible" data-theme="b" class="ui-nosvg ui-nodisc-icon" data-collapsed-icon="eat1" data-expanded-icon="eat1">
						<h1>宝宝喂养要点</h1>
						${list_one.getBook()}
						<img src="/weixinImage/beforeparent/${list_one.getPic()}"/>
						${list_one.getCookbook()}
					</div>
					<div data-role="collapsible" data-theme="b" class="ui-nosvg ui-nodisc-icon" data-collapsed-icon="music1" data-expanded-icon="music1">
						<h1>亲子游戏</h1>
						${list_one.getTaegyo()}
					</div>
				</div>
			</div>
		</div>
	</c:forEach>
</body>
</html>