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
			var beforeYear;
			var beforeMonth;
			if(currMonth<11){
				beforeYear = currYear-1;
				beforeMonth = currMonth+2;
			}else{
				beforeYear = currYear;
				beforeMonth = currMonth-10;
			}
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
		        startYear:currYear - 1, //开始年份
		        endYear:currYear + 1, //结束年份
		        dateFormat: 'yyyy-mm-dd',
		        minDate: new Date(beforeYear, beforeMonth, currDay),
		        maxDate: new Date(currYear, currMonth, currDay),
			};

			$("#appDate").val('').scroller('destroy').scroller($.extend(opt['date'], opt['defaults']));
		  	var optDateTime = $.extend(opt['datetime'], opt['defaults']);
		  	var optTime = $.extend(opt['time'], opt['defaults']);
		    $("#appDateTime").mobiscroll(optDateTime).datetime(optDateTime);
		    $("#appTime").mobiscroll(optTime).time(optTime);
			
			//下面注释部分是上面的参数可以替换改变它的样式
			//希望一起研究插件的朋友加我个人QQ也可以，本人也建个群 291464597 欢迎进群交流。哈哈。这个不能算广告。
			// 直接写参数方法
			//$("#scroller").mobiscroll(opt).date(); 
			// Shorthand for: $("#scroller").mobiscroll({ preset: 'date' });
			//具体参数定义如下
		    //{
		    //preset: 'date', //日期类型--datatime --time,
		    //theme: 'ios', //皮肤其他参数【android-ics light】【android-ics】【ios】【jqm】【sense-ui】【sense-ui】【sense-ui】
										//【wp light】【wp】
		    //mode: "scroller",//操作方式【scroller】【clickpick】【mixed】
		    //display: 'bubble', //显示方【modal】【inline】【bubble】【top】【bottom】
		    //dateFormat: 'yyyy-mm-dd', // 日期格式
		    //setText: '确定', //确认按钮名称
		    //cancelText: '清空',//取消按钮名籍我
		    //dateOrder: 'yymmdd', //面板中日期排列格
		    //dayText: '日', 
		    //monthText: '月',
		    //yearText: '年', //面板中年月日文字
		    //startYear: (new Date()).getFullYear(), //开始年份
		    //endYear: (new Date()).getFullYear() + 9, //结束年份
		    //showNow: true,
		    //nowText: "明天",  //
		    //showOnFocus: false,
		    //height: 45,
		    //width: 90,
		    //rows: 3}

        });
    </script>

</head>
<body>
	<img src="./images/nobaby.jpg" />
	<p style="font-size: 16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请选择您的月经末次日期</p>
	<form method="post" action="weekServlet">
		<table>
		<tr>
			<td style="font-size: 16px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;末次日期：</td>
			<td><input type="text" name="appDate" id="appDate" style="width: 160px;"/></td>
		</tr>
		<tr><td></td><td align="left"><input type="submit" data-role="none" class="submit1" value="" style="width: 160px;height: 40px;background-image: url('./images/submit.jpg');border: 0px;"></td></tr>
		</table>
	</form>
</body>
</html>