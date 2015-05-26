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
		<form name="addfrom" id="add_from" method="post"
			action="beforeParentServlet?method=edit" enctype="multipart/form-data">
			<table class="editTable">
				<tr>
					<td>第几周:</td>
					<td><input type="text" name="week" /><input type="text" name="id" /></td>
				</tr>
				<tr>
					<td>图片:</td>
					<td><input type="file" name="pic" /></td>
				</tr>
				<tr>
					<td>卷首语:</td>
					<td><textarea rows="4" cols="40" name="outline" ></textarea></td>
				</tr>
				<tr>
					<td>本周孕妈妈必读:</td>
					<td><textarea rows="4" cols="40" name="mamiRead" ></textarea></td>
				</tr>
				<tr>
					<td>准爸妈要知道:</td>
					<td><textarea rows="4" cols="40" name="parentKnow" ></textarea></td>
				</tr>
				<tr>
					<td>本周特别关注:</td>
					<td><textarea rows="4" cols="40" name="focus" ></textarea></td>
				</tr>
				<tr>
					<td>温馨食谱:</td>
					<td><textarea rows="4" cols="40" name="cookbook" ></textarea></td>
				</tr>
				<tr>
					<td>本周胎教:</td>
					<td><textarea rows="4" cols="40" name="taegyo" ></textarea></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>