package servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Person;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet{

	Person person = new Person();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		person.setName(req.getParameter("name"));
		person.setSex(req.getParameter("sex"));
		person.setAccount(req.getParameter("account"));
		person.setPassword(req.getParameter("password"));
		person.setPhone(req.getParameter("phone"));
		
		System.out.println(person.getName());
		System.out.println(person.getSex());
		System.out.println(person.getAccount());
		System.out.println(person.getPassword());
		System.out.println(person.getPhone());
		
		resp.setContentType("text/html");
		Writer out = resp.getWriter();
		out.write("received");
		out.flush();
		out.close();
		
	}

	
}
