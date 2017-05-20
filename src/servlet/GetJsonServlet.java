package servlet;

import java.io.IOException;
import java.io.Writer;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import impl.ComingSoonMovieManager;
import impl.InTheaterMovieManager;
import impl.MovieManager;
import impl.MovieOrderManager;
import impl.TicketManager;

/**
 * Servlet implementation class GetJsonServlet
 */
@WebServlet("/GetJsonServlet")
public class GetJsonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetJsonServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String type  = request.getParameter("type");
		System.out.println(type);
		int from = -1;
		int to = -1;
		int movieId = -1;
		Date create_date = null;	
		
		if (type.equals("ChoosedSeats")) {	//如果是请求座位的话
			movieId = Integer.parseInt(request.getParameter("movieId"));
			create_date = Date.valueOf(request.getParameter("create_date"));
		} else if (type.equals("MoviePrice")) {	//如果是请求电影票价格的话
			movieId = Integer.parseInt(request.getParameter("movieId"));
		} else {	//其他查询情况
			from  = Integer.parseInt(request.getParameter("from"));
			to = Integer.parseInt(request.getParameter("to"));
		}
		
		String result = "";
//		
//		PicListInfo picListInfo = new PicListInfo();
//		List<PicInfo> reqList = new ArrayList<PicInfo>();
//		for(int i=from; i<picListInfo.getPicList().size() && i<to; i++) {
//			PicInfo picInfo = picListInfo.getPicList().get(i);
//			reqList.add(picInfo);
//		}
//		
//		Gson gson = new Gson();
//		String picListJson = gson.toJson(reqList);
//		PicInfo picInfo = new PicInfo();
//		picInfo.setPicName("Hello");
//		picInfo.setPicAddress("none");
//		String picJson = new Gson().toJson(picInfo);
		switch(type) {
		case "OnShow":
			MovieManager movieManager = new MovieManager();
			result = movieManager.getQueryMovieJson(from, to);
			break;
		case "ComingSoon":
			ComingSoonMovieManager csMovieManager = new ComingSoonMovieManager();
			result = csMovieManager.getQueryMovieJson(from, to);
			break;
		case "InTheater":
			InTheaterMovieManager itMovieManager = new InTheaterMovieManager();
			result = itMovieManager.getQueryMovieJson(from, to);
			break;	
		case "ChoosedSeats":	//选择座位,返回已定座位的json
			MovieOrderManager movieOrderManager = new MovieOrderManager();
			result = movieOrderManager.getChoosedMovieOrder(movieId, create_date);
			break;
		case "MoviePrice":
			TicketManager ticketManager = new TicketManager();
			result =  ticketManager.getPrice(movieId) + "";
			break;
		default:
			break;
		}
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
