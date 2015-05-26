<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<form name="addfrom" id="edit_from" method="post" action="imageTextServlet"
			 enctype="multipart/form-data" >
			<table class="editTable">
				<tr>
					<td for="name">种类:</td>
					<td><select id="cc" class="easyui-combobox" name="kind"
						style="width: 200px;">
							<option value="10">时尚辣妈</option>
							<option value="11">孕育周刊</option>
							<option value="1">育儿知识</option>
							<option value="12">亲子互动</option>
							<!-- <option value="2">每天一问</option>
							<option value="3">学员分享</option>
							<option value="4">最萌宝贝</option>
							<option value="5">会员赢iPad</option>
							<option value="6">微信活动</option>
							<option value="7">线下活动介绍</option>
							<option value="8">使用指南</option>
							<option value="9">产品介绍</option> -->
					</select></td>
				</tr>
				<tr>
					<td for="name">标题:</td>
					<td><input class="easyui-validatebox" type="text" name="title"
						data-options="required:true" /></td>
				</tr>
				<!-- <tr>
					<td>作者:</td>
					<td><input class="easyui-validatebox" type="text"
						name="author" value="智慧妈咪"/></td>
				</tr>-->
				<tr>
					<td>封面:</td>
					<td><input type="file" name="cover" /></td>
				</tr>
				<tr>
					<td>状态:</td>
					<td>
					<input type="radio" name="status" value="0" checked/>开启
					<input type="radio" name="status" value="1" />关闭
					</td>
				</tr>
				<tr>
					<td>摘要:</td>
					<td><input type="text" name="digest" /></td>
				</tr>
				<!-- <tr>
					<td>内容:</td>
					<td><textarea name="content" name="content" cols="35" rows="20"></textarea></td>
				</tr> -->
				<tr>
					<td><input type="hidden" name="date" /></td>
				</tr>
				<tr>
					<td>链接:</td>
					<td><input type="text" name="url" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>