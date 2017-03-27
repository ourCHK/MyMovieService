<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<H1 >添加电影</H1>
	<form action="UploadServlet"enctype="multipart/form-data" method="post">
		<ul>电影名称：<input type="text" name="name"></ul>
		<ul>主演:<input type="text" name="main_performer"></ul>
		<ul>介绍:<textarea name="introduce" placeholder="请在此添加电影介绍"></textarea></ul>
		<ul>添加图片：<input type="file" name="file1" id="file1"></ul>
		<ul><input type="submit" value="提交"></ul>
	</form>
</body>
</html>