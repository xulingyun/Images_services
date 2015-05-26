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
<title>Hello World</title>
<link rel="stylesheet"
	href="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css">
<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<script
	src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>
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
</head>
<body>
	<div data-role="page" id="page0">
		<div data-role="header">
			<h1>我的主页1</h1>
		</div>

		<div data-role="content">
			<p>
			<h1>00会上，国家发改委、住建部和安徽省及云推进新型城镇化，促进“新四化”协同发展，取得新的突破。</h1>
			<a id="tiao0" href="#page1" data-transition="slide">滑动到页面二</a>
		</div>

		<!-- <div data-role="footer">
			<h1>页脚文本</h1>
		</div> -->
	</div>

	<div data-role="page" id="page1">
		<div data-role="header">
			<h1>我的主页2</h1>
		</div>

		<div data-role="content">
			<p>
			<h1>会上，国家发改委、住建部和安徽省及云南红河州、福建晋江市、山东桓台县马桥镇负责人汇报了推进新型城镇化的思路、做法和建议。李克强对各地的积极探索给予肯定。他说，我国经济保持中高速增长、迈向中高端水平，必须用好新型城镇化这个强大引擎。新型城镇化是一个综合载体，不仅可以破解城乡二元结构、促进农业现代化、提高农民生产和收入水平，而且有助于扩大消费、拉动投资、催生新兴产业，释放更大的内需潜力，顶住下行压力，为中国经济平稳增长和持续发展增动能。必须认真贯彻中央城镇化工作会议精神，按照科学发展的要求，遵循规律，用改革的办法、创新的精神推进新型城镇化，促进“新四化”协同发展，取得新的突破。</h1>
			</p>
			<a id="tiao1" href="#page2" data-transition="slide"
				data-direction="slide">滑动到页面一（反向）</a>
			<!-- <a href="#pageone" data-transition="slide" data-direction="reverse">滑动到页面一（反向）</a> -->
		</div>
	</div>


	<div data-role="page" id="page2">
		<div data-role="header">
			<h1>我的主页3</h1>
		</div>

		<div data-role="content">
			<p>
			<h1>111会上，国家发改委、住建部和安徽省及云南红河州、福建晋江市、山东桓台县马桥镇负责人汇报了推进新型城镇化的思路、做法和建议。李克强对各地的积极探索给予肯定。他说，我国经济保持中高速增长、迈向中高端水平，必须用好新型城镇化这个强大引擎。新型城镇化是一个综合载体，不仅可以破解城乡二元结构、促进农业现代化、提高农民生产和收入水平，而且有助于扩大消费、拉动投资、催生新兴产业，释放更大的内需潜力，顶住下行压力，为中国经济平稳增长和持续发展增动能。必须认真贯彻中央城镇化工作会议精神，按照科学发展的要求，遵循规律，用改革的办法、创新的精神推进新型城镇化，促进“新四化”协同发展，取得新的突破。</h1>
			</p>
			<a id="tiao2" href="#page3" data-transition="slide"
				data-direction="slide">滑动到页面一（反向）</a>
			<!-- <a href="#pageone" data-transition="slide" data-direction="reverse">滑动到页面一（反向）</a> -->
		</div>
	</div>


	<div data-role="page" id="page3">
		<div data-role="header">
			<h1>我的主页4</h1>
		</div>

		<div data-role="content">
			<p>
			<h1>22会上，国家发改委、住建部和安徽省及云南红河州、福建晋江市、山东桓台县马桥镇负责人汇报了推进新型城镇化的思路、做法和建议。李克强对各地的积极探索给予肯定。他说，我国经济保持中高速增长、迈向中高端水平，必须用好新型城镇化这个强大引擎。新型城镇化是一个综合载体，不仅可以破解城乡二元结构、促进农业现代化、提高农民生产和收入水平，而且有助于扩大消费、拉动投资、催生新兴产业，释放更大的内需潜力，顶住下行压力，为中国经济平稳增长和持续发展增动能。必须认真贯彻中央城镇化工作会议精神，按照科学发展的要求，遵循规律，用改革的办法、创新的精神推进新型城镇化，促进“新四化”协同发展，取得新的突破。</h1>
			</p>
			<a id="tiao3" href="#page2" data-transition="slide"
				data-direction="slide">滑动到页面一（反向）</a>
			<!-- <a href="#pageone" data-transition="slide" data-direction="reverse">滑动到页面一（反向）</a> -->
		</div>
	</div>
</body>
</html>