<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="impl.TicketManager" %>
<%@ page import="bean.TicketShow" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
	body{
		background:#00FF00;
		width="200px"
		height="20px"
	}

</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body >
	<p align="center">
		<h2 align="center">更新电影票价格</h2>
	</p>
	
	<%
		TicketManager ticketeManager = new TicketManager();
		List<TicketShow> list = ticketeManager.getTicketShow();
	%>
		<table width="600" height="100" align="center">
		<tr>
			<td width="50" height="5" align="center">电影Id</td>
			<td width="10" height="5" align="center">电影名</td>
			<td width="10" height="5" align="center">票价</td>
			<td width="10" height="5" align="center"></td>
		<tr>
	<%
		for (TicketShow orderShow:list) {
	%>
		<form name="modify_user" method="post" action="/MyMovieService/UpdateTicketPriceServlet">
		<tr>
			<td align="center"><input type="text" value=<%=orderShow.getMovieId() %> size="10" disabled="true"></td>
			<td align="center"><input type="text" name="movieTitle" value=<%=orderShow.getMovieTitle() %> size="20" disabled="true"></td>
			<td align="center"><input type="text" name="moviePrice" value=<%=orderShow.getMoviePrice() %> size="5" ></td>
			<td align="center"><input type="hidden" name="movieId" value=<%=orderShow.getMovieId() %> size="10"></td>
			<td align="center"><input type="submit" value="修改" ></td>
		</tr>
		</form>
		
	<%
		}
	%>	</table>
</body>
</html>