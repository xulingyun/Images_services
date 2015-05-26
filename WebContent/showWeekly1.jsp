<%@ page language="java" import="java.util.*,java.net.URL,java.sql.*"
	pageEncoding="UTF-8"%>
<%@page
	import="org.xulingyun.service.dao.BeforeParent,org.hibernate.Session"%>
<%@page import="org.xulingyun.util.HibernateUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<html>
<head>
<title>Hello World</title>
<link rel="stylesheet"
	href="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css">
<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<script
	src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>

</head>
<body>



	<%
		int day = Integer.parseInt(request.getParameter("day"));
			int week = day / 7 > 1 ? day / 7 : 1;
			Session s = null;
			String sql = "from BeforeParent where week>=? and week<?";
			s = HibernateUtil.getSessionFactory().getCurrentSession();
			s.beginTransaction();
			@SuppressWarnings("unchecked")
			List<BeforeParent> list = (List<BeforeParent>) s.createQuery(sql).setInteger(0, week).setInteger(1, week + 3).list();
			s.getTransaction().commit();
			request.setAttribute("list", list);
			//for (int i = 0; i < list.size(); i++) {
	%>
	<c:forEach items="${list}" var="list_one">
		<c:if test="${list!=null}">
		<div id="page${list_one.getWeek()-1}">
			<div data-role="header">
				<h1>孕育周刊第${list_one.getWeek()}周</h1>
			</div>
			<div data-role="content">
				<div data-role="collapsible-set">
					<div>
						<h3>卷首语</h3>
						${list_one.getOutline()}
					</div>
					<div>
						<p>
					</div>
					<div data-role="collapsible" data-collapsed="false">
						<h3>本周孕妈妈必读</h3>
						${list_one.getMamiRead()}
					</div>
					<div data-role="collapsible">
						<h3>准爸准妈要知道</h3>
						${list_one.getParentKnow()}
					</div>
					<div data-role="collapsible">
						<h3>本周特别关注</h3>
						${list_one.getFocus()}
					</div>
					<div data-role="collapsible">
						<h3>温馨食谱</h3>
						${list_one.getCookbook()}
					</div>
					<div data-role="collapsible">
						<h3>本周胎教</h3>
						${list_one.getTaegyo()}
					</div>
				</div>
			</div>
			<a id="tiao${list_one.getWeek()-1}" href="#page${list_one.getWeek()}" data-transition="slide"></a>
		</div>
		</c:if>
	</c:forEach>
	<script type="text/javascript">
		var page = 0;
		$(document).on("scrollstart", function() {
		});

		$(document).on("scrollstop", function() {
		});

		$(document).on("swipeleft", function() {
			if (page == 1) {
				$("#tiao1").attr("href", "#page0");
				location.href = $("#tiao1").attr("href");
				page -= 1;
			} else if (page == 2) {
				$("#tiao2").attr("href", "#page1");
				location.href = $("#tiao2").attr("href");
				page -= 1;
			} else if (page == 3) {
				$("#tiao3").attr("href", "#page2");
				location.href = $("#tiao3").attr("href");
				page -= 1;
			}
		});

		$(document).on("swiperight", function() {
			if (page == 0) {
				$("#tiao0").attr("href", "#page1");
				location.href = $("#tiao0").attr("href");
				page += 1;
				alert($("#page0"));
				//$("#page0").style.display = "none";
			} else if (page == 1) {
				$("#tiao1").attr("href", "#page2");
				location.href = $("#tiao1").attr("href");
				page += 1;
			} else if (page == 2) {
				$("#tiao2").attr("href", "#page3");
				location.href = $("#tiao2").attr("href");
				page += 1;
			}
		});
	</script>
</body>
</html>