<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="impl.MovieOrderManager" %>
<%@ page import="bean.OrderShow" %>
<jsp:useBean id="orderManager" class="impl.MovieOrderManager" scope="page"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p align="center">
		<h2 align="center">订单查看</h2>
	</p>
	
	<%
		List<OrderShow> list = orderManager.getAllOrderShow();
	%>
		<table width="600" align="center">
		<tr>
			<td width="10" height="5" align="center">用户名</td>
			<td width="50" height="5" align="center">电影名</td>
			<td width="50" height="5" align="center">座位排号</td>
			<td width="50" height="5" align="center">座位列号</td>
			<td width="10" height="5" align="center">购票时间</td>
		<tr>
	<%
		for (OrderShow orderShow:list) {
			
	%>
		<tr>
			<td align="center"><%=orderShow.getUserName() %></td>
			<td align="center"><%=orderShow.getMovieName() %></td>
			<td align="center"><%=orderShow.getRows() %></td>
			<td align="center"><%=orderShow.getColumns() %></td>
			<td align="center"><%=orderShow.getCreate_date() %></td>
		<tr>
		
	<%
		}
	%>	</table>
</body>
</html>