package servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import impl.MovieDetailManager;

/**
 * Servlet implementation class GetMovieDetail
 */
@WebServlet("/GetMovieDetailServlet")
public class GetMovieDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MovieDetailManager movieDetailManager = new MovieDetailManager();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMovieDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int movieId = Integer.parseInt(request.getParameter("movieId"));
		String result = movieDetailManager.getMovieDetailJson(movieId);
		System.out.println(result);
		response.setContentType("text/html");
//		response.setCharacterEncoding("utf8");
		Writer out = response.getWriter();
		out.write(result);
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
