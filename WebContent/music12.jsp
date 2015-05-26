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
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no" />
<title>胎教音乐</title>
<script src="./js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="./jquery.mobile-1.4.4/jquery.mobile-1.4.4.min.js"></script>
<link rel="stylesheet" href="./jquery.mobile-1.4.4/jquery.mobile-1.4.4.1.css">
<script src="./js/MusicBox.js" type="text/javascript"></script>
<script type="text/javascript">
	var mb = null;

	window.onload = function() {

		mb = new MusicBox();

		mb.init();

	}
</script>
<style type="text/css">
       img { width: 100%; }
</style>
</head>
<body>
	<img src="./images/music.png" />
	<div data-role="collapsible" data-collapsed="false">
		<h3 align="center">第1个月</h3>
		<ul id="ul_musicList0">
		</ul>
	</div>
	<div data-role="collapsible">
		<h3 align="center">第2个月</h3>
		<ul id="ul_musicList1">
		</ul>
	</div>
	<div data-role="collapsible">
		<h3 align="center">第3个月</h3>
		<ul id="ul_musicList2">
		</ul>
	</div>
	<div data-role="collapsible">
		<h3 align="center">第4个月</h3>
		<ul id="ul_musicList3">
		</ul>
	</div>
	<div data-role="collapsible">
		<h3 align="center">第5个月</h3>
		<ul id="ul_musicList4">
		</ul>
	</div>
	<div data-role="collapsible">
		<h3 align="center">第6个月</h3>
		<ul id="ul_musicList5">
		</ul>
	</div>
	<div data-role="collapsible">
		<h3 align="center">第7个月</h3>
		<ul id="ul_musicList6">
		</ul>
	</div>
	<div data-role="collapsible">
		<h3 align="center">第8个月</h3>
		<ul id="ul_musicList7">
		</ul>
	</div>
	<div data-role="collapsible">
		<h3 align="center">第9个月</h3>
		<ul id="ul_musicList8">
		</ul>
	</div>
	<div data-role="collapsible">
		<h3 align="center">第10个月</h3>
		<ul id="ul_musicList9">
		</ul>
	</div>
</body>
</html>