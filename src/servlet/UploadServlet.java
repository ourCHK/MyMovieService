package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.catalina.core.ApplicationPart;

import impl.MovieManager;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
@MultipartConfig(location="/home/chk/MyFolder/temp")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String pathDir = "/home/chk/MyFolder/pic/";
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String movie_name = request.getParameter("name");
		String main_performer = request.getParameter("main_performer");
		String introduce = request.getParameter("introduce");
		boolean is_on_show = false;
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		File file = new File(pathDir);
		if (!file.exists()) {
			file.mkdirs();
		}

		Part p = request.getPart("file1");
		if (p.getContentType().contains("image")) {
			ApplicationPart ap = (ApplicationPart) p;
			String fileName = ap.getFilename();
			int path_idx = fileName.lastIndexOf("\\")+1;
			String fileName2 = fileName.substring(path_idx,fileName.length());
			String path = pathDir + fileName2;
			p.write(path);
			MovieManager movieManager = new MovieManager();
			if(movieManager.addMovie(movie_name, main_performer, introduce, is_on_show, path))
				out.write("图片上传成功");
			else {
				out.write("图片上传失败");
			}
		} else {
			out.write("图片上传失败");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
