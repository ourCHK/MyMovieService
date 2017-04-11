package servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import impl.ComingSoonMovieManager;
import impl.MovieManager;

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
		int from  = Integer.parseInt(request.getParameter("from"));
		int to = Integer.parseInt(request.getParameter("to"));
		String type  = request.getParameter("type");
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
