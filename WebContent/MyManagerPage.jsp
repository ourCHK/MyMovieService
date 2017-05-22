<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
	aside{float:left;background: #8DEEEE}
	div{background:#00FF00;height="300px";width="200px"}
	#content
	{
		height:500px;
		width:900px;
		margin-left:200px;
	}
</style>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台管理页面</title>
</head>
<body>
	<h1 align="center">后台管理页</h1>
	<aside>
	<h2>功能选择</h2>
	<ul>
		<li><a href="MyManagerPage.jsp?what=InTheaterMovie">更新今日上映电影</a></li>
		<li><a href="MyManagerPage.jsp?what=ComingSoonMovie">更新即将上映电影</a></li>
		<li><a href="MyManagerPage.jsp?what=UpdateTicketPrice">更新电影票价格</a></li>
		<li><a href="MyManagerPage.jsp?what=UserManager">用户管理</a></li>
		<li><a href="MyManagerPage.jsp?what=OrderManager">订单查看</a></li>
	</ul>
	</aside>
	<div id ="content">
		<%
			String what = request.getParameter("what");
			if (what != null && !what.isEmpty()) {
				if (what.equals("InTheaterMovie")) {
			%>
				<jsp:include page="UpdateInTheaterMovie.jsp">
					<jsp:param value='' name=""/>
					<jsp:param value='' name=""/>
				</jsp:include>
			<%
				} else if (what.equals("ComingSoonMovie")) {
			%>
				<jsp:include page="UpdateComingSoonMovie.jsp">
					<jsp:param value='' name=""/>
					<jsp:param value='' name=""/>
				</jsp:include>
			<%		
				} else if (what.equals("OrderManager")) {
			%>
				<jsp:include page="OrderManager.jsp">
					<jsp:param value='' name=""/>
					<jsp:param value='' name=""/>
				</jsp:include>
			<%		
				} else if (what.equals("UserManager")) {
			%>
				<jsp:include page="UserManager.jsp">
					<jsp:param value='' name=""/>
					<jsp:param value='' name=""/>
				</jsp:include>
			<%
				} else if (what.equals("UpdateInTheaterMovie")){
			%>
					<jsp:include page="UpdateInTheaterMovieResult.jsp">
						<jsp:param value='' name=""/>
						<jsp:param value='' name=""/>
					</jsp:include>
			<%	
				} else if (what.equals("UpdateComingSoonMovie")){
			%>
					<jsp:include page="UpdateComingSoonMovieResult.jsp">
						<jsp:param value='' name=""/>
						<jsp:param value='' name=""/>
					</jsp:include>
			<%
				} else if (what.equals("UpdateTicketPrice")){
					
			%>
					<jsp:include page="UpdateTicketPrice.jsp">
						<jsp:param value='' name=""/>
						<jsp:param value='' name=""/>
					</jsp:include>
			<%		
				}
			}
		%>
	</div>
</body>
</html>