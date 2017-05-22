<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="impl.UserManager" %>
<%@ page import="bean.User" %>
<jsp:useBean id="userManager" class="impl.UserManager" scope="page"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p align="center">
		<h2 align="center">用户管理</h2>
	</p>
	
	
	<%
		List<User> list = userManager.getAllUser();
	%>
		<table width="600" align="center">
		<tr>
			<td width="8" height="5" align="center">用户Id</td>
			<td width="10" height="5" align="center">用户名</td>
			<td width="50" height="5" align="center">性别</td>
			<td width="50" height="5" align="center">账号</td>
			<td width="10" height="5" align="center">密码</td>
			<td width="10" height="5" align="center">手机号</td>
		<tr>
	<%
		for (User user:list) {
			
	%>
		<form name="modify_user" method="post" action="/MyMovieService/UpdateUserServlet">
		<tr>
			<td align="center"><input type="text" value=<%=user.getId() %> size="8" disabled="true"></td>
			<td align="center"><input type="text" name="name" value=<%=user.getName() %> size="8"></td>
			<td align="center"><input type="text" name="sex" value=<%=user.getSex() %> size="8" disabled="true"></td>
			<td align="center"><input type="text" name="account" value=<%=user.getAccount() %> size="10" disabled="true"></td>
			<td align="center"><input type="text" name="password" value=<%=user.getPassword() %> size=12></td>
			<td align="center"><input type="text" name="phone" value=<%=user.getPhone() %> size="11"></td>
			<td align="center"><input type="submit" value="修改" ></td>
		<tr>
		</form>
		
	<%
		}
	%>	</table>
</body>
</html>