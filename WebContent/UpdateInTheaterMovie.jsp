<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="bean.InTheaterMovie" %>    
<%@page import="impl.InTheaterMovieManager" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
	p{padding:100px}
	#padding {
		padding:100px
	}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p align="center" >
		更新正在上映电影！
	</p>
	
	<form action="#" class="padding" align="center"">
		<input type="hidden" name="what" value="UpdateInTheaterMovie">
		<input type="submit" value="更新电影">
	</form>
</body>
</html>