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
<title>UpdateResult</title>
</head>
<body>
	<%
		InTheaterMovieManager inTheaterMovieManager = new InTheaterMovieManager();
		if (inTheaterMovieManager.AddAllMovie()) {
	%>
		<p align=center margin="100">正在上映电影更新成功</>
	<%
		} else { 
	%>
		<p align=center>正在上映电影更新成功</>
	<%
		}
	%>
	
</body>
</html>