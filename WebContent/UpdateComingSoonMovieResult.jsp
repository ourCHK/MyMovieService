<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="bean.InTheaterMovie" %>    
<%@page import="impl.ComingSoonMovieManager" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
	p{padding:200px}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UpdateSuccess</title>
</head>
<body>
	<%
		ComingSoonMovieManager comingSoonMovieManager = new ComingSoonMovieManager();
		if (comingSoonMovieManager.AddAllMovie()) {
	%>
		<p align=center margin="100">即将上映电影更新成功</>
	<%
		} else { 
	%>
		<p align=center>即将上映电影更新成功</>
	<%
		}
	%>
	
</body>
</html>