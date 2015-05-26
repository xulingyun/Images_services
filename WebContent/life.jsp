<%@ page language="java" import="java.util.*,java.net.URL,java.sql.*"
	pageEncoding="UTF-8"%>
<%@page
	import="org.xulingyun.service.dao.BeforeParent,org.hibernate.Session"%>
<%@page import="org.xulingyun.util.HibernateUtil"%>
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
<title>选择时间</title>
<script src="./js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="./js/mobiscroll.core-2.5.2.js" type="text/javascript"></script>
<script src="./js/mobiscroll.core-2.5.2-zh.js" type="text/javascript"></script>
<link href="./js/mobiscroll.core-2.5.2.css" rel="stylesheet" type="text/css" />
<script src="./js/mobiscroll.datetime-2.5.1.js" type="text/javascript"></script>
<script src="./js/mobiscroll.datetime-2.5.1-zh.js" type="text/javascript"></script>

<style type="text/css">
        body {
            padding: 0;
            margin: 0;
            font-family: arial, verdana, sans-serif;
            font-size: 12px;
            background: #ddd;
        }
        input, select {
            width: 100%;
            padding: 5px;
            margin: 5px 0;
            border: 1px solid #aaa;
            box-sizing: border-box;
            border-radius: 5px;
            -moz-box-sizing: border-box;
            -webkit-box-sizing: border-box;
            -webkit-border-radius: 5px;
        }
        .header {
            border: 1px solid #333;
            background: #111;
            color: white;
            font-weight: bold;
            text-shadow: 0 -1px 1px black;
            background-image: linear-gradient(#3C3C3C,#111);
            background-image: -webkit-gradient(linear,left top,left bottom,from(#3C3C3C),to(#111));
            background-image: -moz-linear-gradient(#3C3C3C,#111);
        }
        .header h1 {
            text-align: center;
            font-size: 16px;
            margin: .6em 0;
            padding: 0;
            text-overflow: ellipsis;
            overflow: hidden;
            white-space: nowrap;
        }
        .content {
            padding: 15px;
            background: #fff;
        }
       img { width: 100%; }
    </style>

<script type="text/javascript">
        $(function () {
			var currYear = (new Date()).getFullYear();	
			var currMonth = (new Date()).getMonth();
			var currDay = (new Date()).getDate();
			var opt={};
			opt.date = {preset : 'date'};
			//opt.datetime = { preset : 'datetime', minDate: new Date(2012,3,10,9,22), maxDate: new Date(2014,7,30,15,44), stepMinute: 5  };
			opt.datetime = {preset : 'datetime'};
			opt.time = {preset : 'time'};
			opt.defaults = {
				theme: 'android-ics light', //皮肤样式
		        display: 'modal', //显示方式 
		        mode: 'scroller', //日期选择模式
				lang:'zh',
		        startYear:currYear - 5, //开始年份
		        endYear:currYear, //结束年份
		        dateFormat: 'yyyy-mm-dd',
		        minDate: new Date(currYear-6, currMonth, currDay),
		        maxDate: new Date(currYear, currMonth, currDay),
			};

			$("#appDate").val('').scroller('destroy').scroller($.extend(opt['date'], opt['defaults']));
		  	var optDateTime = $.extend(opt['datetime'], opt['defaults']);
		  	var optTime = $.extend(opt['time'], opt['defaults']);
		    $("#appDateTime").mobiscroll(optDateTime).datetime(optDateTime);
		    $("#appTime").mobiscroll(optTime).time(optTime);
        });
    </script>

</head>
<body>
	<img src="./images/baby.jpg"/>
	<p style="font-size: 16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请选择您宝宝的生日</p>
	<form method="post" action="weekServlet">
		<table>
		<tr>
			<td style="font-size: 18px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;宝宝生日：</td>
			<td><input type="text" name="appDate" id="appDate" style="width: 160px;"/></td>
		</tr>
		<tr>
			<td style="font-size: 18px;"></td>
			<td><input type="hidden" name="type" value="1"></td>
		</tr>
		<tr>
			<td></td>
			<td align="left"><input type="submit" data-role="none" class="submit1" value="" style="width: 160px;height: 40px;background-image: url('./images/submit.jpg');border: 0px;"></td>
		</tr>
		</table>
	</form>
</body>
</html>