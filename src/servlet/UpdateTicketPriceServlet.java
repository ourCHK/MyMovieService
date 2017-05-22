package servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import impl.TicketManager;

/**
 * Servlet implementation class UpdateUserServlet
 */
@WebServlet("/UpdateTicketPriceServlet")
public class UpdateTicketPriceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String SUCCESS = "SUCCESS";
	private static String FAILURE = "FAILURE";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateTicketPriceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println(request.getParameter("movieId")+"  "+request.getParameter("moviePrice"));
		int movieId = Integer.parseInt(request.getParameter("movieId"));
		Float price = Float.parseFloat(request.getParameter("moviePrice"));
		TicketManager ticketManager = new TicketManager();
		response.setContentType("text/html");
		Writer out = response.getWriter();
		if(ticketManager.updatePrice(movieId, price)) //更新成功
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
