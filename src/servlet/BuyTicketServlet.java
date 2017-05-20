package servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import impl.MovieOrderManager;

/**
 * Servlet implementation class BuyTicketServlet
 */
@WebServlet("/BuyTicketServlet")
public class BuyTicketServlet extends HttpServlet {
	private static String SUCCESS = "SUCCESS";
	private static String FAILURE = "FAILURE";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		boolean result;
		int userId = Integer.parseInt(request.getParameter("userId"));
		int movieId = Integer.parseInt(request.getParameter("movieId"));
		String[] rows = request.getParameter("rows").split(",");
		String[] columns = request.getParameter("columns").split(",");
		int[] choosed_rows = new int[rows.length];
		int[] choosed_columns = new int[columns.length];
		for (int i=0; i<rows.length; i++) {
			choosed_rows[i] = Integer.parseInt(rows[i]);
			choosed_columns[i] = Integer.parseInt(columns[i]);
		}
		MovieOrderManager moManager = new MovieOrderManager();
		Writer out = response.getWriter();
		if(moManager.buyTicket(userId, movieId, choosed_rows, choosed_columns))	//成功购买
			out.write(SUCCESS);
		else 
			out.write(FAILURE);
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
