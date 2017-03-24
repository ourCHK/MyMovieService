package servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Person;
import impl.UserManager;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet{

	Person person = new Person();
	private static String SUCCESS = "SUCCESS";
	private static String FAILURE = "FAILURE";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = req.getParameter("name");
		String sex = req.getParameter("sex");
		String account = req.getParameter("account");
		String password = req.getParameter("password");
		String phone = req.getParameter("phone");
		
		UserManager userManager = new UserManager();
		System.out.println(name+" "+sex+ " "+ account + " "+ password + " "+ phone);
		resp.setContentType("text/html");
		Writer out = resp.getWriter();
		if (userManager.registerUser(name, sex, account, password, phone))
			out.write(SUCCESS);
		else 
			out.write(FAILURE);
		out.flush();
		out.close();
		
	}

	
}
